# milu - (milu-backend)

## Overview

milu - an app to use todos

## Requirements

- **Java 21**  
  Ensure you have Java 21 installed since the application is built and compiled against this version.
- **Database:**  
  Either H2 (default configuration provided) or PostgreSQL (configure via the YAML file).

## Installation & Running the Application

### Using Docker

Docker provides a quick and reproducible way to run the application. Follow these steps:

1. **Build the Docker Image**

   Open your terminal in the project root and run:

    ```shell
    docker build -t winsauermichael/milu-backend:0.1 .
    ```

2. **Run the Docker Container**

    After building the image, start a container with:

    ```shell 
    docker run -it -p 8080:8080 winsauermichael/milu-backend:0.1
    ```

The application will start inside the container and be accessible at http://localhost:8080.

### Running Manually
If you prefer to run the application without Docker:

1. **Build the Application**

    In your project root, execute:

    ```shell
    ./mvnw clean install
    ```


2. **Start the Application**

    Launch the application using the provided configuration file (H2 by default):

    ```shell
    java -jar target/milu-backend-0.1.jar server src/main/resources/example-server-h2.yml
    ```

Once started, access the application at http://localhost:8080.

### Auth

Generate a access token to access resources. In your linux shell:

```bash
MILU_ACCESS_TOKEN=$(curl "localhost:8080/api/auth/token?name=my_name&password=my_password")
```

Access resources with this generated access token. For example:

```bash
curl "localhost:8080/api/board?access_token=$MILU_ACCESS_TOKEN"
```

You could also provide the token as header. For example:

```bash
curl "localhost:8080/api/board" -H "Authorization: Bearer $MILU_ACCESS_TOKEN"
```

### Create Board

Create a simple todo board:

```bash
curl -d '{"name":"board 1", "description":"board 1 info"}' -H "Content-Type: application/json" -X POST "http://localhost:8080/api/board?access_token=$MILU_ACCESS_TOKEN"
```

## Authors

Michael Winsauer & Luis Weich

## Credits

thws
