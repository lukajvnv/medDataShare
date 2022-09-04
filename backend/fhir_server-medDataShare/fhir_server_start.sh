#!/bin/bash

export DB_URL="***YOUR_DATA***"

mvn clean
mvn package -Dmaven.test.skip=true

java -jar target/fhir_server-0.0.1-SNAPSHOT.jar