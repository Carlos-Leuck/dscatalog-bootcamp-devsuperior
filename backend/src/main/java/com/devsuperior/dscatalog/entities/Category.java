package com.devsuperior.dscatalog.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt; // with this definition timestamp: UTC.

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    // Category - Products is a bidirectional relationship.
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();


    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist(){
        // first time using save? prePersist will register the Instant at createdAt;
        createdAt = Instant.now();
    }



    @PreUpdate
    public void preUpdate(){
        // preUpdate will update the Instant at updatedAt;
        updatedAt = Instant.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        return getId().equals(category.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
