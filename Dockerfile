FROM azul/zulu-openjdk-alpine:11.0.6-jre
COPY target/atm-cash-dispenser*.jar atm-cash-dispenser.jar
ENTRYPOINT ["java","-jar","/atm-cash-dispenser.jar"]
