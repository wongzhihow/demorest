package com.example.demorest.service;


import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demorest.pagination.PaginationResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demorest.dao.Product;
import com.example.demorest.exception.ProductNotFoundException;
import com.example.demorest.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        LocalDateTime now = LocalDateTime.now();

        product.setCreatedDateTime(now);
        product.setModifiedDateTime(now);

        return productRepository.save(product);
    }

    @Transactional
    public PaginationResponse<Product> getProducts(Pageable pageable) {

        Page<Product> product = productRepository.findAll(pageable);

        PaginationResponse<Product> pagePaginationResponse = new PaginationResponse<Product>(
                product.getContent(), new com.example.demorest.pagination.Page(
                product.getNumber() + 1,
                product.getSize(),
                product.getTotalElements(),
                product.getTotalPages()));


        return pagePaginationResponse;
    }

    @Transactional
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product updateProduct(Long id, Product newProduct) {
        var foundProduct = getProduct(id);

        return foundProduct.map(product -> {
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setProductInventory(newProduct.getProductInventory());
            product.setPrice(newProduct.getPrice());

            product.setModifiedDateTime(LocalDateTime.now());

            return productRepository.save(product);
        }).orElseThrow(() -> new ProductNotFoundException());
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
