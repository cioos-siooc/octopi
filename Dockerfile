# Grab a maven image and build the war
FROM maven:3-jdk-8 AS maven-build
# Currently requires java 8

COPY . /octopi
WORKDIR /octopi

RUN mvn package


# Switch over to a tomcat image to run the service
FROM tomcat:9-jdk8

COPY --from=maven-build /octopi/target/octopi.war /usr/local/tomcat/webapps/octopi.war

CMD catalina.sh run