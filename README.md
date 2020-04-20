# ShortenerURL-Quarkus

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `shortenerurl-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/hello-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/shortenerurl-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Running using docker-compose

### Native:

To running the native image you need to create a native executable using the step Creating a native executable and then
`cd deps/native` and use `docker-compose build` to build an image of the project. Then `docker-compose up -d`
to bring up a mongo container and then the project container using the native image.

### Using Jar:
To running without a native image you need to create the package using the Packaging and running the application step.
Then `cd deps` and use `docker-compose` build to build an image of the project. Then `docker-compose up -d`
to bring up a mongo container and then the project container using the openjdk11 with jar.
In this version it's possible to add newrelic monitoring editing the docker-compose.yml and 
put in -Dnewrelic.config.license_key your license key.
