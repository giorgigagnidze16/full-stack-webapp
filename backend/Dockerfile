FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8080
RUN mkdir /backend
COPY build/libs/backend.jar /backend/backend.jar
ENTRYPOINT ["java","-jar","/backend/backend.jar"]
