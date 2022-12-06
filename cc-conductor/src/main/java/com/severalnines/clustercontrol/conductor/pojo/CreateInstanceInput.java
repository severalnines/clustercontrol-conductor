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

public class CreateInstanceInput {
    public CreateInstanceInput() {}
    private String clusterName;
    public void setClusterName(String clusterName) {this.clusterName = clusterName;}
    public String getClusterName() {return this.clusterName;}

    private String dbPlan;
    public void setDbPlan(String dbPlan) {this.dbPlan = dbPlan;}
    public String getDbPlan() {return this.dbPlan;}

    private String vmInternalId;
    public void setInternalVmId(String vmId) {this.vmInternalId = vmId;}
    public String getInternalVmId() {return this.vmInternalId;}
}
