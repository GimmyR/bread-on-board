# Bread on Board (Backend)

This is the backend and API for the entire Bread on Board project, built with Spring Boot.
You can build and run this application on its own, but the following configurations are required.

## Prerequisites

Before building or running the application, make sure you have the following installed :

* **Java** 21.0.9
* **Maven** 3.9.9
* **Oracle Database**

## Environment variables

First, define the environment variables for the database :

* **DB_URL**: The JDBC URL to connect to your Oracle Database (e.g., `jdbc:oracle:thin:@localhost:1521/XEPDB1`)
* **DB_USER**: The username to connect to your Oracle Database (e.g., `SYSTEM`)
* **DB_PASSWORD**: The password for your Oracle Database

Second, the application generates an administrator account in your database and requires the following environment variables :

* **ADMIN_USERNAME**: The administrator's username (e.g., `admin`)
* **ADMIN_MAIL_ADDRESS**: The administrator's email address (e.g., `admin@example.com`)
* **ADMIN_PASSWORD**: The administrator's password

Third, the application hashes passwords according to a defined password strength :

* **PASSWORD_STRENGTH**: Recommended value is **12**

Fourth, the application generates JSON Web Tokens using a secret key :

* **JWT_SECRET**: 64 bytes (required for `HS512` algorithm)

Finally, if you want another application to communicate with the API :

* **FRONTEND_URL**: Defines the allowed CORS origin

If you want to use a `.env` file, place it in the project's root directory.

## Installation

Open a terminal in the project's root directory and navigate to the backend folder :

```bash
cd backend/
```

Then, run **Maven**'s `clean` and `install` commands :

```bash
mvn clean install
```

To skip tests during the build, use :

```bash
mvn clean install -DskipTests
```

## Launch the application

To run the application, execute the following command :

```bash
java -jar target/*.jar
```

The API documentation is available at http://localhost:8080/docs.
To sign in as an administrator for the backend, open http://localhost:8080 in your browser.
