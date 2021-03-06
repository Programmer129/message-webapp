package com.springsecurity.demo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

@Data
@Entity
@EqualsAndHashCode
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

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FavouriteFoods> favouriteFoods = new HashSet<>();
}
