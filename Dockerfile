FROM openjdk:latest
COPY ./target/sem_cw-0.1-alpha-5-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "sem_cw-0.1-alpha-5-jar-with-dependencies.jar", "db:3306"]