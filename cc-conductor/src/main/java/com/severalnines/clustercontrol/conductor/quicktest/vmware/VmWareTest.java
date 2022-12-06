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
package com.severalnines.clustercontrol.conductor.quicktest.vmware;

import com.vmware.content.library.ItemTypes.FindSpec;
import com.vmware.vcenter.VM;
import com.vmware.vcenter.ovf.DiskProvisioningType;
import com.vmware.vcenter.ovf.LibraryItemTypes.DeploymentResult;
import com.vmware.vcenter.ovf.LibraryItemTypes.DeploymentTarget;
import com.vmware.vcenter.ovf.LibraryItemTypes.OvfSummary;
import com.vmware.vcenter.ovf.LibraryItemTypes.ResourcePoolDeploymentSpec;
import com.vmware.vcenter.vm.guest.networking.Interfaces;
import com.vmware.vcenter.vm.guest.networking.InterfacesTypes;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import org.apache.commons.lang.StringUtils;
import vmware.samples.common.SamplesAbstractBase;
import vmware.samples.common.vim.helpers.VimUtil;
import vmware.samples.common.vim.helpers.VmVappPowerOps;
import vmware.samples.contentlibrary.client.ClsApiClient;
import vmware.samples.vcenter.helpers.FolderHelper;

import java.text.SimpleDateFormat;
import java.util.*;

public class VmWareTest extends SamplesAbstractBase  {

    private String libItemName = "ubuntu2004";
    private String clusterName = "FirstCluster";
    private String vmName = "";
    private ClsApiClient client;
    private VmVappPowerOps vmPowerOps;
    private ManagedObjectReference vmMoRef;

    //Prem:
    private String vmFolderName = "DBnodes";

    private String datacenterName = "HomeDC";

    private String datastoreName = "datastore1";

    private VM vmService;

    private Interfaces intfcService;

    private String _IpAddressOut = "UNDEFINED";

    public VmWareTest() {

    }

    public String ExecuteMe() throws Exception {
        this.execute(null);
        return this._IpAddressOut;
    }

    public String createInstance(String jsonStr) throws Exception {
        String ret = null;

        return ret;
    }

    protected void setup() throws Exception {
        this.client = new ClsApiClient(this.vapiAuthHelper.getStubFactory(),
                this.sessionStubConfig);

        this.vmPowerOps = new VmVappPowerOps(this.vimAuthHelper.getVimPort(),
                this.vimAuthHelper.getServiceContent());

        //Prem:
        this.vmService = vapiAuthHelper.getStubFactory().createStub(VM.class,
                this.sessionStubConfig);

        // Generate a default VM name if it is not provided
        if (StringUtils.isBlank(this.vmName)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-kkmmss");
            this.vmName = "VM-" + sdf.format(new Date());
        }

        this.intfcService = vapiAuthHelper.getStubFactory().createStub(Interfaces.class, this.sessionStubConfig);
    }

    protected void parseArgs(String[] args) {
    }

    protected void cleanup() throws Exception {
    }

