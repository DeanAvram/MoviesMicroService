# Movies Micro Service

This project is a microservice that provides a REST API and RSocket API to manage movies.

## Movie boundary

The movie boundary is a simple example that represents a movie. It has the following attributes:

`
```json
{
  "id": "tt0110912",
  "title": "Pulp Fiction",
  "year": 1994,
  "genres": [
    "Crime","Drama"
  ],
  "language": "English",
  "length": 154,
  "directors": [
    "Quentin Tarantino"
  ],
  "organization": "mirmax"
}
```

## REST API

Swagger page to test the REST API: [http://localhost:9090/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## RSocket API

RSocket is a binary protocol for use on byte stream transports

The RSocket API is available on port 7001.

### Running RSocket API

- delete all movies

We are using fnf (fire and forget) to delete all movies.

```shell
java -jar rsc-0.9.1.jar --fnf --route=delete-all-movies --debug tcp://localhost:7001
```

- find all movies

```shell
java -jar rsc-0.9.1.jar --stream --route=get-all-movies tcp://localhost:7001
```

- create a new movie

We are using request-response to create a new movie.

 ```shell
java -jar rsc-0.9.1.jar --request --route=create-new-movie --data="{\"id\":\"tt0110914\",\"title\":\"Pulp Fiction\",\"year\":1994,\"genres\":[\"Drama\",\"Crime\"],\"language\":\"English\",\"length\":154,\"directors\":[\"Quentin Tarantino\"],\"organization\":\"mirmax\"}" tcp://localhost:7001
```
