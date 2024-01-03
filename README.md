# WeGather

A simple web application to manage appointments and social events. Developed for the ["Design Lab" course @ UniMib](https://elearning.unimib.it/course/info.php?id=44590&lang=en)

Contributors:

Bonomo Mirco \
Sparacino Gabriele 


## Documentation
Technical documentation (in italian :( ) is available [here](http://g.sparacino3.gitlab.io/wegather/)

## Build & Run

The project can be compiled and executed by either installing Java 11 and Maven or using Docker.

In any case, the executable jar contains an in-memory database ([H2](https://www.h2database.com/html/main.html)), which starts and stops concurrently with the application, so there's no need to setup an external database.


#### Java 11 + Maven

1. Install [JDK 11](https://adoptium.net/?variant=openjdk11) (or higher) on your system
2. Create an environment variable named `JAVA_HOME`, with its value being the absolute path of the JDK installation folder
3. The chosen tool for compiling the source code for the project is [Apache Maven](https://maven.apache.org/), version 3.8.3. Compilation can be performed in two ways:
    * Installing Maven on your computer and running `mvn clean package` in the root folder of the project
    * Using the Maven wrapper included in the project's files, by running `./mvnw clean package` in the root folder of the project
4. The project was developed using the [Spring Boot](https://spring.io/projects/spring-boot) framework, and the compilation produces a single executable `jar` file (available in the /target folder created by Maven at the end of the `compile` phase). The application can then be started by running `java -jar target/<filename>`.
5. The application's front-end should then be accessible using a browser at `localhost:8080`.


#### Docker

1. Install Docker on your system (e.g.: [Docker Desktop](https://www.docker.com/products/docker-desktop))
2. Create the Docker image with `docker build -t <image name> .`, where `image name` is going to become the name/label of the output image
3. Start the image with`docker run -p 8080:8080 <image name>`, using the same `image name` provided for the `docker build` command
4. The application's front-end should then be accessible using a browser at `localhost:8080`.
