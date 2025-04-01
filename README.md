# Spring + RabbitMQ project

## Example of rabbitmq consumer in action:

Use this website to simulate the image example below:
https://tryrabbitmq.com/

credits and thanks for the simulator: https://github.com/videlalvaro

In the example down below, 'new_proposals_queue' does have 8 messages, if you link (using shift on your keyboard) to the consumer 'credit_analysis_consumer' , it will consume those messages in the queue.

Click on send button to send messages from the producers, you will see that queues send two messages to those two queues.

![image](https://github.com/user-attachments/assets/a1269651-66a0-4bfe-b122-46623879bf13)

### Direct exchange example:

![image](https://github.com/user-attachments/assets/76beffd0-2bdf-420c-aa83-133b9efadab9)


## Run Rabbitmq docker container:

```
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management

# after this just run:

docker start my-rabbit
```

### Understanding the command:

```
docker run -d
# Initialize the container on detached mode. Run the container and free the terminal for use

-p 5672:5672 -p 15672:15672
# Redirecting port 5672 to 15672. Works like a mapping to 15672
# to open the container go to http://localhost:15672/#/
# user: guest  /  password: guest

--name my-rabbit
# This is the name of the container

rabbitmq:3-management
# This is the image itself
```

## Add this configuration to your application.properties for the Rabbitmq:

```
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

## Run database container if not created:

```
docker run -d --name postgres-container -d -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=dockerproposaldb -p 5433:5432 postgres
```

## Run database container if already created:

```
docker start postgres-container
```

## Stop a container:

```
docker stop container_name
```


## Remove contaier:

```
docker remove contaier-name
```

### See docker containers:

```
docker ps
# or see all containers:
docker ps
```

### application.properties for docker database:

```
spring.application.name=proposal-app
spring.datasource.url=jdbc:postgresql://localhost:5433/dockerproposaldb
spring.datasource.password=123
spring.datasource.username=postgres
```

### application.properties for local database:

```
spring.application.name=proposal-app
spring.datasource.url=jdbc:postgresql://localhost:5432/proposal
spring.datasource.password=admin
spring.datasource.username=postgres
```
