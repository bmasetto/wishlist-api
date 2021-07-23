package br.com.company.wishlist.adapter.output.springmongorepository.repository;

import br.com.company.wishlist.core.domain.Customer;
import br.com.company.wishlist.adapter.output.springmongorepository.mapper.CustomerOutputMapper;
import br.com.company.wishlist.core.usecase.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryMongo implements CustomerRepository {

    private final MongoTemplate mongoTemplate;
    private final CustomerOutputMapper customerOutputMapper;

    @Autowired
    public CustomerRepositoryMongo(MongoTemplate mongoTemplate, CustomerOutputMapper customerOutputMapper) {
        this.mongoTemplate = mongoTemplate;
        this.customerOutputMapper = customerOutputMapper;
    }

    @Override
    public Customer create(Customer customer) {
        var customerEntity = customerOutputMapper.toEntity(customer);
        mongoTemplate.save(customerEntity);
        return customerOutputMapper.toDomain(customerEntity);
    }
}
