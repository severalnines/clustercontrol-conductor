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
package com.severalnines.clustercontrol.conductor.pojo;

public class CreateInfraOutput {
    public CreateInfraOutput() {}

    private String vmInternalId;
    public void setInternalVmId(String vmId) {this.vmInternalId = vmId;}
    public String getInternalVmId() {return this.vmInternalId;}

    private String publicIp;
    public void setPublicIp(String publicIp) {this.publicIp = publicIp;}
    public String getPublicIp() {return this.publicIp;}

    private String privateIp;
    public void setPrivateIp(String privateIp) {this.privateIp = privateIp;}
    public String getPrivateIp() {return this.privateIp;}

    private String hostname;
    public void setHostname(String hostname) {this.hostname = hostname;}
    public String getHostname() {return this.hostname;}

}
