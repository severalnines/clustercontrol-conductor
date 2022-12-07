# ClusterControl for Conductor - Orchestrating database deployment using ClusterControl and Netflix Conducor
Facets of database automation and lifecycle management in hybrid and multi-cloud environments using ClusterControl and Netflix Conductor.

Part I  - Procuring infrastructure from VMware, AWS, GCP, Azure, Vultr, DigitalOcean, etc

Part II - Database automation by ClusterControl

The glu, i.e., the orchestration of the different parts is accomplished using Conductor (Orchestrator)

## Table of Contents
- [Setting up prerequisites](#setting-up-prerequisites)
  - [Setting up Maven](#setting-up-maven)
  - [Setting up VMware Java SDK](#setting-up-vmware-java-sdk)
  - [Setting up ClusterControl Java SDK](#setting-up-clustercontrol-java-sdk)
- [Building Conductor tasks](#building-conductor-tasks)
- [Setting up Netflix Conductor](#setting-up-netflix-conductor)
- [Orchestrate database deployment on VMware](#orchestrate-database-deployment-on-vmware)
  - [Conductor workflow and task definitions](#conductor-workflow-and-task-definitions)
  - [VMware infrastructure task definition](#vmware-infrastructure-task-definition)
  - [Database deployment task definition](#database-deployment-task-definition)
  - [Setting up conductor workflow and tasks](#setting-up-conductor-workflow-and-tasks)
  - [Kicking off conductor workflow and tasks](#kicking-off-conductor-workflow)
  
## Setting up prerequisites

### Setting up Maven
Apache Maven is required to build software from this repository. Here are instructions on how to setup Maven.
1. Download the latest maven from <https://maven.apache.org/download.cgi> and extract it to your machine.
2. Install JDK 8 and set JAVA_HOME to the directory where JDK is installed.
   ```` bash
   export JAVA_HOME=<jdk-install-dir>
   ````
3. Update PATH environment variable to include the maven and jdk "bin" directories.
   ```` bash
   export PATH=<maven-bin-dir>:$JAVA_HOME/bin:$PATH
   ````

### Setting up VMware Java SDK
VMware client SDK for Java is required to build and run software from this repository. Here are instructions on how to set it up.
   ```` bash
   git clone https://github.com/premnalla/vsphere-automation-sdk-java.git
   cd vsphere-automation-sdk-java
   mvn initialize
   mvn -DskipTests clean install
   ````

### Setting up ClusterControl Java SDK
ClusterControl's client API SDK is also required to build and run software from this repository. Here are instructions on how to set it up.
   ```` bash
   git clone https://github.com/severalnines/clustercontrol-client-sdk.git
   cd clustercontrol-client-sdk/java
   mvn -DskipTests clean install
   ````

## Building Conductor tasks
In order to build the software in this repsitory, please run the following commands.

   ```` bash
   git clone https://github.com/severalnines/clustercontrol-conductor.git
   cd clustercontrol-conductor
   mvn -DskipTests clean package
   ````

## Setting up Netflix Conductor
Instructions on how to setup Netflix Conductor from sources are avaiable at the link below.
([Building and Running Netflix Conductor from sources](https://conductor.netflix.com/gettingstarted/source.html)]

## Orchestrate database deployment on VMware
Once you have the software from this repository built and Netflix Conductor running, you can start creating Conductor workflows consisting of tasks. For the time being, we will demonstrate a workflow consiting of two tasks namely.

1. Obtain a virtual machine (VM) from VMware vSphere
2. Setup a MySQL database on the VM from the previous step

### Conductor workflow and task definitions
Here's the workflow to accomplish the overall goal of deploying a database on the host procured from VMware.

   ```` bash
   {
  "name": "create_database_cluster",
  "description": "A workflow to procure infrastructure form vmware and then deploy a specified DB cluster on that infra.",
  "version": 1,
  "schemaVersion": 2,
  "tasks": [
    {
      "name": "procure_infra_task",
      "taskReferenceName": "procure_infra_task_ref",
      "inputParameters": {
        "cluster_name": "${workflow.input.cluster_name}",
        "infra_provider": "${workflow.input.infra_provider}",
        "infra_providers": "${workflow.input.infra_providers}",
        "plan": "${workflow.input.plan}",
        "nodes": "${workflow.input.nodes}",
        "vcpus": "${workflow.input.vcpus}",
        "ram": "${workflow.input.ram}",
        "volume_size": "${workflow.input.volume_size}",
        "tags": "${workflow.input.cluster_name}"
      },
      "type": "SIMPLE"
    },
    {
      "name": "deploy_db_cluster_task",
      "taskReferenceName": "deploy_db_cluster_task_ref",
      "inputParameters": {
        "plan": "${workflow.input.plan}",
        "nodes": "${workflow.input.nodes}",
        "cluster_name": "${workflow.input.cluster_name}",
        "cluster_type": "${workflow.input.cluster_type}",
        "vendor": "${workflow.input.vendor}",
        "version": "${workflow.input.version}",
        "port": "${workflow.input.port}",
        "admin_user": "${workflow.input.admin_user}",
        "admin_pw": "${workflow.input.admin_pw}",
        "hosts": "${procure_infra_task_ref.output.hosts}"
      },
      "type": "SIMPLE"
    }
  ],
  "inputParameters": [],
  "outputParameters": {
    "cluster_id": "${deploy_db_cluster_task_ref.output.cluster_id}",
    "controller_id": "${deploy_db_cluster_task_ref.output.controller_id}"
  },
  "failureWorkflow": "deploy_database_issues",
  "restartable": true,
  "workflowStatusListenerEnabled": true,
  "ownerEmail": "prem@severalnines.com",
  "timeoutPolicy": "ALERT_ONLY",
  "timeoutSeconds": 0,
  "variables": {},
  "inputTemplate": {
    "plan": "Startup_1c_2gb",
    "nodes": 1,
    "vcpus": 1,
    "ram": 2,
    "volume_size": 40,
    "infra_provider": "vmware",
    "infra_providers": [
      {
        "provider": "vmware"
      }
    ],
    "cluster_name": "conductor-cluster",
    "cluster_type": "replication",
    "vendor": "Percona",
    "version": "8.0",
    "port": 3306,
    "admin_user": "root",
    "admin_pw": "u5iw9sTv$qU2$"
  }
}
   ````

### VMware infrastructure task definition
Here's the definition for the task that will procure a VM from VMware vSphere.

   ```` bash
   {
  "name": "procure_infra_task",
  "description": "The task to procure infrastructure",
  "retryCount": 0,
  "inputKeys": [
    "cluster_name",
    "infra_provider",
    "infra_providers",
    "plan",
    "nodes",
    "vcpus",
    "ram",
    "volume_size",
    "tags"
  ],
  "outputKeys": [
    "hosts"
  ],
  "timeoutSeconds": 0,
  "timeoutPolicy": "TIME_OUT_WF",
  "retryLogic": "FIXED",
  "retryDelaySeconds": 60,
  "responseTimeoutSeconds": 3600,
  "inputTemplate": {
    "cluster_name": "mysql-replication",
    "infra_provider": "vmware",
    "infra_providers": [
      {
        "provider": "vmware"
      }
    ],
    "plan": "Startup_1c_2gb",
    "nodes": 1,
    "vcpu": 1,
    "ram": 2,
    "volume_size": 40,
    "tags": "mysql-replication"
  },
  "rateLimitPerFrequency": 0,
  "rateLimitFrequencyInSeconds": 1,
  "ownerEmail": "prem@severalnines.com",
  "backoffScaleFactor": 1
}
   ````

### Database deployment task definition
Here's the definition for the task that will deploy a MySQL database on the VM procured from VMware.

   ```` bash
   {
  "name": "deploy_db_cluster_task",
  "description": "The task to deploy database cluster on infrastructure",
  "retryCount": 0,
  "inputKeys": [
    "plan",
    "nodes",
    "cluster_name",
    "cluster_type",
    "vendor",
    "version",
    "port",
    "admin_user",
    "admin_pw",
    "hosts"
  ],
  "outputKeys": [
    "cluster_id",
    "controller_id"
  ],
  "timeoutSeconds": 0,
  "timeoutPolicy": "TIME_OUT_WF",
  "retryLogic": "FIXED",
  "retryDelaySeconds": 60,
  "responseTimeoutSeconds": 3600,
  "inputTemplate": {
    "plan": "startup_1c_2gb_40gb",
    "nodes": 1,
    "cluster_name": "mysql-replication-percona-8-conductor-test",
    "cluster_type": "replicaiton",
    "vendor": "Percona",
    "version": "8.0",
    "port": 3306,
    "admin_user": "root",
    "admin_pw": "u5iw9sTv$qU2$",
    "hosts": [
      {
        "hostname": "10.0.0.4"
      }
    ]
  },
  "rateLimitPerFrequency": 0,
  "rateLimitFrequencyInSeconds": 1,
  "ownerEmail": "prem@severalnines.com",
  "backoffScaleFactor": 1
}
   ````

### Setting up conductor workflow and tasks
Now run the application developed to here that will in essence be the backing software to accomplish the tasks in the Conductor workflow defined above.
   ```` bash
   java -cp ./cc-conductor-package/target/cc-conductor-package-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.tasks.Main
   ````

### Kicking off conductor workflow
Kick off the conductor workflow with the following input.
   ```` bash
   {
  "infra_provider": "vmware",
  "plan": "startup_1c_2gb_40gb",
  "nodes": 1,
  "vcpus": 1,
  "ram": 2,
  "volume_size": 40,
  "cluster_name": "mysql-replication",
  "cluster_type": "replication",
  "vendor": "percona",
  "version": "8.0",
  "port": 3306,
  "admin_user": "root",
  "admin_pw": "u5iw9sTv$qU2$",
  "infra_providers": [
    {
      "provider": "vmware"
    }
  ]
}
   ````
