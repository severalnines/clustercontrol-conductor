1. Build top-level project.
```mvn clean package```

2. Setup env
``` export CC_URL=https://10.63.1.217:9501/v2```

``` export CC_API_USER=<user>```

``` export CC_API_USER_PW=<user's pw>```

3. Run test
```java -cp ./cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.cc.CcTest```


