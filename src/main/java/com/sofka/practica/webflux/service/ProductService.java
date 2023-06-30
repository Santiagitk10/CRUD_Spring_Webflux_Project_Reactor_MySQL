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
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("product not found")));
    }

    public Mono<Product> save(Product product){
        Mono<Boolean> existsName = productRepository.findByName(product.getName()).hasElement();
        return existsName.flatMap(exists -> exists ? Mono.error(new Exception("product name already in use"))
                : productRepository.save(product));
    }

    public Mono<Product> update(int id, Product product){
        Mono<Boolean> productIdExists = productRepository.findById(id).hasElement();
        Mono<Boolean> productNameIsRepeated = productRepository.repeatedName(id,product.getName()).hasElement();
        return productIdExists.flatMap(
                idExists -> idExists ?
                        productNameIsRepeated.flatMap(nameIsReated -> nameIsReated ? Mono.error(new Exception("product name already in use"))
                        : productRepository.save(new Product(id,product.getName(),product.getPrice())))
        : Mono.error(new Exception("product not found")));
    }

    public Mono<Void> delete(int id){
        Mono<Boolean> productIdExists = productRepository.findById(id).hasElement();
        return productIdExists.flatMap(exists -> exists ? productRepository.deleteById(id)
                : Mono.error(new Exception("product not found")));
    }

}
