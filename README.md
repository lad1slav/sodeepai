# Getting Started

### OpenAI Token ENV
```
OPENAI_TOKEN="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"
```
### Build
```
mvn clean install

docker build -t lad1slavv/sodeep-repo .
```
### Run
```
docker run -p 8080:8080 lad1slavv/sodeep-repo
```
### Docker Push
```
docker push lad1slavv/sodeep-repo
```
### Kubernetes Push
```
kubectl apply -f deployment/sodeep-pvc-stage.yaml
kubectl apply -f deployment/sodeep-stage.yaml
```
### Docker Run Access
```
http://localhost:8080/chat?prompt=any-text
```
### Kubernetes Access
```
kubectl exec -ti sodeep-ai-stage-585db5658d-d8t62 -c reddis-db-stage -- /bin/bash

kubectl get services

Use EXTERNAL-IP for 'sodeep-ai-stage' service to access

http://{EXTERNAL-IP}:8080/chat?prompt=genify
```
###
###
###
### Reference Documentation

* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)

### Guides

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

