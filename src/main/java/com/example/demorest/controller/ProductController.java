package com.example.demorest.controller;

import com.example.demorest.dao.Product;
import com.example.demorest.pagination.PaginationResponse;
import com.example.demorest.service.CurrencyExchangeRateService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demorest.exception.ProductNotFoundException;
import com.example.demorest.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    private ProductService productService;
    private CurrencyExchangeRateService currencyExchangeRateService;

    public ProductController(ProductService productService, CurrencyExchangeRateService currencyExchangeRateService) {
        super();
        this.productService = productService;
        this.currencyExchangeRateService = currencyExchangeRateService;
    }

    @PostMapping(path = "/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {

        return productService.createProduct(product);
    }

    @GetMapping(path = "/products/{id:\\d+}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    @GetMapping(path = "/products")
    public PaginationResponse<Product> getProducts(@PageableDefault(size = 10) Pageable pageable) {

        return productService.getProducts(pageable);
    }

    @PutMapping(path = "/products/{id:\\d+}")
    public Product updateProduct(@PathVariable Long id, @RequestBody @Valid Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping(path = "/products/{id:\\d+}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping(path = "/currency-exchange-rate", produces = "application/json")
    public ResponseEntity<String> getCurrencyExchangeRate() {
        return new ResponseEntity<>(currencyExchangeRateService.getExchangeRate(), HttpStatus.OK);
    }

}
