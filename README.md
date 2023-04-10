# Booking API

Welcome to the very last booking API! This is a Java Spring MVC API with Swagger documentation. This API allows users to book for a single room in the last hotel available in cancun.

## Getting Started

To get started with the API, please follow the instructions below:

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Database Configuration

This API uses a MySQL database for data storage. To configure the database, edit the application.properties file in the src/main/resources directory and set the following properties:

1. spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
2. spring.datasource.username=<your-username>
3. spring.datasource.password=<your-password>
4. spring.jpa.hibernate.ddl-auto=update
5. server.servlet.context-path=/api/v1

### Installation

1. Clone the repository: `git clone https://github.com/your-username/booking-api.git`
2. Navigate to the project directory: `cd booking-api`
3. Add the Database Configuration mentioned above.
4. Build the project: `./mvnw clean install` (for mac users)

### Running the API

#### Using Maven

1. Navigate to the project directory: `cd booking-api`
2. Run the project: `./mvnw spring-boot:run` (for mac users)

### Accessing the API

Once the API is running, you can access it at the following URL:

1. http://localhost:8080/api/v1

## API Documentation

API documentation is available via Swagger at the following URL:

1. http://localhost:8080/api/v1/swagger-ui/index.html

## Reviewing Unit tests

You can check the test suit in the following directory:

1. src/test/java/com/example/test/BookingControllerTest.java
