package com.devsuperior.dscatalog.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // description: use TEXT to tranform VARCHAR to TEXT to increase data capacity.
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imgUrl;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;
    /*
     date ISO 8601 format:'year-month-day T hour:minutes:seconds . fraction of seconds Z'
     obs: Z indicates that we're using UTC.
     add TIMESTAMP WITH TIME ZONE + date ISO8601: use this so a BD with SQL recognizes that this date is UTC.
     Example: TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z'
    */

    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))

    Set<Category> categories = new HashSet<>();
     /* 1) in N-N relation we need another table with the id's.
     Example:tb_category / tb_product => so we need tb_category_product with: product_id and category_id

     2) we will use Set, instead of list, because the product could have only one categorie at same time.
     Example : there are 20 computers but only 1 category named eletronics.
     Could have another 10 computers but only 1 category named computers.
      */

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return getId().equals(product.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
