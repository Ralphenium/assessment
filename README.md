# Technical Assessment
This application is built using Spring/Spring boot and Java.


## Table of Content

- [Retail Store](#retail-store)
    - [Table of Contents](#table-of-contents)
    - [General Information](#general-information)
    - [Technologies Used](#technologies-used)
    - [Project Structure](#project-structure)
        - [Config](#config)
        - [Controller](#controller)
        - [Exception](#exception)
        - [Interceptor](#interceptor)
        - [Kafka](#kafka)
        - [Mapper](#mapper)
        - [Model](#model)
        - [Repository](#repository)
        - [Service](#service)
        - [Utils](#utils)
    - [Endpoints](#endpoints)
        - [Item Endpoints](#item-endpoints)


## General Information
### Assumption
* Docker desktop is install on the host machine [docker installation](https://www.docker.com/products/docker-desktop/)
* Docker compose should be installed, if not available in the docker desktop use [docker compose installation](https://docs.docker.com/compose/install/)
* JDK version 11+ is available on the host machine.
* Maven 3.6+ is available on the host machine

### Requirements
Database needs to be setup in dockers by running the script in the terminal using the following command, this will pull MySQl version 8 if not available in the docker 
```
docker run -p 3307:3306 --name retail_store -e MYSQL_ROOT_PASSWORD=test  --restart unless-stopped -e MYSQL_DATABASE=item_store -d mysql:8
```
### Description
The application is a spring/spring-boot project which runs in a maven environment and packaged into dockers container, MySQL database was used, the project structure is explained [here](#project-structure)  
To run the project on the host machine, the following information will be required:
#### To run the project without using docker
```run
 mvn clean spring-boot:run
```
* It runs on the default port: 8080,
* Access the application using _http://localhost:8080/swagger-ui.html_
* To access the endpoint use user as `user` the password will be printed on the console when the application starts.

To stop and start the docker container, 
```
 docker-compose up
 docker-compose down
```

The project uses a docker image called test-container for Integration testing using the db_ps.sql in the resource directory.

#### General Information

All the requirements of this assessment were worked on.


## Technologies Used
* Programming Language(s) and Frameworks
    * Java 11
    * Spring 5.0/Spring boot 2.7
* Dependencies
    * jackson-datatype-jsr310
    * commons-lang3
    * springfox-swagger-ui
    * spring-boot-starter-web
    * lombok
    * spring security
    * kafka
* Tools
    * IntelliJ IDEA
* Testing
    * Mockito
    * JUnit 5
    * TestContainer


## Project Structure

### Config
This contains the configuration files

### Controller
This contains the exposed endpoints of the project

### Exception
This contains the custom exception classes of the application such as BadRequest, ResourceNotFoundException, ApiError etc.

### Mapper
This is the package that has the Mapper class that helps with converting one class from one to another.

### Model
This package has Three(3) sub-packages:
* **Entity**
This consist of the project entity classes that is mapped to the Database.
* **DTO** 
This contains the response and request classes that is sent externally from  or to the application.

### Repository
This is the package that connects the database to the project implementation classes through the application.properties file

### Service
This contains the class implementation and the interface classes of the application, this is where the project business logic is written.

### Interceptor
This house the HttpTraceInterceptor for http traces log on the kafka topic called http-trace-topic

### Kafka
This house the HttpTraceProducer that produce into the http-trace-topic
### Utils
This contains utility classes, these classes comprise of the reusable resource.

## Endpoints

* ### Item endpoints

|               Endpoint             |     Method    |              Route                      | Payload                                 |
|------------------------------------|---------------|-----------------------------------------|-----------------------------------------|
 | Add Item | POST | items/add| ItemDTO class                  |
 | Add Item in bulk | POST | items/bulk| List ItemDTO class                  || List item | GET | items/list | page (optional), size (optional)                         |
 | List item	GET	| item/list	| page (optional), size (optional) | 
 | Update Item | POST | items/update | ItemDTO class            |
 | Delete Item | DELETE | items/{id} |            |
 | Get By ID | GET | items/{id} |            |
 | Get By Name and Price | GET | items |    params(name and price)        |


## Testing
So far different testing has been done on the application, testing such as Unit Testing and Integrated Testing.
For the Unit testing, Mockito was used to test each controller or route, the tests are written in the test folder.
Test done on this application
* Unit Testing
* Integrated Testing

