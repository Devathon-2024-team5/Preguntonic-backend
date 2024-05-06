# Preguntonic API

Preguntonic API es el núcleo inteligente y robusto que impulsa la experiencia de juego de Preguntonic. Diseñado para proporcionar una gestión eficiente de preguntas, respuestas y partidas

## Deployment

### Requirements

1. jdk17
2. maven
3. docker

### Run Locally 

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

Create `.jar` file

```bash
mvn clean package
```
    
Build and Run the docker image

```bash
docker compose up -d
```
    
Open the browser and go to http://localhost:8080

## Installation

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```
    
## Deployment

To deploy this project run

```bash
  npm run deploy
```

## License

[MIT](https://choosealicense.com/licenses/mit/)


## Authors

- [@peenyaa7](https://github.com/peenyaa7)
- [@JFCowboy](https://github.com/JFCowboy)
