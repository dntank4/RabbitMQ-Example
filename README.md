# Message-Driven Application with Spring Boot and RabbitMQ

This project is a simple message-driven application using Spring Boot and RabbitMQ to simulate a producer-consumer scenario. The application tracks and logs the total number of messages processed successfully and the number of errors encountered.

## Prerequisites

- Java 8 or later
- Maven
- Docker and Docker Compose File
- RabbitMQ

## RabbitMQ docker-compose.yml
```sh
version: '3.8'
services:
  rabbitmq:
    image: "rabbitmq:management"  # Official RabbitMQ image with the management plugin enabled
    ports:
      - "5672:5672"  # RabbitMQ default port for AMQP
      - "15672:15672"  # RabbitMQ Management UI port
    environment:
      RABBITMQ_DEFAULT_USER: "device"
      RABBITMQ_DEFAULT_PASS: "device"
      RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS: "-rabbit confirm_delivery true"
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq  # Persist RabbitMQ data
volumes:
  rabbitmq_data:  # Docker volume to persist RabbitMQ data
```

### 3. Configure Application Properties

Update the `application.properties` file in the `src/main/resources` directory with the provided RabbitMQ configuration.

### 4. Run the Application

Navigate to the project directory and run the application:

```sh
mvn spring-boot:run
```

### 5. Using Postman to Interact with the Application

Import the provided Postman collection into Postman to test the endpoints. The collection includes the following requests:

- **Get Success & Error Count**
    - `GET http://localhost:8080/getCount`
- **Send A Message**
    - `GET http://localhost:8080/send`
    - Body (raw JSON): `{"id": 1, "name": "Harry Potter"}`
- **Send Multiple Messages**
    - `GET http://localhost:8080/sendMultiple?count=20`
- **Sample Fail Test**
    - `GET http://localhost:8080/sampleFailTest`

### 6. Running Tests

To run the unit tests, execute the following command:

```sh
mvn test
```

### Project Structure

- **Configuration**
    - `RabbitMQConfig.java`: Configuration class for RabbitMQ.
- **Listener**
    - `RabbitMQListener.java`: Listener for processing messages from the queue.
- **Controller**
    - `DataSendController.java`: REST controller to send messages to the queue.
- **Utilities**
    - `CommonUtils.java`: Utility class for logging success and error counts.
- **Tests**
    - `QueueApplicationTests.java`: Test class for validating the application.

  **Thank You**