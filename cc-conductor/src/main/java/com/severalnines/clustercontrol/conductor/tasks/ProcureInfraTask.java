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
import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraInput;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProcureInfraTask extends AbstractConductorTask {

    private static final Logger logger
            = LoggerFactory.getLogger(ProcureInfraTask.class);

    public ProcureInfraTask(String taskDefName) {
        super(taskDefName);
    }

    @Override
    public TaskResult execute(Task task) {

        logger.info("Starting task...");

        TaskResult result = new TaskResult(task);
        result.setStatus(Status.FAILED);

        List<CreateInfraInput> deploySpecs;
        try {
            deploySpecs = getDepoloymentSpecifications(task);
        } catch (Exception e) {
            logger.warn("Exception: ", e);
            return result;
        }

        ArrayList<CreateInstanceWorker> wList = new ArrayList<>();
        for (int i = 0; i < deploySpecs.size(); i++) {
            // ***********
            // Spawn workers to create instance and wait for all of them to complete
            // ***********
            CreateInstanceWorker w = new CreateInstanceWorker(deploySpecs.get(i));
            w.start();
            wList.add(w);
        }

        // *********
        // Block until create-instance-workers complete
        // *********
        for (int i = 0; i < wList.size(); ) {
            CreateInstanceWorker w = wList.get(i);
            try {
                w.join(5000);
                if (!w.isAlive()) {
                    i++;
                }
            } catch (InterruptedException e) {
                i++;
            }
        }

        // *********
        // Setup Workflow Output
        // *********
        ArrayList<CreateInfraOutput> responseData = new ArrayList<>();
        for (int i = 0; i < wList.size(); i++) {
            CreateInstanceWorker w = wList.get(i);
            responseData.add((CreateInfraOutput) w.getResponseData());
        }

        result.getOutputData().put("hosts", responseData);
        logger.debug("Num hosts: {}", responseData.size());
        try {
            logger.debug("Hosts: {}", JsonSerializeDeserialize.objectToJson(responseData));
        } catch (Exception e) {
        }

        result.setStatus(Status.COMPLETED);

        return result;
    }

    private List<CreateInfraInput> getDepoloymentSpecifications(Task task) throws Exception {
        String infraProvider = task.getInputData().get("infra_provider").toString();
        logger.info("infra_provider: {}", infraProvider);
        String dbPlan = task.getInputData().get("plan").toString();
        logger.info("plan: {}", dbPlan);
        String numDbNodes = task.getInputData().get("nodes").toString();
        logger.info("nodes: {}", numDbNodes);
        String vCPUs = task.getInputData().get("vcpus").toString();
        logger.info("vcpus: {}", vCPUs);
        String systemRAM = task.getInputData().get("ram").toString();
        logger.info("ram: {}", systemRAM);
        String volumeSize = task.getInputData().get("volume_size").toString();
        logger.info("volume_size: {}", volumeSize);
        String clusterName = task.getInputData().get("cluster_name").toString();
        logger.info("cluster_name use for tagging: {}", clusterName);

        Object infraProviders = task.getInputData().get("infra_providers");

        if (infraProviders == null &&
                (infraProvider == null || infraProvider.length() == 0)) {
            // ERROR
            logger.error("Infra provider not specified");
            throw new Exception("Input error: Infra providers unspecified");
        }

        CreateInfraInput root = new CreateInfraInput();
        // clusterName, infraProvider, vCPUs, systemRAM, volumeSize
        root.setClusterName(clusterName);
        root.setInfraProvider(infraProvider);
        root.setVcpus(vCPUs);
        root.setRam(systemRAM);
        root.setVolumeSize(volumeSize);

        List<CreateInfraInput> childSpecs = new ArrayList<>();

        int numHosts = 0;
        if (numDbNodes != null && numDbNodes.length() > 0) {
            try {
                numHosts = Integer.parseInt(numDbNodes);
            } catch (Exception e) {
                logger.warn("Exception: ", e);
                throw new Exception("Unable to parse host quantity.");
            }
        }

        ArrayList<Object> infraP = null;
        if (infraProviders != null) {
            try {
                infraP = (ArrayList<Object>) infraProviders;
                if (infraP.size() > numHosts) {
                    numHosts = infraP.size();
                }

                for (Object o : infraP) {
                    LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) o;
                    // childHosts.add(item);
                    CreateInfraInput childInfra = new CreateInfraInput();
                    childInfra.setClusterName(root.getClusterName());
                    try {
                        childInfra.setInfraProvider((String) item.get("provider"));
                    } catch (Exception castExp) {
                        // throw new Exception(castExp); // don't throw!!!
                    }
                    try {
                        childInfra.setVcpus(((Integer) item.get("vcpus")).toString());
                    } catch (Exception castExp) {
                        // throw new Exception(castExp); // don't throw!!!
                    }
                    try {
                        childInfra.setRam(((Integer) item.get("ram")).toString());
                    } catch (Exception castExp) {
                        // throw new Exception(castExp); // don't throw!!!
                    }
                    try {
                        childInfra.setVolumeSize(((Integer) item.get("volume_size")).toString());
                    } catch (Exception castExp) {
                        // throw new Exception(castExp); // don't throw!!!
                    }

                    childSpecs.add(childInfra);
                }

            } catch (Exception e) {
                logger.warn("Exception:", e);
                throw new Exception(e);
            }
        }

        if (numHosts == 0) {
            logger.error("Num DB hosts is {}", 0);
            throw new Exception("Invalid number of DB hosts: 0");
        }

        List<CreateInfraInput> deploySpecs = new ArrayList<>();

        for (int i = 0; i < numHosts; i++) {
            String provider = root.getInfraProvider();
            String vCpus = root.getVcpus();
            String sysRam = root.getRam();
            String volSz = root.getVolumeSize();
            if (childSpecs.size() > i) {
                CreateInfraInput h = childSpecs.get(i);
                String p = h.getInfraProvider();
                provider = (p != null && p.length() > 0) ? p : provider;

                String vc = h.getVcpus();
                vCpus = (vc != null && vc.length() > 0) ? vc : vCpus;

                String ram = h.getRam();
                sysRam = (ram != null && ram.length() > 0) ? ram : sysRam;

                String vs = h.getVolumeSize();
                volSz = (vs != null && vs.length() > 0) ? vs : volSz;
            }
            assert (provider != null);
            // Eventually we would want to uncomment the following to enforce input error checking
            // assert (vCpus!=null);
            // assert (sysRam!=null);
            // assert (volSz!=null);
            CreateInfraInput infraEntry = new CreateInfraInput();
            infraEntry.setClusterName(root.getClusterName());
            infraEntry.setInfraProvider(provider);
            infraEntry.setVcpus(vCpus);
            infraEntry.setRam(sysRam);
            infraEntry.setVolumeSize(volSz);
            deploySpecs.add(infraEntry);
        }

        return deploySpecs;
    }

}
