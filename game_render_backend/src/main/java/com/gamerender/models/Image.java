package com.gamerender.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    protected Long imageID;
    
    @Column(nullable = true, length = 256)
    private String url;
    
    @Column(nullable = false, name = "prompt_text")
    protected String promptText;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;
    
    @ElementCollection
    @CollectionTable(name = "image_tags", joinColumns = @JoinColumn(name = "image_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
    
    public Image(String url, String promptText, Collection collection, Set<String> tags) {
        this.url = url;
        this.promptText = promptText;
        this.collection = collection;
        this.tags = tags;
    }
}