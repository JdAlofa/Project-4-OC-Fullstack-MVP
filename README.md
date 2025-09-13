# MDD - Full-Stack Application

This repository contains the source code for the MDD (Meet, Discover, Discuss) full-stack application, featuring an Angular frontend and a Spring Boot backend.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or later
- [Maven](https://maven.apache.org/download.cgi)
- [Node.js and npm](https://nodejs.org/en/) (LTS version recommended)
- [MySQL](https://dev.mysql.com/downloads/mysql/)

---

## Backend Setup (Spring Boot)

### 1. Database Configuration

The backend requires a MySQL database to function.

1.  **Create the Database**:
    Open a MySQL client and run the following command to create the database:

    ```sql
    CREATE DATABASE mdd_db;
    ```

2.  **Set Environment Variables**:
    The application uses environment variables to connect to the database and for JWT security. You need to configure them before launching the server.

    Create a file named `.env` in the `back/` directory of the project and add the following variables, replacing the placeholder values with your actual MySQL credentials and a strong, unique secret for the JWT key.

    ```bash
    # /back/.env

    SPRING_DATASOURCE_USERNAME=your_mysql_username
    SPRING_DATASOURCE_PASSWORD=your_mysql_password
    JWT_SECRET_KEY=your_super_secret_and_long_jwt_key_here
    ```

    _Note: Ensure your shell is configured to load `.env` files or export these variables manually before running the application._

### 2. Launch the Backend

Navigate to the `back` directory and run the application using the Maven wrapper:

```bash
cd back
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

### 3. Data Seeding

On its first launch, the backend will automatically populate the `mdd_db` database with initial data, including:

- Several themes (Java, JavaScript, etc.).
- A default test user.
- A collection of sample articles associated with the test user and themes.

**Test User Credentials:**

- **Email:** `testuser@example.com`
- **Username:** `testuser`
- **Password:** `password`

### 4. API Documentation (Swagger)

Once the backend is running, you can explore and test the API endpoints using the Swagger UI, which is available at:

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## Frontend Setup (Angular)

### 1. Install Dependencies

Navigate to the `front` directory and install the required npm packages:

```bash
cd front
npm install
```

### 2. Launch the Frontend

Run the following command to start the Angular development server:

```bash
ng serve
```

The application will be available at `http://localhost:4200/`. The app will automatically reload if you make any changes to the source files.

### 3. Build for Production

To create a production build of the frontend, run:

```bash
ng build
```

The build artifacts will be stored in the `dist/` directory.
