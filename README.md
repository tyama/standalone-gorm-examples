# GORM Standalone Examples

## Using GORM for Hibernate4 in a Groovy Script
https://gist.github.com/tyama/def5264c0e265e08e2dd

```
groovy script-gorm-hibernate4.groovy
```

## Using GORM for MongoDB in a Groovy Script
https://gist.github.com/tyama/9685076

```
groovy script-gorm-mongodb.groovy
```

## Using GORM for Hibernate 4 in Spring Boot
https://gist.github.com/tyama/47001579ff57521eba2c

```
spring run boot-gorm-hibernate4.groovy

curl -XPOST http://localhost:8080/person/add -F 'firstName=Tony'
curl -XGET 'http://localhost:8080/person/greet?firstName=Tony'
```

## Using GORM for MongoDB in Spring Boot
https://gist.github.com/tyama/9685071

```
spring run boot-gorm-mongodb.groovy

curl -XGET http://localhost:8080
curl -XGET http://localhost:8080/near/London
```

