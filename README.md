# spring-rabbitmq
spring-rabbitmq

## Example of rabbitmq consumer in action:

Use this website to simulate the image example below:
https://tryrabbitmq.com/

credits and thanks for the simulator: https://github.com/videlalvaro

In the example down below, 'new_proposals_queue' does have 8 messages, if you link (using shift on your keyboard) to the consumer 'credit_analysis_consumer' , it will consume those messages in the queue.

![image](https://github.com/user-attachments/assets/a3d231e1-5f10-41ca-a176-c62c81849606)

## Run database container if not created:

```
docker run --name postgres-container -d -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=dockerproposaldb -p 5433:5432 postgres
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
