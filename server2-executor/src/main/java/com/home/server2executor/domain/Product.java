package com.home.server2executor.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Product domain model.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "products_generator")
    @SequenceGenerator(
            name = "products_generator",
            sequenceName = "products_sequence"
    )
    private Long id;

    @NotBlank
    @Size(min = 5, max = 150)
    private String title;

    @NotBlank
    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    private Float price;

    public Product() {
    }

    public Product(Long id,
                   @NotBlank @Size(min = 5, max = 150) String title,
                   @NotBlank @Size(min = 5, max = 500) String description,
                   @NotNull Float price) {
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
