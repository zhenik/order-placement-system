FROM openjdk:8
EXPOSE 6831/udp
ADD target/order.jar .
CMD java -jar order.jar --spring.profiles.active=docker