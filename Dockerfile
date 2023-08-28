FROM maven:3.8.2-jdk-18

WORKDIR /kusra4
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
