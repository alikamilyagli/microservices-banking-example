Prerequests:
- A running local Docker Service
- Java 17
- Maven 3.8.0+

Hi There,

I setup the databases to run in-memory in the docker environment because I had some problems connecting to the services in docker.
I kept actual configs in `docker-true` profile for backend services. I guess it's some problem depending to my computer's arm based processor but not sure. 

You can give a try to updating profiles in `Dockerfile` configs for `account`, `transaction` and `history` backend services as use the `docker-true` profile, and uncomment backend service configs in `docker-compose.yml` file.
I prepared necessary settings for true configs as It should be normally but couldn't test it successfully.

In final circumstances I managed to run application with current `docker-compose.yml` config and running the backend services on local terminal with `local` profile.

For that, you should firstly build frontend image with the command below and then run `docker-compose up -d` on your terminal, then run backend services locally and everything should be fine.

When everything is ready, you can access to Web UI and register with your own e-mail address.

You also need to confirm your account with the confirmation code that will be sent to your e-mail address.

### Building frontend image

```bash
docker build -t cenoa/web-app ./cenoa-web-app
```

### Running backend services locally
```bash
mvn --projects com.cenoa:services-account spring-boot:run
mvn --projects com.cenoa:services-transaction spring-boot:run
mvn --projects com.cenoa:services-history spring-boot:run
```

---------------

### Building backed images with arm based processors

```bash
docker buildx build --platform linux/arm64 -t cenoa/account-service:latest ./services/services-account
docker buildx build --platform linux/arm64 -t cenoa/transaction-service:latest ./services/services-transaction
docker buildx build --platform linux/arm64 -t cenoa/history-service:latest ./services/services-history
```

### Building backed images with intel & amd based processors

```bash
docker build -t cenoa/account-service:latest ./services/services-account
docker build -t cenoa/transaction-service:latest ./services/services-transaction
docker build -t cenoa/history-service:latest ./services/services-history
```

### Running docker images
```bash
docker-compose up -d
```

### Service Descriptions
- Account Service: [http://localhost:8001/swagger-ui/index.html](http://localhost:8001/swagger-ui/index.html)
- Transaction Service: [http://localhost:8002/swagger-ui/index.html](http://localhost:8002/swagger-ui/index.html)
- History Service: [http://localhost:8003/swagger-ui/index.html](http://localhost:8003/swagger-ui/index.html) 

### Web UI
[http://localhost:3000/](http://localhost:3000/)

### Confluent Kafka Control Center
[http://localhost:9021/](http://localhost:9021/) <br/>
<em>clear your browser cache if you see any 4xx error</em>

### Notes:

- Unit tests are created only for account service as a sample
- You can import `Cenoa_Bank_API_Examples.postman_collection.json` into to your postman
- I accidentally used a library in the web app that is not supported by typescript which is `react-bootstrap-table-next` but couldnt have enough time to change it at the end so I needed to make a trick which you may come across in the source code of the web app. sorry for that
