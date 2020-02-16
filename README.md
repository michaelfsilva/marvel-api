# marvel-api
API to provide Marvel Comics data

This project was created from the Spring Initializr with H2 Database, Spring Web, Spring Data JPA, Spring Security and Spring Boot Dev Tools
The H2 Database was chosen to be easy for testing without the necessity of configuring a database.

### Instructions to use
Download the zip project by clicking on Clone or download button, then Downlod ZIP;
Extract the zip file and open a terminal in the folder extracted;
Run the following command:

If you have maven installed:
```
$ mvn spring-boot:run
```
If not:
```
$ java -jar target/marvel-api-0.0.1-SNAPSHOT.jar
```
Access http://localhost:8080/v1/characters/ with user "test" and password "marvel" to see a list of characters.

### APIs endpoints
GET http://localhost:8080/v1/characters/ [list all characters]  
GET http://localhost:8080/v1/characters/{id} [list a character by ID]  
GET http://localhost:8080/v1/characters/findByName/Hulk [list a character by name]  
POST http://localhost:8080/v1/characters/ [add a new character]  
PUT http://localhost:8080/v1/characters/{id} [update character all attributes]  
PATCH http://localhost:8080/v1/characters/{id} [update one or more attrubutes of a character]  
DELETE http://localhost:8080/v1/characters/{id} [remove a character]  

