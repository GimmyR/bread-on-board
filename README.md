# Bread on Board

[![Bread on Board - CI Pipeline](https://github.com/GimmyR/bread-on-board/actions/workflows/ci.yaml/badge.svg)](https://github.com/GimmyR/bread-on-board/actions/workflows/ci.yaml)

Bread on Board is a web application that lets users create, manage, and explore cooking recipes.

It is built with:

- **Next.js** for the frontend  
- **Spring Boot** for the backend API  
- **Oracle Database** for data persistence  
- **Docker** for containerized deployment

![Home page](./screenshots/home.png)

![Recipe page](./screenshots/one-recipe.png)


## Prerequisites

Before building or running the application, make sure you have the following installed :

* **Docker** 29.0.2
* **Docker Compose** 2.40.3

## Environment variables

```bash
# Database (Oracle)
DB_USER=SYSTEM
DB_PASSWORD=your_db_password

# Administrator account (created at startup)
ADMIN_USERNAME=admin
ADMIN_MAIL_ADDRESS=admin@example.com
ADMIN_PASSWORD=change_me

# Password hashing strength (bcrypt cost)
PASSWORD_STRENGTH=12

# JWT (HS512 requires at least 64 bytes)
JWT_SECRET=your_very_long_random_secret_key_here_at_least_64_bytes

# Frontend / CORS / API URLs
FRONTEND_URL=http://localhost:3000
NEXT_PUBLIC_CLIENT_SIDE_TO_API=http://localhost:8080
```

If you want to use a `.env` file, place it in the project's root directory.

## Launch the application

Open a terminal in the project's root directory and run the following command :

```bash
docker compose --profile dev up --build
```

You can access the frontend application in your browser at http://localhost:3000.

The API documentation is available at http://localhost:8080/docs.

To sign in as an administrator for the backend, open http://localhost:8080 in your browser.

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.