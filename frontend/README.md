# Bread on Board (Frontend)

This is the frontend for the entire Bread on Board project, built with Next.js.
You can build and run this application on its own, but the following configurations are required.

## Prerequisites

Before building or running the application, make sure you have the following installed :

* **Node** 20.18
* **NPM** 11.6

## Environment variables

First, the application decodes JSON Web Tokens using a secret key:

* **JWT_SECRET**: 64 bytes (required for the `HS512` algorithm)

Second, to allow the application to communicate with an API:

* **NEXT_PUBLIC_SERVER_SIDE_TO_API**: Defines the API url used by the server-side of the Next.js application
* **NEXT_PUBLIC_CLIENT_SIDE_TO_API**: Defines the API url used by the client-side of the Next.js application

If you want to use a `.env` file, place it in the project's root directory.

## Dependencies

Open a terminal in the project's root directory and navigate to the frontend folder :

```bash
cd frontend/
```

Then, install the dependencies by running :

```bash
npm install
```

## Launch the application

Always in frontend folder, start the application by running:

```bash
npm run dev
```

You can access the application in your browser at `http://localhost:3000`.