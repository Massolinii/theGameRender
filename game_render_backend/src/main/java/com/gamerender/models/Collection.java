package com.gamerender.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "collectionID")
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
    @JsonManagedReference
    private List<Image> images = new ArrayList();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public Collection(String collectionName, Category category) {
        this.collectionName = collectionName;
        this.category = category;
    }
    
    public void addImage(Image image) {
        images.add(image);
    }
}