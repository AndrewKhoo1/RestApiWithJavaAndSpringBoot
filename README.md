# RestApiWithJavaAndSpringBoot
a RESTful Api expense tracker with JWT authentication and password encryption with BCrypt using Spring Boot, Java, a PostgreSQL Docker container, and a javascript web client with CORS enabled.

**Setup steps**

Clone the repo from GitHub using: 

    git clone https://github.com/AndrewKhoo1/RestApiWithJavaAndSpringBoot.git

Cd into the main directory using: 

    cd expense-tracker-api

Run a Postgres Docker container using:

    docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d -p 5432:5432 postgres

Copy the SQL script file (expensetracker_db.sql) to the running container using the below command and then exec into the running container:

    docker container cp expensetracker_db.sql postgresdb:/
    docker container exec -it postgresdb bash

Run the SQL script using the psql client to build database objects:

    psql -U postgres --file expensetracker_db.sql

Run the spring boot application using:

    ./mvnw spring-boot:run

Api will now start on "http://localhost:8080"

Run index.html from the main directory to start the javascript web client and view the results of your requests in the console
