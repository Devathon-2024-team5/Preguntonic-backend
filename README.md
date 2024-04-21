# Preguntonic-backend
PreguntonicBackend es el núcleo inteligente y robusto que impulsa la experiencia de juego de Preguntonic. Diseñado para proporcionar una gestión eficiente de preguntas, respuestas y partidas

## Deployment

### Requirements

1. jdk17
2. maven
3. docker

### Run service in local 

Run as a regular Java Spring Boot application.

#### With Maven

```bash
mvn clean package
java -jar target/preguntonic-backend-0.0.1-SNAPSHOT.jar
```

### Run service in local using Docker
using docker 
1. create a .jar file
2. build the docker image
    ```bash
    docker compose build preguntonic-backend
    ```
3. run the docker image
    ```bash
    docker compose up
    ```
4. open the browser and go to http://localhost:8080

