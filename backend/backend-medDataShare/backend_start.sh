#chmod +x mvnw
#./mvnw clean install
rm -v wallet/*

export CRYPTO_FILEPATH=src/main/resources/med_data_share.jks
export CUSTOM_PROPERTIES_PATH=src/main/resources/custom_props.yml
export KEYSTORE_PASS=medDataShare
export ALIAS=meddatashare
export PASS=medDataShare

./mvnw spring-boot:run
