/*
 *     Copyright 2022 Severalnines Inc. @ https://severalnines.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.severalnines.clustercontrol.conductor.infraprovider.vmware;

import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraInput;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import com.vmware.content.library.ItemTypes;
import com.vmware.vcenter.ovf.DiskProvisioningType;
import com.vmware.vcenter.ovf.LibraryItemTypes;
import com.vmware.vcenter.vm.guest.networking.Interfaces;
import com.vmware.vcenter.vm.guest.networking.InterfacesTypes;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vmware.samples.common.vim.helpers.VimUtil;
import vmware.samples.vcenter.helpers.FolderHelper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VmWareVm extends VmWareAbstractBase {

    private static final Logger logger
            = LoggerFactory.getLogger(VmWareVm.class);

    @Override
    public String createInstance(String jsonStr) throws Exception {
        String ret = null;

        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);
        logger.info("Tags/ClusterName: {}", in.getClusterName());

        ManagedObjectReference clusterMoRef =
                VimUtil.getCluster(vimAuthHelper.getVimPort(),
                        this.vimAuthHelper.getServiceContent(),
                        this.clusterName);
        assert clusterMoRef != null : "Cluster by name " + this.clusterName
                +" must exist";
        logger.debug("Cluster MoRef : {} : {}", clusterMoRef.getType(), clusterMoRef.getValue());

        // Find the cluster's root resource pool
        List<DynamicProperty> dynamicProps =
                VimUtil.getProperties(this.vimAuthHelper.getVimPort(),
                        this.vimAuthHelper.getServiceContent(),
                        clusterMoRef,
                        clusterMoRef.getType(),
                        Arrays.asList("resourcePool"));
        assert dynamicProps != null && dynamicProps.size() > 0;
        ManagedObjectReference rootResPoolMoRef =
                (ManagedObjectReference) dynamicProps.get(0).getVal();
        logger.debug("Resource pool MoRef : {} : {}", rootResPoolMoRef.getType(), rootResPoolMoRef.getValue());

        // Find the library item by name
        ItemTypes.FindSpec findSpec = new ItemTypes.FindSpec();
        findSpec.setName(this.libItemName);
        List<String> itemIds = this.client.itemService().find(findSpec);
        assert !itemIds.isEmpty() : "Unable to find a library item with name: "
                + this.libItemName;
        String itemId = itemIds.get(0);
        logger.info("Library item ID : {}", itemId);

        // Deploy a VM from the library item on the given cluster
        logger.info("Deploying Vm : {}", this.vmName);
        String vmId =
                deployVMFromOvfItem(rootResPoolMoRef, this.vmName, itemId);
        assert vmId != null;
        logger.info("Vm created : {}", vmId);

        // Power on the VM and wait for the power on operation to complete
        this.vmMoRef = new ManagedObjectReference();
        this.vmMoRef.setType("VirtualMachine");
        this.vmMoRef.setValue(vmId);
        this.vmPowerOps.powerOnVM(this.vmName, this.vmMoRef);

        CreateInfraOutput out = new CreateInfraOutput();
        out.setInternalVmId(vmId);

        // return (vmId);
        return (JsonSerializeDeserialize.objectToJson(out));
    }

    @Override
    public String listInstance(String jsonStr) throws Exception {
        String ret = null;

        // CreateInfraOutput carries internaVmID from previous phase
        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);

        logger.info("Internal VM ID: {}", in.getInternalVmId());

        try {
            Thread.sleep(25000);
        } catch (Exception e) {
        }

        boolean isSuccess = false;
        int totalRetryTm = 0;
        final int MAX_RETRY_TIME_MS = 5*60*1000; // 5 minutes
        final int RETRY_SLEEP_TIME_MS = 5*1000; // 5 seconds
        String ipAddr = null;
        while (!isSuccess) {
            try {
                Thread.sleep(RETRY_SLEEP_TIME_MS);
                totalRetryTm += RETRY_SLEEP_TIME_MS;
                if (totalRetryTm >= MAX_RETRY_TIME_MS) {
                    logger.warn("Time exceeded: waiting for IP address");
                    break;
                }
                List<InterfacesTypes.Info> intfcList = this.intfcService.list(in.getInternalVmId());
                logger.debug("Num interfaces: {}", intfcList.size());
                for (InterfacesTypes.Info item : intfcList) {
                    for (Interfaces.IpAddressInfo ipItem : item.getIp().getIpAddresses()) {
                        if (ipItem.getState()==InterfacesTypes.IpAddressStatus.PREFERRED &&
                                ipItem.getPrefixLength() < 33) {
                            // Return IP address back to orchestrator
                            logger.info("IP: {}", ipItem.getIpAddress());
                            ipAddr = ipItem.getIpAddress();
                            isSuccess = true;
                        } else {
                            logger.debug("IP state: {}", ipItem.getState());
                            logger.debug("IP prefix len: {}", ipItem.getPrefixLength());
                            logger.debug("IP: {}", ipItem.getIpAddress());
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn("Exception: ", e);
            }
        }

        CreateInfraOutput out = new CreateInfraOutput();
        if (ipAddr != null) {
            out.setHostname(ipAddr);
            out.setPrivateIp(ipAddr);
            out.setPublicIp(ipAddr);
            out.setInternalVmId(in.getInternalVmId());
            ret = JsonSerializeDeserialize.objectToJson(out);
        }

        return (ret);
    }

    private String deployVMFromOvfItem(ManagedObjectReference rootResPoolMoRef,
                                       String vmName, String libItemId) {
        // Creating the deployment.
        LibraryItemTypes.DeploymentTarget deploymentTarget = new LibraryItemTypes.DeploymentTarget();

        //Prem:
        if (this.vmFolderName != null) {
            String vmFolderId =
                    FolderHelper.getFolder(this.vapiAuthHelper.getStubFactory(),
                            this.sessionStubConfig,
                            this.datacenterName,
                            this.vmFolderName);
            logger.debug("Selecting folder name=({}) id=({})", vmFolderName, vmFolderId);
            deploymentTarget.setFolderId(vmFolderId);
        }

        // Setting the target resource pool.
        deploymentTarget.setResourcePoolId(rootResPoolMoRef.getValue());
        // Creating and setting the resource pool deployment spec.
        LibraryItemTypes.ResourcePoolDeploymentSpec deploymentSpec =
                new LibraryItemTypes.ResourcePoolDeploymentSpec();
        deploymentSpec.setName(this.vmName);
        deploymentSpec.setAcceptAllEULA(true);

        //Prem:
        deploymentSpec.setStorageProvisioning(DiskProvisioningType.thin);

        logger.info("Storage profile ID: {}", deploymentSpec.getStorageProfileId());

        // Retrieve the library items OVF information and use it for populating
        // deployment spec.
        LibraryItemTypes.OvfSummary ovfSummary = this.client.ovfLibraryItemService()
                .filter(libItemId, deploymentTarget);
        logger.debug("Ovf summary: {}", ovfSummary);

        // Setting the annotation retrieved from the OVF summary.
        deploymentSpec.setAnnotation(ovfSummary.getAnnotation());

        // Calling the deploy and getting the deployment result.
        LibraryItemTypes.DeploymentResult deploymentResult = this.client.ovfLibraryItemService()
                .deploy(UUID.randomUUID().toString(),
                        libItemId,
                        deploymentTarget,
                        deploymentSpec);
        if (deploymentResult.getSucceeded()) {
            return deploymentResult.getResourceId().getId();
        } else {
            throw new RuntimeException(deploymentResult.getError().toString());
        }
    }

}
