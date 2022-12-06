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

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import com.severalnines.clustercontrol.api.abstraction.client.ClusterControlClient;
import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.api.abstraction.pojo.*;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DeployDbClusterTask extends AbstractConductorTask {

    private static final Logger logger
            = LoggerFactory.getLogger(DeployDbClusterTask.class);

    private ClusterControlClient ccClient;

    public DeployDbClusterTask(String taskDefName, ClusterControlClient ccClient) {
        super(taskDefName);
        this.ccClient = ccClient;
    }

    public ClusterControlClient getCcClient() {
        return ccClient;
    }

    public void authenticate() throws Exception {
        AuthenticateUser authU = new AuthenticateUser();
        authU.setCcUrl(System.getenv("CC_URL"));
        authU.setUsername(System.getenv("CC_API_USER"));
        authU.setPassword(System.getenv("CC_API_USER_PW"));
        String authRet = this.ccClient.authenticateUser(
                com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize.objectToJson(authU));
        logger.info("Login: {}", authRet);
    }

    @Override
    public TaskResult execute(Task task) {

        logger.info("Starting task...");

        TaskResult result = new TaskResult(task);
        result.setStatus(Status.FAILED);

        String clusterName = task.getInputData().get("cluster_name").toString();
        String clusterType = task.getInputData().get("cluster_type").toString();
        String vendor = task.getInputData().get("vendor").toString();
        String version = task.getInputData().get("version").toString();
        String port = ((Integer) task.getInputData().get("port")).toString();
        String adminUser = task.getInputData().get("admin_user").toString();
        String adminPw = task.getInputData().get("admin_pw").toString();
        String nodes = ((Integer) task.getInputData().get("nodes")).toString();

        Object hosts = task.getInputData().get("hosts");

        if (hosts == null) {
            logger.warn("Hosts empty!!!");
            return result;
        }

        DbCluster createDetails = new DbCluster();
        createDetails.setClusterName(clusterName);
        createDetails.setClusterType(clusterType);
        createDetails.setDbVendor(vendor);
        createDetails.setDbVersion(version);
        createDetails.setPort(port);
        createDetails.setDbAdminUser(adminUser);
        createDetails.setDbAdminUserPw(adminPw);

        try {
            ArrayList<Object> realHosts = (ArrayList<Object>) hosts;
            logger.debug("Num hosts: {}", realHosts.size());
            for (Object o : realHosts) {
                LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) o;
                Host ccHost = new Host();
                ccHost.setHostname((String) item.get("hostname"));
                ccHost.setPort(port);

                createDetails.addHost(ccHost);
            }

            if (createDetails.getHosts().size() > 1) {
                Map<String,String> msLink = new HashMap<>();
                // msLink.put("primary", "standby");
                Host p = createDetails.getHosts().get(0);
                for (int i = 1; i < createDetails.getHosts().size(); i++) {
                    Host s = createDetails.getHosts().get(i);
                    msLink.put(p.getHostname(), s.getHostname());
                }
                createDetails.addMasterSlaveLink(msLink);
            }
        } catch (Exception e) {
            logger.warn("Exception: ", e);
            return result;
        }

        String ret;
        String createJson;
        try {
            createJson = JsonSerializeDeserialize.objectToJson(createDetails);
            logger.debug("Create: {}", createJson);
            ret = this.ccClient.createDatabaseCluster(createJson);
        } catch (Exception e) {
            logger.warn("Exception: ", e);
            return result;
        }

        JobResponse crJobResp;
        try {
            crJobResp = JsonSerializeDeserialize.jsonToObject(ret, JobResponse.class);
            logger.info("CreateCluster Job ID: {}", crJobResp.getJob().getJobId());
        } catch (Exception e) {
            logger.warn("Exception: ", e);
            return result;
        }

        int maxWaitTime = 1000 * 60 * 15; // 15 minutes
        int SLEEP_TIME = 1000 * 5;
        Job job = new Job();
        job.setJobId(crJobResp.getJob().getJobId());
        String getJobReqStr;
        try {
            getJobReqStr = JsonSerializeDeserialize.objectToJson(job);
        } catch (Exception e) {
            logger.warn("Exception: ", e);
            return result;
        }
        while (maxWaitTime > 0) {
            logger.debug("Job status: {}", crJobResp.getJob().getStatus());
            if (crJobResp.getJob().getStatus().equals("FINISHED")) {
                // logger.info("Final job response: {}", ret);
                break;
            } else if (crJobResp.getJob().getStatus().equals("RUNNING") ||
                    crJobResp.getJob().getStatus().equals("DEFINED")) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception e) {
                }
                try {
                    // Get Job's status from back-end
                    ret = this.ccClient.getJob(getJobReqStr);
                } catch (Exception e) {
                }
                logger.debug("Get Job response: {}", ret);
                try {
                    crJobResp = JsonSerializeDeserialize.jsonToObject(ret, JobResponse.class);
                } catch (Exception e) {
                }
            } else {
                logger.warn("Something went badly wrong with the CreateCluster job (JobID: {})",
                        crJobResp.getJob().getJobId());
                return result;
            }
            maxWaitTime -= SLEEP_TIME;
        }

        /**
         * Either job succeeded, failed, or is taking way too long
         */
        if (maxWaitTime > 0) {
            // Job finishsed successfully
            try {
                // Thread.sleep(60*1000);
                // Make call to back-end to get cluster ID
                ret = this.ccClient.getClusterInfo(createJson);
                logger.debug("GetCluster response: {}", ret);
            } catch (Exception e) {
                logger.warn("Exception: ", e);
                return result;
            }
            ClusterResponse clusResp;
            try {
                clusResp = JsonSerializeDeserialize.jsonToObject(ret, ClusterResponse.class);
            } catch (Exception e) {
                logger.warn("Exception: ", e);
                return result;
            }
            logger.info("Cluster ID: {}", clusResp.getCluster().getClusterId());

            // Finally: Set output for the conductor task
            result.getOutputData().put("cluster_id", clusResp.getCluster().getClusterId());
            result.getOutputData().put("controller_id", clusResp.getControllerId());
        } else {
            logger.warn("Ran out of time or failed");
            return result;
        }

        result.setStatus(Status.COMPLETED);

        return result;
    }
}
