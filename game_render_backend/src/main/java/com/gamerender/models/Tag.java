package com.gamerender.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	protected Long tagID;
	
	@Column(nullable = false, name = "tag_name")
	protected String tagName;
	
	@ManyToMany(mappedBy = "tags")
    private Set<Image> images = new HashSet<>();
	
	public Tag(String tagName) {
	    this.tagName = tagName;
	}
}
