# Introduction 

Resources for discussion around multi tenancy management in the new AARO Ascension Kubernetes
cluster. This is a work in progress and is not yet ready for production use.


# Getting Started
Using standard maven build, Java 21 and Spring Boot 3.4.4.

Maven Wrapper need to be initialized.


# Build and Test
Standard Maven commands.


# API Specification

Found under `src/main/resources/CustomerTenantManagementAPI.yml`

This is an OpenAPI 3.1.1 specification file. 
It describes the API endpoints, request/response formats, and other details about the API.

# Build Docker image

To build and run the Docker image, run the following command in the root directory of the project:

```bash
docker build -t demo-spring-app .
docker run --name demo-spring-app -d -p 8080:8080 demo-spring-app
```

# Stop by name
> docker stop demo-spring-app

# Or force stop
> docker kill demo-spring-app

# Delete the container 
(since we don't have the --rm flag to automatically remove the container after stopping it, we need to do it manually)

> docker rm demo-spring-app

# Unleash Feature Management

Unleash is the Feature Management/Flag system used in this Demo.

The DexterRunner uses the Unleash SDK directly and the PenywiseRunner uses Open Feature.

See https://github.com/Unleash/unleash#get-started-in-2-steps for documentation on the Open Source variant.

admin password in Lastpass under `Shared Development/Kubernetes & Helm/Unleash in AKS01`.

## Big Data Comparisson
### Database Initialization

The `init-scripts` directory contains SQL scripts that are automatically executed when the PostgreSQL container is created for the first time. This is handled by the official PostgreSQL Docker image, which looks for scripts in the `/docker-entrypoint-initdb.d` directory.

**Start the Services:**
    Open a terminal in the root of the project and run the following command:

    ```bash
    docker compose up --force-recreate --remove-orphans --detach
    ```

The initscripts generates tables for the TPC-H database, described at https://www.tpc.org/TPC_Documents_Current_Versions/pdf/TPC-H_v3.0.1.pdf



