# Bread on Board

[![Bread on Board - CI Pipeline](https://github.com/GimmyR/bread-on-board/actions/workflows/ci.yaml/badge.svg)](https://github.com/GimmyR/bread-on-board/actions/workflows/ci.yaml)

Bread on Board is a web application that lets users create, manage, and explore cooking recipes.

It is built with:

- **Next.js** for the frontend  
- **Spring Boot** for the backend API  
- **Oracle Database** for data persistence  
- **Docker** for containerized deployment

## Prerequisites

Before building or running the application, make sure you have the following installed :

* **Docker** 29.0.2
* **Docker Compose** 2.40.3

## Environment variables

First, define the environment variables for the database :

* **DB_USER**: The username to connect to your Oracle Database (e.g., `SYSTEM`)
* **DB_PASSWORD**: The password for your Oracle Database

Second, the application generates an administrator account in your database and requires the following environment variables :

* **ADMIN_USERNAME**: The administrator's username (e.g., `admin`)
* **ADMIN_MAIL_ADDRESS**: The administrator's email address (e.g., `admin@example.com`)
* **ADMIN_PASSWORD**: The administrator's password

Third, the application hashes passwords according to a defined password strength :

* **PASSWORD_STRENGTH**: Recommended value is **12**

Fourth, the application encodes and decodes JSON Web Tokens using a secret key :

* **JWT_SECRET**: 64 bytes (required for `HS512` algorithm)

Finally, if you want your frontend application to communicate with the API :

* **FRONTEND_URL**: Defines the allowed CORS origin for cross-origin requests
* **NEXT_PUBLIC_SERVER_SIDE_TO_API**: Defines the API url used by the server-side of the Next.js application
* **NEXT_PUBLIC_CLIENT_SIDE_TO_API**: Defines the API url used by the client-side of the Next.js application

If you want to use a `.env` file, place it in the project's root directory.

## Launch the application

Open a terminal in the project's root directory and run the following command :

```bash
docker compose up --build
```

You can access the frontend application in your browser at http://localhost:3000.

The API documentation is available at http://localhost:8080/docs.

To sign in as an administrator for the backend, open http://localhost:8080 in your browser.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.