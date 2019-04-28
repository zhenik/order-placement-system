FROM openjdk:8
EXPOSE 6831/udp
ADD target/customer.jar .
CMD java -jar customer.jar --spring.profiles.active=docker