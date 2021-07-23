package br.com.company.wishlist.adapter.output.springmongorepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.company.wishlist.springmongorepository")
public class MongoConfiguration {

}
