FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/demo-0.0.2-SNAPSHOT.jar /app/app.jar
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]