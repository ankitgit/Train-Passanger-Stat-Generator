FROM openjdk:8

ENV B 'EMPTY'
ENV V 'EMPTY'
ENV NC 'EMPTY'
ENV CC 'EMPTY'

ADD src/main/resources/application.properties  /opt/generator/application.properties
ADD build/libs/train-passanger-stat-generator-all.jar  /opt/generator/application.jar

CMD ["sh", "-c", "java -jar /opt/generator/application.jar -P /opt/generator/application.properties -B ${B} -V ${V} -NC ${NC} -CC ${CC}"]