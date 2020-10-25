# Code-challenge

This repository aims to solve a Code challenge, the instructions of the challenge are available in the Doc directory.

## Table of Contents

   * [Introduction](#repository)
   * [Services](#services)
      * [Build](#build)
      * [Run](#run)
      * [Run in docker](#run-in-docker)
      * [API definition](#api-definition)
      * [Test the service](#test-the-service)
  * [Frameworks Used](#frameworks-used)


## Introduction
The whole solution is explained and architected in the power point file in the doc folder of this repo, you can access the file from [here](https://github.com/ferry91/code-challenge/raw/main/doc/Adidas%20Coding%20Challenge.pptx) as well.
We encourage you to read that file first, before going forward with this repository.

## Services
This repository have 2 micro services:
* Subscriptions
* Subscriptions-internal

These are the requirements to run the services locally:

* Java 11
* Maven 3x

### Build
Clone this repo, open a shell in the root and perform the following:

```bash
    $ cd subscriptions
    $ mvn clean package
```

```bash
    $ cd subscriptions-internal
    $ mvn clean package
```

### Run

In order to run the services we just compiled, we need to either have a local PostreSQL database and Kafka/Zookeper installed or run them using docker compose. Following a sample docker-compose.yaml file:

```yaml
version: '3.1'

services:
  zookeeper-server:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka-server:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092:9092'
      - '9093:9093'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT
      - KAFKA_CFG_LISTENERS=CLIENT://:9092,EXTERNAL://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=CLIENT://kafka-server:9092,EXTERNAL://localhost:9093
      - KAFKA_INTER_BROKER_LISTENER_NAME=CLIENT
    depends_on:
      - zookeeper-server
  db:
    image: postgres
    ports:
      - '5432:5432'
    restart: always
    environment:
      POSTGRES_PASSWORD: root
      POSTGRES_USER: root
      POSTGRES_DB: subscriptions
```

Save it locally in a file named: docker-compose.yaml, open a shell and run:
```bash
$ docker-compose up -d
```
Once it is finished and the services are up, you can go back to the services folder and run:
```bash
$ cd subscriptions
$ java -jar -Dspring.profiles.active=local target/subscriptions-0.0.1.jar
```
```bash
$ cd subscriptions-internal
$ java -jar -Dspring.profiles.active=local target/subscriptions-internal-0.0.1.jar
```

### Run in docker
In order to make it easier to build and test, we have already pre-built docker images for both services and they are available at [Docker Hub](https://hub.docker.com/r/fernandogrgo/code_challenge/tags).

So we can use a docker-compose to built the whole system at once:
```yaml
version: '3.1'

services:
  zookeeper-server:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka-server:
    image: 'bitnami/kafka:latest'
    ports:
      - '9092:9092'
      - '9093:9093'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper-server:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT
      - KAFKA_CFG_LISTENERS=CLIENT://:9092,EXTERNAL://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=CLIENT://kafka-server:9092,EXTERNAL://localhost:9093
      - KAFKA_INTER_BROKER_LISTENER_NAME=CLIENT
    depends_on:
      - zookeeper-server
  db:
    image: postgres
    ports:
      - '5432:5432'
    restart: always
    environment:
      POSTGRES_PASSWORD: root
      POSTGRES_USER: root
      POSTGRES_DB: subscriptions
  subscriptions-service:
    image: 'fernandogrgo/code_challenge:subscriptions'
    ports:
      - '8080:8080'
    environment:
      - JAVA_OPTIONS=-Dspring.profiles.active=docker -DKAFKA_HOSTNAME=kafka-server
    depends_on:
      - kafka-server
      - db
  subscriptions-internal-service:
    image: 'fernandogrgo/code_challenge:subscriptions-internal'
    ports:
      - '8081:8080'
    environment:
      - JAVA_OPTIONS=-Dspring.profiles.active=docker -DKAFKA_HOSTNAME=kafka-server -DDB_HOSTNAME=db
    depends_on:
      - kafka-server
      - db
```
### API Definition
There is an OpenAPI swagger file that describes the API exposed int eh API folder of this repo. In order to access it quickly, you can read it on [swagger.io directly](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/ferry91/code-challenge/main/API/SubscriptionsAPI.yaml)

### Test the service
Once you have runned the services successfully you can test the API by running:

```bash
curl --location --request POST 'http://localhost:8080/subscriptions' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email": "user3633308@adidas.com",
  "firstName": "John",
  "gender": "MALE",
  "dateOfBirth": "2000-10-23",
  "consentAccepted": true,
  "newsletterId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}'
```

## Frameworks used
| Framwork                                                     | Description                                                                                        |
|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| org.springframework.boot/spring-boot-starter                 | Spring boot framework starter to build the MS                                                      |
| org.springframework.kafka/spring-kafka                       | Starter for spring kafka in order to interact with kafka cluster                                   |
| org.springframework.boot/spring-boot-starter-web             | Starter to build restful applications                                                              |
| org.springframework.boot/spring-boot-starter-undertow        | Starter to use UnderTow as the embedded servlet container                                          |
| org.springframework.boot/spring-boot-starter-data-jpa        | Starter to use spring data jpa to interact with DB                                                 |
| org.springframework.boot/spring-boot-starter-actuator        | Starter to use production-ready features to monitor, etc.                                          |
| org.springframework.boot/spring-boot-configuration-processor | Enable mapping of configuration properties to beans                                                |
| org.postgresql/postgresql                                    | PostgreSQL drivers to interact with DB                                                             |
| org.projectlombok/lombok                                     | Enable Automatic Resource Management, automatic generation of getters, setters, etc.               |
| org.modelmapper/modelmapper                                  | Library to facilitate mapping java objects to other java objects                                   |
| javax.persistence/javax.persistence-api                      | API for the management for persistence and object/relational mapping                               |
| net.logstash.logback/logstash-logback-encoder                | Logger to facilitate production monitoring (I did not finally used it in the local implementation) |
| io.springfox/springfox-swagger2                              | Set of annotations to define API interfaces                                                        |
| io.springfox/springfox-swagger-ui                            | Set of annotations to define API interfaces                                                        |
| javax.xml.bind/jaxb-api                                      | Used to unmarshall and process api interfaces.                                                     |
| com.fasterxml.jackson.datatype/jackson-datatype-jsr310       | Add-on module to support JSR-310 (Java 8 Date & Time API) data types(used with the above)          |
| org.openapitools/jackson-databind-nullable                   | Utilities to API interfaces                                                                        |
| org.hibernate/hibernate-validator                            | Used for bean validations annotations                                                              |
