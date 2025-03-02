# spring-rabbitmq
spring-rabbitmq

## Run the front end container:

```
docker run -d -p 80:80 --name proposta-web-container matheuspieropan/proposta-web
```

### Open on your browser:

```
localhost:80
```

## Stop a container:

```
docker stop container_name
```

## Run database container if not created:

```
docker run --name postgres-container -d -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=dockerproposaldb -p 5433:5432 postgres
```

## Run database container if already created:

```
docker start postgres-container
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