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
package com.severalnines.clustercontrol.conductor.infraprovider.aws;

import com.severalnines.clustercontrol.api.abstraction.common.JsonSerializeDeserialize;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraInput;
import com.severalnines.clustercontrol.conductor.pojo.CreateInfraOutput;
import com.severalnines.clustercontrol.conductor.infraprovider.InfraProviderBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class Ec2Instance extends InfraProviderBase {

    private static final Logger logger
            = LoggerFactory.getLogger(Ec2Instance.class);

    @Override
    public String createInstance(String jsonStr) throws Exception {
        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);
        String ret = null;

        // logger.info("Starting...");
        String amiId = "ami-086f99f384efb019d";
        String instanceName = in.getClusterName(); // "conductor-test";
        String secGrpId = "sg-00e398e6545a336ea";
        Region region = Region.US_EAST_2;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
            RunInstancesRequest runRequest = RunInstancesRequest.builder()
                    .imageId(amiId)
                    .securityGroupIds(secGrpId)
                    .instanceType(InstanceType.T3_A_SMALL)
                    // .instanceType(InstanceType.T3_A_MICRO)
                    .maxCount(1)
                    .minCount(1)
                    .build();

            RunInstancesResponse response = ec2.runInstances(runRequest);
            Instance instance = response.instances().get(0);
            String instanceId = instance.instanceId();
            String publicIp = instance.publicIpAddress();
            String privateIp = instance.privateIpAddress();
            String publicDnsName = instance.publicDnsName();
            logger.info("Createed instance (ID: {}; PublicIP: {}; PrivatIP: {}; publicDNS: {}).",
                    instanceId, publicIp, privateIp, publicDnsName);
            Tag tag = Tag.builder()
                    .key("Name")
                    .value(instanceName)
                    .build();

            CreateTagsRequest tagRequest = CreateTagsRequest.builder()
                    .resources(instanceId)
                    .tags(tag)
                    .build();

            ec2.createTags(tagRequest);

            logger.info("Successfully started EC2 Instance {} based on AMI {}", instanceId, amiId);

            CreateInfraOutput out = new CreateInfraOutput();
            out.setInternalVmId(instanceId);
            ret = JsonSerializeDeserialize.objectToJson(out);

        } catch (Ec2Exception e) {
            logger.warn("Exception in Create EC2 instance AWS API: ", e);
        } finally {
            ec2.close();
        }

        return (ret);
    }

    @Override
    public String listInstance(String jsonStr) throws Exception {
        CreateInfraInput in = JsonSerializeDeserialize.jsonToObject(jsonStr, CreateInfraInput.class);
        String ret = null;

        Region region = Region.US_EAST_2;
        Ec2Client ec2 = Ec2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        try {
            logger.info("Waiting to get IP address....");

            boolean hasIp = false;
            long max_wait_time_ms = 2 * 60 * 1000;
            final long SLEEP_TM_MS = 5 * 1000;
            DescribeInstancesRequest descRequest = DescribeInstancesRequest.builder()
                    .instanceIds(in.getInternalVmId())
                    .build();
            String instPublicIP = null;
            String instPrivateIP = null;
            while (!hasIp && max_wait_time_ms > 0) {
                DescribeInstancesResponse listResponse = ec2.describeInstances(descRequest);
                Instance listInstance = listResponse.reservations().get(0).instances().get(0);

                logger.debug("Instance details: (ID: {}; PublicIP: {})",
                        in.getInternalVmId(), listInstance.publicIpAddress());

                if (listInstance.state().name() == InstanceStateName.RUNNING &&
                        listInstance.publicIpAddress().length() > 0) {
                    instPublicIP = listInstance.publicIpAddress();
                    instPrivateIP = listInstance.privateIpAddress();
                    hasIp = true;
                } else {
                    try {
                        Thread.sleep(SLEEP_TM_MS);
                        max_wait_time_ms -= SLEEP_TM_MS;
                    } catch (Exception ex) {
                        // logger.warn("Exception in sleep: {}", ex);
                        logger.warn("Exception in sleep: ", ex);
                    }
                }
            }

            if (hasIp) {
                logger.info("Instance publicIP: {}; privateIP: {}", instPublicIP, instPrivateIP);
                CreateInfraOutput out = new CreateInfraOutput();
                out.setInternalVmId(in.getInternalVmId());
                out.setHostname(instPublicIP);
                out.setPublicIp(instPublicIP);
                out.setPrivateIp(instPrivateIP);
                ret = JsonSerializeDeserialize.objectToJson(out);
            }

        } catch (Ec2Exception e) {
            logger.warn("Exception in List Ec2 instance AWS API: ", e);
        } finally {
            ec2.close();
        }

        return ret;
    }
}
