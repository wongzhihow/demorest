package com.example.demorest.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    @NotNull
    @Min(value = 1, message = "quantity must greater than 0")
    private int quantity;

    @OneToOne(mappedBy = "productInventory")
    @JsonIgnore
    private Product product;


    public ProductInventory() {
    }

    public ProductInventory(int quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductInventory that = (ProductInventory) o;
        return id == that.id && quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product);
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}