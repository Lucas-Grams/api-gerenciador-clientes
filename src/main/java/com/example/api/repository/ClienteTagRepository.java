package com.example.api.repository;

import com.example.api.model.ClienteTag;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClienteTagRepository extends JpaRepository<ClienteTag, Long> {

    void deleteByClienteId(Long cienteId);
}

