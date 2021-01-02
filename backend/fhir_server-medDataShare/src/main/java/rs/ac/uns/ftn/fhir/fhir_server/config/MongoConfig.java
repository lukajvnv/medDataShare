package rs.ac.uns.ftn.fhir.fhir_server.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static String connectionString = "mongodb://localhost:27017";
//    private static String connectionString = "mongodb+srv://luka:luka@cluster0.wa47o.gcp.mongodb.net/test?retryWrites=true&w=majority";

    private String getMongoDatabaseUrl() {
        return connectionString;
    }

    @Bean
    public MongoClient mongo() {
        String connectUrl = getMongoDatabaseUrl();
        MongoCredential mongoCredential = MongoCredential.createCredential(
                "myUserAdmin",
                "admin",
                "myUserAdmin".toCharArray()
        );
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
