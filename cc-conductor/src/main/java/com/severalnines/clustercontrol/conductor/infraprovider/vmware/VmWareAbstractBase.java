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

import com.severalnines.clustercontrol.conductor.infraprovider.InfraProviderBase;
import com.vmware.vapi.bindings.StubConfiguration;
import com.vmware.vapi.protocol.HttpConfiguration;
import com.vmware.vapi.protocol.HttpConfiguration.KeyStoreConfig;
import com.vmware.vapi.protocol.HttpConfiguration.SslConfiguration;
import com.vmware.vcenter.VM;
import com.vmware.vcenter.vm.guest.networking.Interfaces;
import com.vmware.vim25.ManagedObjectReference;
import org.apache.commons.lang.StringUtils;
import vmware.samples.common.SslUtil;
import vmware.samples.common.authentication.VapiAuthenticationHelper;
import vmware.samples.common.authentication.VimAuthenticationHelper;
import vmware.samples.common.vim.helpers.VmVappPowerOps;
import vmware.samples.contentlibrary.client.ClsApiClient;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Abstract base class for vSphere samples
 *
 */
public abstract class VmWareAbstractBase extends InfraProviderBase {
    private String server="";
    private String username="";
    private String password="";
    private boolean skipServerVerification=true;
    private String truststorePath = null;
    private String truststorePassword = null;
    protected VimAuthenticationHelper vimAuthHelper;
    protected VapiAuthenticationHelper vapiAuthHelper;
    protected StubConfiguration sessionStubConfig;

    protected String libItemName = "ubuntu2004";

    protected String clusterName = "FirstCluster";

    protected String vmFolderName = "DBnodes";

    protected String datacenterName = "HomeDC";

    protected String datastoreName = "datastore1";

    protected String vmName = "";

    protected ClsApiClient client;
    protected VmVappPowerOps vmPowerOps;

    protected VM vmService;

    protected Interfaces intfcService;

    protected ManagedObjectReference vmMoRef;

    public String getServer() {
        return this.server;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isSkipServerVerification() {
        return skipServerVerification;
    }

    protected VmWareAbstractBase() {
        super();
        this.server = System.getenv("VMWARE_API_SERVER");
        this.username = System.getenv("VMWARE_API_USER");
        this.password = System.getenv("VMWARE_API_PASSWORD");
        System.out.println("VmWareAbstractBase::login() - server:" + this.server + "; user:" + this.username);
    }

    /**
     * Creates a session with the server using username/password.
     *
     *<p><b>
     * Note: If the "skip-server-verification" option is specified, then this
     * method trusts the SSL certificate from the server and doesn't verify
     * it. Circumventing SSL trust in this manner is unsafe and should not be
     * used with production code. This is ONLY FOR THE PURPOSE OF DEVELOPMENT
     * ENVIRONMENT
     * <b></p>
     * @throws Exception
     */
    @Override
    public void login() throws Exception {
        this.vapiAuthHelper = new VapiAuthenticationHelper();
        this.vimAuthHelper = new VimAuthenticationHelper();
        HttpConfiguration httpConfig = buildHttpConfiguration();
        this.sessionStubConfig =
                vapiAuthHelper.loginByUsernameAndPassword(
                    this.server, this.username, this.password, httpConfig);
        System.out.println("VmWareAbstractBase::login() - server:" + this.server + "; user:" + this.username);
        this.vimAuthHelper.loginByUsernameAndPassword(
                    this.server, this.username, this.password);
    }

    public void setup() throws Exception {
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

    /**
     * Builds the Http settings to be applied for the connection to the server.
     * @return http configuration
     * @throws Exception 
     */
    protected HttpConfiguration buildHttpConfiguration() throws Exception {
        HttpConfiguration httpConfig =
            new HttpConfiguration.Builder()
            .setSslConfiguration(buildSslConfiguration())
            .getConfig();
        
        return httpConfig;
	}

	/**
     * Builds the SSL configuration to be applied for the connection to the
     * server
     * 
     * For vApi connections:
     * If "skip-server-verification" is specified, then the server certificate
     * verification is skipped. The method retrieves the certificate
     * from specified server and adds it to an in-memory trustStore which is
     * returned.
     * If "skip-server-verification" is not specified, then it uses the
     * truststorepath and truststorepassword to load the truststore and return
     * it.
     *
     * For VIM connections:
     * If "skip-server-verification" is specified, then it trusts all the
     * VIM API connections made to the specified server.
     * If "skip-server-verification" is not specified, then it sets the System
     * environment property "javax.net.ssl.trustStore" to the path of the file
     * containing the trusted server certificates.
     *
     *<p><b>
     * Note: Below code circumvents SSL trust if "skip-server-verification" is
     * specified. Circumventing SSL trust is unsafe and should not be used
     * in production software. It is ONLY FOR THE PURPOSE OF DEVELOPMENT
     * ENVIRONMENTS.
     *<b></p>
     * @return SSL configuration
     * @throws Exception
     */
    protected SslConfiguration buildSslConfiguration() throws Exception {
        SslConfiguration sslConfig;

        if(this.skipServerVerification) {
            /*
             * Below method enables all VIM API connections to the server
             * without validating the server certificates.
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            SslUtil.trustAllHttpsCertificates();

            /*
             * Below code enables all vAPI connections to the server
             * without validating the server certificates..
             *
             * Note: Below code is to be used ONLY IN DEVELOPMENT ENVIRONMENTS.
             * Circumventing SSL trust is unsafe and should not be used in
             * production software.
             */
            sslConfig = new SslConfiguration.Builder()
            		.disableCertificateValidation()
            		.disableHostnameVerification()
            		.getConfig();
        } else {
            /*
             * Set the system property "javax.net.ssl.trustStore" to
             * the truststorePath
             */
            System.setProperty("javax.net.ssl.trustStore", this.truststorePath);
            KeyStore trustStore =
                SslUtil.loadTrustStore(this.truststorePath,
                		this.truststorePassword);
            KeyStoreConfig keyStoreConfig =
            		new KeyStoreConfig("", this.truststorePassword);
            sslConfig =
            		new SslConfiguration.Builder()
            		.setKeyStore(trustStore)
            		.setKeyStoreConfig(keyStoreConfig)
            		.getConfig();
        }

        return sslConfig;
    }

    /**
     * Logs out of the server
     * @throws Exception
     */
    public void logout() throws Exception {
        this.vapiAuthHelper.logout();
        this.vimAuthHelper.logout();
    }

    /**
     * Executes the sample using the command line arguments or parameters from
     * the configuration file. Execution involves the following steps:
     * 1. Parse the arguments required by the sample
     * 2. Login to the server
     * 3. Setup any resources required by the sample run
     * 4. Run the sample
     * 5. Cleanup any data created by the sample run, if cleanup=true
     * 6. Logout of the server
     *
     * @param args command line arguments passed to the sample
     * @throws Exception
     */
//    protected void execute(String[] args) throws Exception {
//        try {
//            // Parse the command line arguments or the configuration file
//            parseArgs(args);
//
//            // Login to the server
//            login();
//
//            // Setup any resources required by the sample
//            setup();
//
//            // Execute the sample
//            run();
//
//            if (clearData) {
//                // Clean up the sample data
//                cleanup();
//            }
//
//        } finally {
//            // Logout of the server
//            logout();
//        }
//    }

}
