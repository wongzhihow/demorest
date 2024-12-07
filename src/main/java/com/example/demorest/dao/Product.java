package com.example.demorest.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "name must not blank")
    private String name;
    @NotBlank(message = "description must not blank")
    private String description;
    @NotNull
    @DecimalMin(value = "0.01", message = "price must greater than 0")
    private BigDecimal price;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="product_inventory_id", referencedColumnName = "id")
    private ProductInventory productInventory;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    public Product() {
        super();
    }

    public Product(String name, String description, BigDecimal price, ProductInventory productInventory, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productInventory = productInventory;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime localDateTime) {
        this.createdDateTime = localDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime localDateTime) {
        this.modifiedDateTime = localDateTime;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(productInventory, product.productInventory) && Objects.equals(createdDateTime, product.createdDateTime) && Objects.equals(modifiedDateTime, product.modifiedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, productInventory, createdDateTime, modifiedDateTime);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productInventory=" + productInventory +
                ", createdDateTime=" + createdDateTime +
                ", modifiedDateTime=" + modifiedDateTime +
                '}';
    }


}
