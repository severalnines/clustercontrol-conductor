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
package com.severalnines.clustercontrol.conductor.quicktest.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.List;

public class Ec2TerminateInstance {

    private static final Logger logger
            = LoggerFactory.getLogger(Ec2TerminateInstance.class);

    public static void main(String[] args) {

        logger.info("Starting...");

//        String amiId = "ami-086f99f384efb019d";
//        String instanceName = "conductor-test";
//        String secGrpId = "sg-00e398e6545a336ea";
        Region region = Region.US_EAST_2;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
            DescribeInstancesRequest descRequest = DescribeInstancesRequest.builder()
                    .build();

            DescribeInstancesResponse response = ec2.describeInstances(descRequest);

            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    logger.info(
                            "Found instance with id {}, AMI {}, type {}, state {}, and monitoring state {}",
                            instance.instanceId(),
                            instance.imageId(),
                            instance.instanceType(),
                            instance.state().name(),
                            instance.monitoring().state());

                    if (instance.state().name() != InstanceStateName.RUNNING) {
                        continue;
                    }

                    TerminateInstancesRequest ti = TerminateInstancesRequest.builder()
                            .instanceIds(instance.instanceId())
                            .build();
                    try {
                        TerminateInstancesResponse termResponse = ec2.terminateInstances(ti);
                        List<InstanceStateChange> list = termResponse.terminatingInstances();
                        for (InstanceStateChange sc : list) {
                            logger.info("The ID of the terminated instance is {}", sc.instanceId());
                        }
                    } catch (Ec2Exception e2) {
                        logger.warn("Exception:", e2);
                    }

                }
            }
        } catch (Ec2Exception e) {
            // logger.warn(e.awsErrorDetails().errorMessage());
            logger.warn("Exception:", e);
        } finally {
            ec2.close();
        }

        logger.info("Exiting...");

    }
}
