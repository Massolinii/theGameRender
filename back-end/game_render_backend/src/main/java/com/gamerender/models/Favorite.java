package com.gamerender.models;

import jakarta.persistence.Column;
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
@Table(name = "favorites")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Favorite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favorite_id")
	protected Long favoriteID;

	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	protected User user;
	
	@ManyToOne
    @JoinColumn(name="image_id", nullable=true)
	protected Image image;

    @ManyToOne
    @JoinColumn(name="collection_id", nullable=true)
	protected Collection collection;
}
