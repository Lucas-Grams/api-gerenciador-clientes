package com.example.api.controller;

import com.example.api.model.Tag;
import com.example.api.dto.ResponseDTO;
import com.example.api.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    public TagController(
            TagService tagService
    ) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseDTO<List<Tag>> getTags() {
        return this.tagService.getTags();
    }

    @PostMapping
    public ResponseDTO postTag(@RequestBody Tag tag) {
        return this.tagService.postTag(tag);
    }
}