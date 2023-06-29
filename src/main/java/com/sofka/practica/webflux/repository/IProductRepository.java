package com.sofka.practica.webflux.repository;

import com.sofka.practica.webflux.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
