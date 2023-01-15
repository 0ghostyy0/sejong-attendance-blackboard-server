FROM openjdk:11-jdk

EXPOSE 8080
ADD ./build/libs/*.jar ghosty-attendance.jar

CMD ["java", "-jar", "ghosty-attendance.jar"]