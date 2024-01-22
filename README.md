# monitoring-app

Implementation of a simple application framework for monitoring service availability on a collection of servers. The monitoring checks similarly to what is done in the micro service architecture whether there is communication with the server via http.
Another option would be to just check if a server is available at all with a given IP address or DNS domain (e.g. InetSocketAddress class).
However, I chose to check additionally the REST service, because it is more practical and this is what today's microservice monitoring tools do.

To run the application:

### Build monitoringapp-backens

```shell
cd $PROJEKT_PATH/monitoringapp-backens
./gradlew build
./gradlew runBoot
```

### Build monitoringapp-frontend
```shell
cd $PROJEKT_PATH/monitoringapp-frontend
npm install
npm start
```
