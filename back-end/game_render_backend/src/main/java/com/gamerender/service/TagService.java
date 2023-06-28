package com.gamerender.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamerender.exceptions.TagAlreadyExistsException;
import com.gamerender.exceptions.TagNotFoundException;
import com.gamerender.models.Image;
import com.gamerender.models.Tag;
import com.gamerender.repository.TagRepository;

@Service
public class TagService {

    @Autowired private TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        if (tagRepository.findByTagName(tag.getTagName()).isPresent()) {
            throw new TagAlreadyExistsException("Tag with name " + tag.getTagName() + " already exists.");
        }
        return tagRepository.save(tag);
    }

    public Tag findTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException("Tag with ID " + id + " not found."));
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public Tag updateTag(Tag tag) {
        if (!tagRepository.existsById(tag.getTagID())) {
            throw new TagNotFoundException("Tag with ID " + tag.getTagID() + " not found.");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new TagNotFoundException("Tag with ID " + id + " not found.");
        }
        tagRepository.deleteById(id);
    }
    
    public List<Tag> findTagsByImageId(Long imageId) {
        return tagRepository.findTagsByImageId(imageId);
    }
    
    public List<Image> findImagesByTagId(Long tagId) {
        return tagRepository.findImagesByTagId(tagId);
    }
}
