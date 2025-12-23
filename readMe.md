# Budget Planner REST API

This micro-service had been designed to read/write expenditures and revenues later to calculate balance for any given time range.

## Execution using Docker

To build docker image

```bash
docker build -t budget-planner-api:latest .
```


To run docker image (with a container has naming convention)

```bash
docker run --name budget-planner-api --link ty-db-container
```

## Production Build using gradle wrapper AND Execution with Java17

### Step 0 (Database Readiness):
You will find **data.sql** within /database directory of project root.
Keep in mind it requires as minimum MySQL 8.x to be installed in your local machine.

If you are already done with MySQL DB installation in your local, then you could execute data.sql script straight forward.

After script exection, please verify schema had been created covering mentioned tables within script content.

Within application.properties in src/main/resources directory you will find DB user AND password paramters. Please ensure it matches with your DB user if not, please modify it before next steps.

### Step 1:
```bash
./gradlew clean
./gradlew bootJar
```

After a successful build .jar file should be created under **build/libs** directory within project root.

### Step 2:
Then you could execute following command:
```bash
cd build/libs
```

### Step 3:
There should be a .jar file created already similar to following:

budget-planner-0.0.1-SNAPSHOT.jar

If there's no .jar file then re-apply *Step 1*.

Otherwise, if .jar file is presented then you could execute following command:

```bash
java -jar your-jar-file.jar
```

#### As an example:
```bash
java -jar budget-planner-0.0.1-SNAPSHOT.jar
```

Then, it will start the application. As per relevant configuration within **application.properties**, by default it will be using port 8088 in your local.

### Step 4 (optional):

Within application.properties there's a path for swagger UI you could use this for verification,

You could validate the execution by reaching the swagger-ui using following addresses in your browser:

http://127.0.0.1:8088/swagger-ui.html

http://127.0.0.1:8088/api-docs

**Assumption**: You did not change the port number for the application. 

- (If you had modified the port number for any reason, then please use your specific port number for above swagger ui address)

Finally, once you review the info provided within its swagger-ui, you will find enough information on how to consume Budget Planner API endpoints.