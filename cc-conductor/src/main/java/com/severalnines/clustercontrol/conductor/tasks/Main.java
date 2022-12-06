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

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.severalnines.clustercontrol.api.abstraction.client.ClusterControlClient;
import com.severalnines.clustercontrol.api.abstraction.client.SimpleAuthenticationStrategy;
import com.severalnines.clustercontrol.api.abstraction.common.AbstractAuthenticationStrategy;
import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Main {

    private static final Logger logger
            = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        logger.info("Starting...");

        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/"); // Point this to the server API

        // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers
        int threadCount = 2;

        JsonSerializeDeserialize.SetSnakeNaming();

        // Task to get VM
        Worker worker1 = new ProcureInfraTask("procure_infra_task");

        // Task to deploy DB cluster on VM from previous step
        SimpleAuthenticationStrategy authStrategy = new SimpleAuthenticationStrategy();
        // AbstractAuthenticationStrategy.TurnOnDebug();
        // AbstractAuthenticationStrategy.TurnOffDebug();
        ClusterControlClient ccClient = new ClusterControlClient(authStrategy);
        DeployDbClusterTask worker2 = new DeployDbClusterTask("deploy_db_cluster_task", ccClient);
        worker2.authenticate();

        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(worker1, worker2))
                // new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(worker1))
                        .withThreadCount(threadCount)
                        .build();

        // Start the polling and execution of tasks
        configurer.init();

    }
}
