package com.springsecurity.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "foods", schema = "demo")
public class Food {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_stock", nullable = false)
    private Integer isStock;

    @Column(name = "is_imported")
    private Integer isImported;

    @Column(name = "max_stock", nullable = false)
    private Integer maxStock;

    @OneToMany(mappedBy = "food", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<FavouriteFoods> favouriteFoods = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public Integer getIsImported() {
        return isImported;
    }

    public void setIsImported(Integer isImported) {
        this.isImported = isImported;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }

    public Set<FavouriteFoods> getFavouriteFoods() {
        return favouriteFoods;
    }

    public void setFavouriteFoods(Set<FavouriteFoods> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
    }
}
