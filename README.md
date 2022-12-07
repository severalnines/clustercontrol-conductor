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
  - [Running conductor workflow and tasks](#running-conductor-workflow-and-tasks)
  
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

## Orchestrate database deployment on VMware

### Conductor workflow and task definitions

### VMware infrastructure task definition

### Database deployment task definition

### Running conductor workflow and tasks

