package rs.ac.uns.ftn.fhir.fhir_server.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Autowired
    private Environment environment;

    private String getMongoDatabaseUrl() {
        return environment.getProperty("DB_URL");
    }

    @Bean
    public MongoClient mongo() {
        String connectUrl = getMongoDatabaseUrl();
        String DB_USER_NAME = environment.getProperty("DB_USER_NAME");
        String DB_USER_DB = environment.getProperty("DB_USER_DB");
        String DB_USER_PASS = environment.getProperty("DB_USER_PASS");

        MongoCredential mongoCredential = MongoCredential.createCredential(
                DB_USER_NAME,
                DB_USER_DB,
                DB_USER_PASS.toCharArray()
        );
//        MongoCredential.createScramSha256Credential()
        ConnectionString connectionString = new ConnectionString(connectUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
//                .credential(mongoCredential)
//                .applyToSslSettings(builder -> builder.enabled(true))
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), "fhir-server");

        return mongoTemplate;
    }
}
