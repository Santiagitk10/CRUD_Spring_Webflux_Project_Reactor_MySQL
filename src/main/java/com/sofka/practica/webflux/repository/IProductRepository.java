package com.sofka.practica.webflux.repository;

import com.sofka.practica.webflux.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Mono<Product> findByName(String name);

    @Query("SELECT * FROM product WHERE id <> :id AND name = :name")
    Mono<Product> repeatedName(int id, String name);
}
