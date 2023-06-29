package com.gamerender.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    protected Long categoryID;
    
    @Column(nullable = false, name = "category_name")
    protected String categoryName;

    @OneToMany(mappedBy="category")
    private Set<Collection> collections = new HashSet<>();
    
    @OneToMany(mappedBy = "category")
    private Set<Image> images = new HashSet<>();
}
