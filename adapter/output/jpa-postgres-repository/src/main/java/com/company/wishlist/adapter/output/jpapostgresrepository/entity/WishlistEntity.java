package com.company.wishlist.adapter.output.jpapostgresrepository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity(name = "Wishlist")
@NoArgsConstructor
@Table(name = "wishlist")
public class WishlistEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_seq")
    @SequenceGenerator(name = "wishlist_seq", sequenceName = "wishlist_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "external_product_id", nullable = false)
    private String externalProductId;

}
