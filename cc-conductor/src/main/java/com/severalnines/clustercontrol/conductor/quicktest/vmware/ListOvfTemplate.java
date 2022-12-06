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
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.ManagedObjectReference;
import org.apache.commons.cli.Option;
import vmware.samples.common.SamplesAbstractBase;
import vmware.samples.common.vim.helpers.VimUtil;
import vmware.samples.common.vim.helpers.VmVappPowerOps;
import vmware.samples.contentlibrary.client.ClsApiClient;

import java.util.Arrays;
import java.util.List;

/**
 * Description: Demonstrates the workflow to deploy an OVF library item to a
 * resource pool.
 *
 * $ java -ea -cp ./packaging/target/packaging-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.vmware.ListOvfTemplate \
 *   --server 10.63.1.215 --username "administrator@vsphere.local" --password PASS --clustername FirstCluster \
 *   --libitemname ubuntu2004 --skip-server-verification
 *
 * Author: Severalnines, Inc.
 * Sample Prerequisites: The sample needs an existing OVF
 * library item and an existing cluster with resources for creating the VM."
 *
 */
public class ListOvfTemplate extends SamplesAbstractBase {

    private String libItemName;
    private String clusterName;
    private ClsApiClient client;
    private VmVappPowerOps vmPowerOps;
    private ManagedObjectReference vmMoRef;

    /**
     * Define the options specific to this sample and configure the sample using
     * command-line arguments or a config file
     *
     * @param args command line arguments passed to the sample
     */
    protected void parseArgs(String[] args) {
        // Parse the command line options or use config file
        Option clusNameOption = Option.builder()
            .longOpt("clustername")
            .desc("Name of the Cluster in which VM would be created")
            .required(true)
            .hasArg()
            .argName("CLUSTER")
            .build();

        Option libItemNameOption = Option.builder()
            .longOpt("libitemname")
            .desc("The name of the library item to"
                  + "deploy. The library item "
                  + "should contain an OVF package")
            .required(true)
            .hasArg()
            .argName("CONTENT LIBRARY")
            .build();

        List<Option> optionList = Arrays.asList(clusNameOption,
            libItemNameOption);
        super.parseArgs(optionList, args);
        this.clusterName = (String) parsedOptions.get("clustername");
        this.libItemName = (String) parsedOptions.get("libitemname");
    }

    protected void setup() throws Exception {
        this.client = new ClsApiClient(this.vapiAuthHelper.getStubFactory(),
            sessionStubConfig);
        this.vmPowerOps = new VmVappPowerOps(this.vimAuthHelper.getVimPort(),
            this.vimAuthHelper.getServiceContent());
    }

    protected void run() throws Exception {
        // Find the MoRef of the VC cluster using VIM APIs
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
        // findSpec.setName("foo");
        List<String> itemIds = this.client.itemService().find(findSpec);
        assert !itemIds.isEmpty() : "Unable to find a library item with name: "
                                    + this.libItemName;
        System.out.println(itemIds);
        String itemId = itemIds.get(0);
        System.out.println("Library item ID : " + itemId);
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
        new ListOvfTemplate().execute(args);
    }
}
