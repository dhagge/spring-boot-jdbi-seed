# Spring Boot + JDBI 

## Description

This is a seed project for building a RESTful API with Spring Boot and JDBI:

* Java 8
* Spring Boot
* MySql
* Checkstyle
* Findbugs

It also features:

* Integration tests
* Seperate prod liquibase scripts which are tested as part of the integration tests

## Install MySql
The seed project uses MySQL 5.7+ and can be installed quite easily on mac, linux or windows following
this [guide](http://dev.mysql.com/doc/refman/5.7/en/installing.html)

# Command Line #

Typical usage is to:

* Build the project (note: this will create the test and integration-test databases in mysql)
* Run the liquibase schema upgrade
* Run the liquibase db seed

## Build the project

```
gradlew clean install
```

This command will run the build, unit tests and integration tests.

## Run liquibase schema upgrade

```
gradlew dbUpdate
```

## Run the liquibase db seed script

```
gradlew dbSeed
```

## Running the API

Start the service by running the following command:

```
gradlew bootRun
```

You can now consume the service endpoints which will run at ```http://localhost:9090```.

The in-built test resource (com.myco.TestResource) is available at:

* http://127.0.0.1:9090/test (GET and POST)

## Tutorial

See [this tutorial](http://damianhagge.com/blog/2014/09/29/spring-boot-jdbi/) for further info as it highlights the bare-bones changes needed just to integrate Spring Boot + JDBI.
