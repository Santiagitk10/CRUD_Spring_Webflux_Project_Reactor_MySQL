package com.sofka.practica.webflux.service;

import com.sofka.practica.webflux.entity.Product;
import com.sofka.practica.webflux.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor //sierve para no poner el @Autowired
public class ProductService {

    private final IProductRepository productRepository;

    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id){
        return productRepository.findById(id);
    }

    public Mono<Product> save(Product product){
        return productRepository.save(product);
    }

    public Mono<Product> update(int id, Product product){
        return productRepository.save(new Product(id,product.getName(),product.getPrice()));
    }

    public Mono<Void> delete(int id){
        return productRepository.deleteById(id);
    }

}