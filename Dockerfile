EXPOSE 8080
WORKDIR /deltaforce
COPY /src /deltaforce/src
COPY pom.xml /deltaforce
RUN mvn clean install
CMD ["mvn", "spring-boot:run"]