    protected void run() throws Exception {
        ManagedObjectReference clusterMoRef =
                VimUtil.getCluster(this.vimAuthHelper.getVimPort(),
                        this.vimAuthHelper.getServiceContent(),
                        this.clusterName);
        assert clusterMoRef != null : "Cluster by name " + this.clusterName
                +" must exist";
        System.out.println("Cluster MoRef : " + clusterMoRef.getType() + " : "
                + clusterMoRef.getValue());

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
        System.out.println("Resource pool MoRef : " + rootResPoolMoRef.getType()
                + " : " + rootResPoolMoRef.getValue());

        // Find the library item by name
        FindSpec findSpec = new FindSpec();
        findSpec.setName(this.libItemName);
        List<String> itemIds = this.client.itemService().find(findSpec);
        assert !itemIds.isEmpty() : "Unable to find a library item with name: "
                + this.libItemName;
        String itemId = itemIds.get(0);
        System.out.println("Library item ID : " + itemId);

        // Deploy a VM from the library item on the given cluster
        System.out.println("Deploying Vm : " + this.vmName);
        String vmId =
                deployVMFromOvfItem(rootResPoolMoRef, this.vmName, itemId);
        assert vmId != null;
        System.out.println("Vm created : " + vmId);

        // Power on the VM and wait for the power on operation to complete
        this.vmMoRef = new ManagedObjectReference();
        this.vmMoRef.setType("VirtualMachine");
        this.vmMoRef.setValue(vmId);
        this.vmPowerOps.powerOnVM(this.vmName, this.vmMoRef);

        //Prem:
//        VMTypes.Info vmInfo = this.vmService.get(vmId);
//        System.out.println("\nDefault VM Info:\n" + vmInfo);

        //Prem:
        try {
            Thread.sleep(25000);
        } catch (Exception e2) {
        }

        boolean isSuccess = false;
        int totalRetryTm = 0;
        final int MAX_RETRY_TIME_MS = 5*60*1000; // 5 minutes
        final int RETRY_SLEEP_TIME_MS = 5*1000; // 5 seconds
        while (!isSuccess) {
            try {
                Thread.sleep(RETRY_SLEEP_TIME_MS);
                totalRetryTm += RETRY_SLEEP_TIME_MS;
                if (totalRetryTm >= MAX_RETRY_TIME_MS) {
                    break;
                }
                List<InterfacesTypes.Info> intfcList = this.intfcService.list(vmId);
                for (InterfacesTypes.Info item : intfcList) {
                    for (Interfaces.IpAddressInfo ipItem : item.getIp().getIpAddresses()) {
                        if (ipItem.getState()==InterfacesTypes.IpAddressStatus.PREFERRED && ipItem.getPrefixLength() < 33) {
                            // Return IP address back to orchestrator
                            System.out.println("IP: " + ipItem.getIpAddress());
                            this._IpAddressOut = ipItem.getIpAddress();
                            isSuccess = true;
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

    }

    private String deployVMFromOvfItem(ManagedObjectReference rootResPoolMoRef,
                                       String vmName, String libItemId) {
        // Creating the deployment.
        DeploymentTarget deploymentTarget = new DeploymentTarget();

        //Prem:
        if (this.vmFolderName != null) {
            String vmFolderId =
                    FolderHelper.getFolder(this.vapiAuthHelper.getStubFactory(),
                            this.sessionStubConfig,
                            this.datacenterName,
                            this.vmFolderName);
            System.out.println("Selecting folder " + vmFolderName + "id=("
                    + vmFolderId + ")");

            deploymentTarget.setFolderId(vmFolderId);
        }

        // Setting the target resource pool.
        deploymentTarget.setResourcePoolId(rootResPoolMoRef.getValue());
        // Creating and setting the resource pool deployment spec.
        ResourcePoolDeploymentSpec deploymentSpec =
                new ResourcePoolDeploymentSpec();
        deploymentSpec.setName(this.vmName);
        deploymentSpec.setAcceptAllEULA(true);

        //Prem:
        deploymentSpec.setStorageProvisioning(DiskProvisioningType.thin);

        System.out.println("Storage profile ID: " + deploymentSpec.getStorageProfileId());

        // Retrieve the library items OVF information and use it for populating
        // deployment spec.
        OvfSummary ovfSummary = this.client.ovfLibraryItemService()
                .filter(libItemId, deploymentTarget);
        System.out.println(ovfSummary);

        // Setting the annotation retrieved from the OVF summary.
        deploymentSpec.setAnnotation(ovfSummary.getAnnotation());

        // Calling the deploy and getting the deployment result.
        DeploymentResult deploymentResult = this.client.ovfLibraryItemService()
                .deploy(UUID.randomUUID().toString(),
                        libItemId,
                        deploymentTarget,
                        deploymentSpec);
        if (deploymentResult.getSucceeded()) {
            return deploymentResult.getResourceId().getId();
        } else
            throw new RuntimeException(deploymentResult.getError().toString());
    }

    public static void main(String[] args) {
    }
}
