# Library application

### This is a simple library implementation. It provides CRUD operations on books and their sales/authors. The application uses spring boot, mysql database and maven.

## Running with docker:
To run the application we first need to build our docker image:
```shell
docker build -t library:1.0 . 
```
Then we need to clone to adapter app:
https://github.com/Shiedable/library-app-adapter
And build the image for it:
```shell
docker build -t library-adapter:1.0 .
```
Building those images will also build the projects and run the tests

Then in order to run this app:
```shell
docker compose up -d
```

Once we are done with it, we can close the app:
```shell
docker compose down
```
## Running without docker

First we need to build the project, we need Maven installed for this:
```shell
mvn clean install
```

This will also run all unit and integration tests

Start the database(for example using homebrew):
```shell
brew services start mysql
```

Then run the application:
```shell
mvn spring-boot:run
```

We also have to install and run the adapter, go to the folder where the adapter app is and do:
```shell
mvn clean install
mvn spring-boot-run
```

# Application endpoints

## Author

#### Add Author
#### Endpoint: /author
#### Method: POST
#### Parameters:
```
mandatory: 
    firstName - String
    lastName - String
    birthDate - String, format YYYY-MM-DD
```
#### Example:
```shell
curl -X POST http://localhost:8080/author?firstName=John&lastName=Doe&birthDate=1990-01-01
```

#### Delete Author
#### Endpoint: /author
#### Method: DELETE
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl -X DELETE http://localhost:8080/author/1
```

#### Update Author
#### Endpoint: /author
#### Method: PUT
#### Parameters:
```
mandatory: 
    id - int
Optional:
    firstName - String
    lastName - String
    birthDate - String, format YYYY-MM-DD
```
#### Example:
```shell
curl -X PUT http://localhost:8080/author/1?firstName=John&lastName=Doe&birthDate=1990-01-01
```

#### Get Author By ID
#### Endpoint: /author
#### Method: GET
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl http://localhost:8080/author/1
```

#### Get All Authors
#### Endpoint: /author
#### Method: GET
#### Example:
```shell
curl http://localhost:8080/author/get
```

## Book

#### Add Book
#### Endpoint: /book
#### Method: POST
#### Parameters:
```
mandatory: 
    title - String
    publicationDate - String, format YYYY-MM-DD
    isbn - String
    price - Double
```
#### Example:
```shell
curl -X POST http://localhost:8080/book?title=Book%20Title&publicationDate=2023-01-01&isbn=978-1-234567-89-0&price=9.99
```

#### Delete Book
#### Endpoint: /book
#### Method: DELETE
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl -X DELETE http://localhost:8080/book/1
```

#### Update Book
#### Endpoint: /book
#### Method: PUT
#### Parameters:
```
mandatory: 
    id - int
Optional:
    title - String
    publicationDate - String, format YYYY-MM-DD
    isbn - String
    price - Double
```
#### Example:
```shell
curl -X PUT http://localhost:8080/book/1?title=Updated%20Book%20Title
```

#### Get Book By ID
#### Endpoint: /book
#### Method: GET
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl http://localhost:8080/book/1
```

#### Get All Books
#### Endpoint: /book
#### Method: GET
#### Example:
```shell
curl http://localhost:8080/book/get
```

#### Add a book and it's data + Author data by isbn
#### Endpoint: /bookByIsbn
#### Method: POST
#### Parameters:
```
mandatory:
    isbn - String
```
#### Example:
```shell
curl -X POST "http://localhost:8080/bookByIsbn?isbn=1339018144"
```

## Sale

#### Add Sale
#### Endpoint: /sale
#### Method: POST
#### Parameters:
```
mandatory: 
    saleDate - String, format YYYY-MM-DD
    quantity - int
    bookId - int
```
#### Example:
```shell
curl -X POST "http://localhost:8080/sale?saleDate=2023-01-01&quantity=10&bookId=1"
```

#### Delete Sale
#### Endpoint: /sale
#### Method: DELETE
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl -X DELETE http://localhost:8080/sale/1
```

#### Update Sale
#### Endpoint: /sale
#### Method: PUT
#### Parameters:
```
mandatory: 
    id - int
Optional:
    saleDate - String, format YYYY-MM-DD
    quantity - int
    bookId - int
```
#### Example:
```shell
curl -X PUT "http://localhost:8080/sale/1?saleDate=2023-01-01"
```

#### Get Sale By ID
#### Endpoint: /sale
#### Method: GET
#### Parameters:
```
mandatory: 
    id - int
```
#### Example:
```shell
curl http://localhost:8080/sale/1
```

#### Get All Sales
#### Endpoint: /sale
#### Method: GET
#### Example:
```shell
curl http://localhost:8080/sale/get
```
