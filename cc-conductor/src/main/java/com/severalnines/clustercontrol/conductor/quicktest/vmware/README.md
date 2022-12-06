```mvn initialize```

```mvn clean package```

```export VMWARE_API_USER=testuser```

```export VMWARE_API_PASSWORD=XXX```

```java -ea -cp ../cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.vmware.ListVMs --server <> --datacenter <> --cluster <> --vmfolder <> --skip-server-verification```

e.g. ```java -ea -cp ./cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar com.severalnines.clustercontrol.conductor.quicktest.vmware.ListVMs --server 10.63.1.215 --datacenter HomeDC --cluster FirstCluster --vmfolder DBnodes --skip-server-verification``` 

```java -ea -cp ./cc-conductor-package/target/packaging-1.0.0-jar-with-dependencies.jar  com.severalnines.clustercontrol.conductor.quicktest.vmware.ListOvfTemplate --server 10.63.1.215 --username "administrator@vsphere.local" --password PASS --clustername FirstCluster --libitemname ubuntu2004 --skip-server-verification```

