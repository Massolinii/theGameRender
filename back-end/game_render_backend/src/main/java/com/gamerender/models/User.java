package com.gamerender.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	protected Long userID;
	
	@Column(nullable = false)
	@Email
	protected String email;
	
	@Column(nullable = false)
	protected String username;
	
	@Column(nullable = false)
	protected String password;
	
	@ManyToMany
    @JoinTable(
        name = "user_likes_image",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> likedImages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;
}
