# Preguntonic API

Preguntonic API es el núcleo inteligente y robusto que impulsa la experiencia de juego de Preguntonic. Diseñado para proporcionar una gestión eficiente de preguntas, respuestas y partidas

## API Reference
 - OpenAPI 3.0 documentation can be found [here](docs/preguntonicApi.yaml)
 - Asynchronous API documentation can be found [here](docs/preguntonicAsyncApi.yaml)
 
 - API is hosted on [render](https://preguntonic-backend.onrender.com/)


## Deployment

### Requirements

1. [Java jdk17](https://openjdk.org/projects/jdk/17/) ♨️
2. [maven](https://maven.apache.org/) 🪶
3. [docker](https://www.docker.com/) 🐳
4. [spring-boot](https://spring.io/projects/spring-boot) 🍃
5. [h2 database](https://www.h2database.com/html/main.html) 🛢

### Run Locally 🚀

#### With Maven ☕

Create `.jar` file 

```bash
mvn clean package
```

Run as a service

```bash
java -jar target/preguntonic-backend-0.0.1-SNAPSHOT.jar
```

### With Docker 🐳

Build the docker image
```bash
docker compose build preguntonic-backend
```
    
Run the docker image
```bash
docker compose up -d
```
    
Open the browser and go to http://localhost:8080


## License

[MIT](https://choosealicense.com/licenses/mit/)


## Authors

- [@peenyaa7](https://github.com/peenyaa7)
- [@JFCowboy](https://github.com/JFCowboy)
