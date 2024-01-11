# Library application

### This is a simple library implementation. It provides CRUD operations on books and their sales/authors. The application uses spring boot, mysql database and maven.

## Running with docker:
To run the application we first need to build our docker image:
```shell
docker build -t library:1.0 . 
```
Building this image will also build the project and run the tests

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


