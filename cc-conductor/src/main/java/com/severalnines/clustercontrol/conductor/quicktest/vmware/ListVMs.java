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

import com.vmware.vcenter.VM;
import com.vmware.vcenter.VMTypes.FilterSpec.Builder;
import com.vmware.vcenter.VMTypes.Summary;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vmware.samples.common.ParametersHelper;
import vmware.samples.common.SamplesAbstractBase;
import vmware.samples.vcenter.helpers.ClusterHelper;
import vmware.samples.vcenter.helpers.DatacenterHelper;
import vmware.samples.vcenter.helpers.FolderHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Description: Demonstrates getting list of VMs present in vCenter
 * <p>
 * Author: VMware, Inc.
 * Sample Prerequisites: vCenter 6.5+
 */
public class ListVMs extends SamplesAbstractBase {

    private static final Logger logger
            = LoggerFactory.getLogger(SamplesAbstractBase.class);

    private VM vmService;
    private String vmFolderName;
    private String datacenterName;
    private String clusterName;

    /**
     * Define the options specific to this sample and configure the sample using
     * command-line arguments or a config file
     *
     * @param args command line arguments passed to the sample
     */
    protected void parseArgs(String[] args) {
        ParametersHelper paramsHelper = null;

        try {
            Option serverOption = Option.builder()
                    .required(true)
                    .hasArg()
                    .argName("SERVER")
                    .longOpt("server")
                    .desc("hostname of vCenter Server")
                    .build();

            Option datacenterOption = Option.builder()
                    .longOpt("datacenter")
                    .desc("OPTIONAL: Specify the name of the Datacenter"
                            + " to list the Vms in it.")
                    .argName("DATACENTER")
                    .required(false)
                    .hasArg()
                    .build();
            Option clusterOption = Option.builder()
                    .longOpt("cluster")
                    .desc("OPTIONAL: Specify the name of the Cluster to list the"
                            + " Vms in it.")
                    .argName("CLUSTER")
                    .required(false)
                    .hasArg()
                    .build();
            Option vmFolderOption = Option.builder()
                    .longOpt("vmfolder")
                    .desc("OPTIONAL: Specify the name of the VM Folder to list the"
                            + " Vms in it.")
                    .argName("VM FOLDER")
                    .required(false)
                    .hasArg()
                    .build();
            Option skipServerVerificationOption =
                    Option.builder()
                            .required(false)
                            .longOpt("skip-server-verification")
                            .type(Boolean.class)
                            .desc("OPTIONAL: Specify this option if you do not "
                                    + "want to perform SSL certificate "
                                    + "verification.\nNOTE: Circumventing SSL "
                                    + "trust in this manner is unsafe and should "
                                    + "not be used with production code. "
                                    + "This is ONLY FOR THE PURPOSE OF "
                                    + "DEVELOPMENT ENVIRONMENT.")
                            .build();
            Option cleardataOption = Option.builder()
                    .required(false)
                    .longOpt("cleardata")
                    .type(Boolean.class)
                    .desc("OPTIONAL: Specify this option"
                            + " to undo all persistent "
                            + "results of running the "
                            + "sample.")
                    .build();
            Option truststorePathOption =
                    Option.builder()
                            .required(false)
                            .hasArg()
                            .argName("ABSOLUTE PATH OF JAVA TRUSTSTORE FILE")
                            .longOpt("truststorepath")
                            .desc("Specify the absolute path to the file "
                                    + "containing the trusted server certificates. "
                                    + "This option can be skipped if the parameter "
                                    + "skip-server-verification is specified.")
                            .build();

            Option truststorePasswordOption =
                    Option.builder()
                            .required(false)
                            .hasArg()
                            .argName("JAVA TRUSTSTORE PASSWORD")
                            .longOpt("truststorepassword")
                            .desc("Specify the password for the java "
                                    + "truststore. This option can be skipped if "
                                    + "the parameter skip-server-verification is "
                                    + "specified.")
                            .build();
            Option configFileOption = Option.builder()
                    .required(false)
                    .hasArg()
                    .argName("CONFIGURATION FILE")
                    .longOpt("config-file")
                    .desc("OPTIONAL: Absolute path to  "
                            + "the configuration file "
                            + "containing the sample "
                            + "options.\nNOTE: "
                            + "Parameters can be "
                            + "specified either "
                            + "in the configuration "
                            + "file or on the command "
                            + "line. Command "
                            + "line parameters will "
                            + "override values "
                            + "specified in the "
                            + "configuration file.")
                    .build();

            List<Option> optionList = Arrays.asList(vmFolderOption,
                    datacenterOption, clusterOption, configFileOption, serverOption, truststorePathOption,
                    truststorePasswordOption, cleardataOption,
                    skipServerVerificationOption);

            paramsHelper = new ParametersHelper(optionList);
            this.parsedOptions = paramsHelper.parse(args,
                    this.getClass().getName());

            // super.parseArgs(optionList, args);
            this.server = (String) parsedOptions.get("server");
            this.username = System.getenv("VMWARE_API_USER");
            this.password = System.getenv("VMWARE_API_PASSWORD");
            logger.debug("username: {}; password: {}", this.username, this.password);

            this.datacenterName = (String) parsedOptions.get("datacenter");
            this.clusterName = (String) parsedOptions.get("cluster");
            this.vmFolderName = (String) parsedOptions.get("vmfolder");

            Object clearDataObj = parsedOptions.get("cleardata");
            if (clearDataObj != null) {
                this.clearData = (Boolean) clearDataObj;
            } else {
                this.clearData = false;
            }

            Object skipServerVerificationObj =
                    parsedOptions.get("skip-server-verification");
            if(skipServerVerificationObj != null) {
                this.skipServerVerification =
                        (Boolean) skipServerVerificationObj;
            } else {
                this.skipServerVerification = false;
            }

            Object truststorePathObj = parsedOptions.get("truststorepath");
            if(truststorePathObj != null) {
                this.truststorePath =
                        (String) parsedOptions.get("truststorepath");
            }

            Object truststorePasswordObj =
                    parsedOptions.get("truststorepassword");
            if(truststorePasswordObj != null) {
                this.truststorePassword =
                        (String) parsedOptions.get("truststorepassword");
            }

            this.configFile =
                    parsedOptions.get("config-file") != null ?
                            (String) parsedOptions.get(
                                    "config-file") : null;

            // Check if truststorePath and truststorePassword are specified
            if(!this.skipServerVerification && (
                    this.truststorePath == null ||
                            this.truststorePassword == null)) {
                throw new ConfigurationException(
                        "The parameters truststorepath and truststorepassword "
                                + "need to be specified for server certificate "
                                + " verification. These are required"
                                + " parameters if the parameter skip-server-verification "
                                + "has not been specified.");
            }

        } catch (ParseException pex) {
            System.out.println(pex.getMessage());
            System.exit(0);
        } catch (ConfigurationException cex) {
            System.out.println(cex.getMessage());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    protected void setup() throws Exception {
        this.vmService =
                vapiAuthHelper.getStubFactory()
                        .createStub(VM.class, sessionStubConfig);
    }

    protected void run() throws Exception {
        Builder bldr = new Builder();
        if (null != this.datacenterName && !this.datacenterName.isEmpty()) {
            bldr.setDatacenters(Collections.singleton(DatacenterHelper.
                    getDatacenter(this.vapiAuthHelper.getStubFactory(),
                            this.sessionStubConfig, this.datacenterName)));
        }
        if (null != this.clusterName && !this.clusterName.isEmpty()) {
            bldr.setClusters(Collections.singleton(ClusterHelper.getCluster(
                    this.vapiAuthHelper.getStubFactory(), sessionStubConfig,
                    this.clusterName)));
        }
        if (null != this.vmFolderName && !this.vmFolderName.isEmpty()) {
            bldr.setFolders(Collections.singleton(FolderHelper.getFolder(
                    this.vapiAuthHelper.getStubFactory(), sessionStubConfig,
                    this.vmFolderName)));
        }
        List<Summary> vmList = this.vmService.list(bldr.build());
        System.out.println("----------------------------------------");
        System.out.println("List of VMs");
        for (Summary vmSummary : vmList) {
            System.out.println(vmSummary);
        }
        System.out.println("----------------------------------------");
    }

    protected void cleanup() throws Exception {
        // No cleanup required
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
        new ListVMs().execute(args);
    }
}
