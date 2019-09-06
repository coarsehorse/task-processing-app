package com.home.server1queue.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Product domain model.
 */
public class Product implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Float price;

    public Product() {
    }

    public Product(Long id, String title, String description, Float price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Product(Product that) {
        this(that.getId(), that.getTitle(), that.getDescription(), that.getPrice());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                title.equals(product.title) &&
                Objects.equals(description, product.description) &&
                price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
