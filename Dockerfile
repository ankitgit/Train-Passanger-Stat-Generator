FROM openjdk:8

ADD build/libs/train-passanger-stat-generator-all.jar  /opt/generator/application.jar

CMD ["Java", "-jar", "/opt/generator/application.jar"]