# Spring Boot, MySQL, JPA, Hibernate Rest API Tutorial

Build Restful CRUD API for a simple Student application using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 5.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/philemongloblehi/spring-boot-mysql-rest-api-masterclass.git
```

**2. Create Mysql database**
```bash
create database studentDB
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Build and run the app using maven**

```bash
mvn package
java -jar target/students-1.0.0.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/v1/rest/students
    
    POST /api/v1/rest/students
    
    GET /api/v1/rest/students/{id}
    
    PUT /api/v1/rest/students/{id}
    
    DELETE /api/v1/rest/students/{id}

You can test them using postman or any other rest client.

## Learn more

You can find the tutorial for this application on my github -

<https://github.com/philemongloblehi/>
