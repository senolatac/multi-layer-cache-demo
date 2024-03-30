### Multi-Layer Cache Demo
- EhCache + Redis Cache
- Redis docker-compose
- Admin Client

#### Run with multiple instances
```
java -Dspring.profiles.active=prod -Dserver.port=9595 -jar target/*.jar
java -Dspring.profiles.active=prod -Dserver.port=9596 -jar target/*.jar
```