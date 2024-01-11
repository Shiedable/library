# Library application

### This is a simple library implementation. It provides CRUD operations on books and their sales/authors. The application uses spring boot, mysql database and maven. It also uses a separate adapter application which gets book data from a public API using only ISBN.

## Running with docker:
Firstly we need to get the adapter up and running:

Clone the repo https://github.com/Shiedable/library-app-adapter

First you need to build the image for the adapter: 
```shell
 docker build -t library-adapter:1.0 .  
```
We then need to build our docker image for the main app:
```shell
docker build -t library:1.0 . 
```
Building those images will also build the project and run the tests

Then in order to run this app:
```shell
docker compose up -d
```

Once we are done with it, we can close the app:
```shell
docker compose down
```
## Running without docker

First we need to build the project, we need Maven installed for this, do it for both the main app and the adapter:
```shell
mvn clean install
```
This will also run all unit and integration tests

Start the database(for example using homebrew):
```shell
brew services start mysql
```

Then run the adapter:
```shell
mvn spring-boot:run
```
Repeat the last command for the main app too

## Using the app:

The app provides functionality through endpoints:
## Author
### Add Author
#### Endpoint: /author
#### Method: POST
#### Parameters:
```
firstName: First Name of the author
lastName: Last Name of the author
birthDate: The Birth Date of an Author

all parameters are mandatory
```
#### Example:
```shell
curl -X POST http://localhost:8080/author?firstName=John&lastName=Doe&birthDate=1990-01-01
```

### Delete Author
#### Endpoint: /author
#### Method: DELETE
#### Parameters:
```
id: The id of the author to be deleted
parameter is mandatory
```
#### Example:
```shell
curl -X DELETE http://localhost:8080/author/1 
```

### Update Author
#### Endpoint: /author
#### Method: PUT
#### Parameters:
```
id: The id of the author to be deleted
firstName: First Name of the author
lastName: Last Name of the author
birthDate: The Birth Date of an Author

only id parameter is mandatory
```
#### Example:
```shell
curl -X PUT http://localhost:8080/author/1?firstName=John&lastName=Doe&birthDate=1990-01-01
```

### Get Author
#### Endpoint: /author
#### Method: GET
#### Parameters:
```
id: The id of the author to be deleted
parameter is mandatory
```
#### Example:
By id:
```shell
curl http://localhost:8080/author/1
```
All:
```shell
curl http://localhost:8080/author/get
```

## Book
### Add Book
#### Endpoint: /book
#### Method: POST
#### Parameters:
```
firstName: First Name of the author
lastName: Last Name of the author
birthDate: The Birth Date of an Author

all parameters are mandatory
```
#### Example:
```shell
curl -X POST http://localhost:8080/author?firstName=John&lastName=Doe&birthDate=1990-01-01
```

### Delete Author
#### Endpoint: /author
#### Method: DELETE
#### Parameters:
```
id: The id of the author to be deleted
parameter is mandatory
```
#### Example:
```shell
curl -X DELETE http://localhost:8080/author/1 
```

### Update Author
#### Endpoint: /author
#### Method: PUT
#### Parameters:
```
id: The id of the author to be deleted
firstName: First Name of the author
lastName: Last Name of the author
birthDate: The Birth Date of an Author

only id parameter is mandatory
```
#### Example:
```shell
curl -X PUT http://localhost:8080/author/1?firstName=John&lastName=Doe&birthDate=1990-01-01
```

### Get Author
#### Endpoint: /author
#### Method: GET
#### Parameters:
```
id: The id of the author to be deleted
parameter is mandatory
```
#### Example:
By id:
```shell
curl http://localhost:8080/author/1
```
All:
```shell
curl http://localhost:8080/author/get
```


