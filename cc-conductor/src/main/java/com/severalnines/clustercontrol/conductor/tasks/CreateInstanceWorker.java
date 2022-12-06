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
package com.severalnines.clustercontrol.conductor.tasks;

import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraInput;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import com.severalnines.clustercontrol.conductor.infraprovider.InfraProviderBase;
import com.severalnines.clustercontrol.conductor.infraprovider.aws.Ec2Instance;
import com.severalnines.clustercontrol.conductor.infraprovider.vmware.VmWareVm;
import com.severalnines.clustercontrol.conductor.infraprovider.vultr.VultrVps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateInstanceWorker extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CreateInstanceWorker.class);

    private CreateInfraInput infraSpecification;

    public CreateInfraInput getInfraSpecification() {
        return this.infraSpecification;
    }

    private CreateInfraOutput responseData;

    public Object getResponseData() {
        return this.responseData;
    }

    private CreateInstanceWorker() {
    }

    protected CreateInstanceWorker(CreateInfraInput infraSpec) {
        this.infraSpecification = infraSpec;
    }

    @Override
    public void run() {
        try {
            InfraProviderBase iB = null;
//            CreateInfraInput infraInput = new CreateInfraInput();
//            infraInput.setClusterName(infraSpecification.getClusterName());
//            infraInput.setInfraProvider(infraSpecification.getInfraProvider());
//            infraInput.setVcpus(infraSpecification.getvCPUs());
//            infraInput.setSystemRam(infraSpecification.getSystemRam());
//            infraInput.setVolumeSize(infraSpecification.getVolumeSize());
            CreateInfraOutput out = null;
            String vmId = null;

            // Serialize input params
            String jsonIn = JsonSerializeDeserialize.objectToJson(this.infraSpecification);

            if (this.infraSpecification.getInfraProvider().compareToIgnoreCase("vultr")==0) {
                iB = new VultrVps();
            } else if (this.infraSpecification.getInfraProvider().compareToIgnoreCase("vmware")==0) {
                iB = new VmWareVm();
                try {
                    iB.login();
                    iB.setup();
                } catch (Exception e1) {
                    logger.warn("Exception initialing VMware", e1);
                }
            } else if (this.infraSpecification.getInfraProvider().compareToIgnoreCase("aws")==0) {
                iB = new Ec2Instance();
            } else {
                // Bad news ....
                logger.warn("Infra provider type (vultr, vmware, aws, etc) not specified");
                return;
            }

            // **********************
            // Create the VM instance
            String jsonOut = iB.createInstance(jsonIn);
            logger.debug("Create output: {}", jsonOut);
            // **********************

            // Deserialize response
            out = JsonSerializeDeserialize.jsonToObject(jsonOut, CreateInfraOutput.class);
            logger.debug("Intenal VM ID: {}", out.getInternalVmId());
            // vmId = out.getInternalVmId();

            // Setup how to list the VM instance. Need to poll until an IP address is given to VM
            CreateInfraInput listIn = new CreateInfraInput();
            listIn.setClusterName(infraSpecification.getClusterName());
            listIn.setInternalVmId(out.getInternalVmId());
            jsonIn = JsonSerializeDeserialize.objectToJson(listIn);

            // **********************
            // List the instance (in a loop until) a valid IP addresses is available
            jsonOut = iB.listInstance(jsonIn);
            // **********************
            this.responseData = JsonSerializeDeserialize.jsonToObject(jsonOut, CreateInfraOutput.class);
        } catch (Exception e) {
            logger.warn("Exception in creating instance: ", e);
            return;
        }

    }

}
