1. Build top-level project.

```mvn clean package```

2. Setup env

```export VULTR_API_KEY=<yourkey>```

3. Run test

```java -cp ./cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.vultr.VultrVps```

