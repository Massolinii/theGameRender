package com.gamerender.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamerender.models.Tag;
import com.gamerender.service.TagService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired TagService tagService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags =tagService.findAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        Tag tag = tagService.findTagById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
    
    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @Valid @RequestBody Tag tag) {
        tag.setTagID(id);
        Tag updatedTag = tagService.updateTag(tag);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // GET ALL tags BY image
    @GetMapping("/image/{imageId}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Tag>> getTagsByImage(@PathVariable Long imageId) {
        List<Tag> tags = tagService.findTagsByImageId(imageId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}