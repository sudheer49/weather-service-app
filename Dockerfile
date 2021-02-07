
FROM java:8-jdk-alpine
COPY /target/weather-service-app*.jar /home/app/weather-service-app/app.jar
EXPOSE 8081
ENTRYPOINT java -jar /home/app/weather-service-app/app.jar