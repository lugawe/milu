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

## Authors

Michael Winsauer & Luis Weich

## Credits

thws
