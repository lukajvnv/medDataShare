#!/bin/bash

export DB_URL="mongodb+srv://lukaMedDataShare:x594VybYG45XWsNQ@cluster0.wa47o.gcp.mongodb.net/test?retryWrites=true&w=majority"
export DB_USER_NAME=lukaMedDataShare
export DB_USER_DB=admin
export DB_USER_PASS=x594VybYG45XWsNQ

mvn clean
mvn package

java -jar target/fhir_server-0.0.1-SNAPSHOT.jar
