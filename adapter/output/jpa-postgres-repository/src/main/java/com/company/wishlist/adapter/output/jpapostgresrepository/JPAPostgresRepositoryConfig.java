package com.company.wishlist.adapter.output.jpapostgresrepository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.company.wishlist.adapter.output.jpapostgresrepository.entity")
public class JPAPostgresRepositoryConfig { //TODO Add statement_timeout and lock_timeout config
}
