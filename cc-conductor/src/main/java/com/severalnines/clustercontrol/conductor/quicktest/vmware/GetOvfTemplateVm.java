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

import com.vmware.vcenter.vm.guest.networking.Interfaces;
import com.vmware.vcenter.vm.guest.networking.InterfacesTypes;
import org.apache.commons.cli.Option;
import vmware.samples.common.SamplesAbstractBase;

import java.util.Arrays;
import java.util.List;

/**
 * Description: Demonstrates the workflow to deploy an OVF library item to a
 * resource pool.
 *
 * $ java -ea -cp ./packaging/target/packaging-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
 *    com.severalnines.clustercontrol.conductor.quicktest.vmware.GetOvfTemplateVm \
 *    --server 10.63.1.215 --username "administrator@vsphere.local" --password PASS --vmid <vm-32> --skip-server-verification
 *
 * Author: Severalnines, Inc.
 * Sample Prerequisites: The sample needs an existing OVF
 * library item and an existing cluster with resources for creating the VM."
 *
 */
public class GetOvfTemplateVm extends SamplesAbstractBase {

    Interfaces intfcService;

    private String vmId;

    /**
     * Define the options specific to this sample and configure the sample using
     * command-line arguments or a config file
     *
     * @param args command line arguments passed to the sample
     */
    protected void parseArgs(String[] args) {
        // Parse the command line options or use config file
        Option vmIdOption = Option.builder()
            .longOpt("vmid")
            .desc("Name of the Cluster in which VM would be created")
            .required(true)
            .hasArg()
            .argName("VM ID")
            .build();

        List<Option> optionList = Arrays.asList(vmIdOption);
        super.parseArgs(optionList, args);
        this.vmId = (String) parsedOptions.get("vmid");
    }

    protected void setup() throws Exception {
        this.intfcService = vapiAuthHelper.getStubFactory().createStub(Interfaces.class, this.sessionStubConfig);
    }

    protected void run() throws Exception {
        List<InterfacesTypes.Info> intfcList = this.intfcService.list(this.vmId);
        System.out.println("\nDefault All INTFC Info:\n" + intfcList);
        for (InterfacesTypes.Info item : intfcList) {
            System.out.println("\nDefault Single INTFC item Info:\n" + item);
            for (Interfaces.IpAddressInfo ipItem : item.getIp().getIpAddresses()) {
                System.out.println("\nDefault Single IP item Info:\n" + ipItem);
                if (ipItem.getState()==InterfacesTypes.IpAddressStatus.PREFERRED && ipItem.getPrefixLength() < 33) {
                    // Return IP address back to orchestrator
                    System.out.println("IP: " + ipItem.getIpAddress());
                }
            }
        }
    }

    protected void cleanup() throws Exception {
    }

    public static void main(String[] args) throws Exception {
        /*
         * Execute the sample using the command line arguments or parameters
         * from the configuration file. This executes the following steps:
         * 1. Parse the arguments required by the sample
         * 2. Login to the server
         * 3. Setup any resources required by the sample run
         * 4. Run the sample
         * 5. Cleanup any data created by the sample run, if cleanup=true
         * 6. Logout of the server
         */
        new GetOvfTemplateVm().execute(args);
    }
}
