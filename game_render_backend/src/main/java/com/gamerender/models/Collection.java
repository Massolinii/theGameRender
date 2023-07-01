package com.gamerender.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collections")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    protected Long collectionID;
    
    @Column(nullable = false)
    protected String collectionName;
    
    @OneToMany(mappedBy = "collection")
    private Set<Image> images = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public Collection(String collectionName, Category category) {
        this.collectionName = collectionName;
        this.category = category;
    }
}