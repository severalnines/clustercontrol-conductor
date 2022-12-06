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
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;

public class Ec2ListInstances {

    private static final Logger logger
            = LoggerFactory.getLogger(Ec2ListInstances.class);

    public static void main(String[] args) {

        logger.info("Starting...");
        Region region = Region.US_EAST_2;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

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
                logger.info(
                        "Instance details (ID: {}; PublicIP: {}; PrivatIP: {}; publicDNS: {}).",
                        instance.instanceId(), instance.publicIpAddress(), instance.privateIpAddress(),
                        instance.publicDnsName());
            }
        }

        ec2.close();

        logger.info("Exiting...");
    }

}
