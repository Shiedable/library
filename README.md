# Library application

### This is a simple library implementation. The application uses spring boot, mysql database and maven.

To run the application we first need to build our docker image:
```shell
docker build -t library:1.0 . 
```
This building this image will also build the project and run the tests

Then we in order to run this app:
```shell
docker compose up -d
```

Once we are done with it, we can close the app:
```shell
docker compose down
```
