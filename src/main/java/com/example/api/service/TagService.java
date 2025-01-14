package com.example.api.service;

import com.example.api.model.Tag;
import com.example.api.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import com.example.api.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(
            TagRepository tagRepository
    ) {
        this.tagRepository = tagRepository;
    }

    public ResponseDTO<List<Tag>> getTags() {
        List<Tag> tags = tagRepository.findAll();
        try{
            if(tags.isEmpty()) return ResponseDTO.ok("Nenhuma tag encontrada");
            return ResponseDTO.ok(tags);
        } catch (Exception e) {
            return ResponseDTO.err("Erro ao buscar tags");
        }
    }

    public ResponseDTO postTag(Tag tag) {
        try {
            tagRepository.save(tag);
            return ResponseDTO.ok("Tag salva com sucesso");
        } catch (Exception e) {
            return ResponseDTO.err("Erro ao cadastrar tag");
        }
    }
}
