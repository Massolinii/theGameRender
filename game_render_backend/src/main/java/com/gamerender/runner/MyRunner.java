package com.gamerender.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.gamerender.service.CategoryService;
import com.gamerender.service.CollectionService;
import com.gamerender.service.FavoriteService;
import com.gamerender.service.ImageService;
import com.gamerender.service.TagService;

public class MyRunner implements ApplicationRunner{
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CollectionService collectionService;
	
	@Autowired
	FavoriteService favoriteService;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	TagService tagService;
	
	// The user Runner is found in the Security runner
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
