package com.company.wishlist.adapter.output.jpapostgresrepository;

import com.company.wishlist.adapter.output.jpapostgresrepository.entity.CustomerEntity;
import com.company.wishlist.adapter.output.jpapostgresrepository.entity.WishlistEntity;
import com.company.wishlist.adapter.output.jpapostgresrepository.mapper.WishlistOutputMapper;
import com.company.wishlist.core.wishlist.Wishlist;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.repository.ProductRepository;
import com.company.wishlist.core.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@Primary
@Slf4j
public class WishlistJPARepository implements WishlistRepository {

    private final EntityManager entityManager;

    private final WishlistOutputMapper wishlistOutputMapper;

    private final ProductRepository productRepository;

    @Autowired
    WishlistJPARepository(EntityManager entityManager, WishlistOutputMapper wishlistOutputMapper,
                          @Qualifier("cached") ProductRepository productRepository) {
        this.entityManager = entityManager;
        this.wishlistOutputMapper = wishlistOutputMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Wishlist getBy(Customer customer) {
        log.debug("Finding wishlist: " + customer.id());

        var wishlistEntities = entityManager
                .createQuery("SELECT w FROM Wishlist w WHERE w.customer.id = :customerId", WishlistEntity.class)
                .setParameter("customerId", customer.id())
                .getResultList();

        var productsIds = wishlistEntities.stream()
                .map(wishlistEntity -> ProductId.from(wishlistEntity.getExternalProductId()))
                .collect(toList());

        var products = productsIds.stream()
                .map(productRepository::findBy)
                .map(Optional::get)
                .collect(toSet());

        return Wishlist.from(customer, products);
    }

    @Override
    @Transactional
    public void save(Wishlist wishList) {
        log.debug("Saving wishlist: " + wishList.customer().id());

        var value = wishList.customer().id();

        entityManager.createQuery("DELETE FROM Wishlist w WHERE w.customer.id = :customerId")
                .setParameter("customerId", value)
                .executeUpdate();

        var customerEntity = entityManager.find(CustomerEntity.class, value);

        wishlistOutputMapper.toEntity(wishList, customerEntity).forEach(entityManager::persist);
    }
}
