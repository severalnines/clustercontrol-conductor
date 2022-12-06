1. Build top-level project.

```mvn clean package```

2. Setup env

```AWS_REGION=<region>```
```AWS_ACCESS_KEY_ID=<your-access-key>```
```AWS_SECRET_ACCESS_KEY=<your-secret-key>```

3. Run test

```java -cp ./cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.aws.Ec2ListInstances```

