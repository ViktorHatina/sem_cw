FROM openjdk:latest
COPY ./target/sem_cw.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "sem_cw.jar", "db:3306", "30000"]