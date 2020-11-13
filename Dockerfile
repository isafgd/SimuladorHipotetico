FROM maven:3.6.1-jdk-8-alpine

COPY target/*.jar /simulador_hipotetico.jar

#Executa o .jar
CMD ["java", "-jar", "/simulador_hipotetico.jar"]




