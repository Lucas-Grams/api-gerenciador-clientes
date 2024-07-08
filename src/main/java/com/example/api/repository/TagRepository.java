package com.example.api.repository;

import com.example.api.model.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
