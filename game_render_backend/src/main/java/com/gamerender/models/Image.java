package com.gamerender.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    
    @Column(nullable = false)
    protected String title;
    
    @Column(nullable = false, name = "prompt_text")
    protected String promptText;
    
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    protected Category category;
    
    @ManyToMany(mappedBy = "likedImages")
    private Set<User> likedByUsers = new HashSet<>();

    @ManyToMany(mappedBy = "images")
    private Set<Collection> collections = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "image_tags",
        joinColumns = @JoinColumn(name = "image_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
