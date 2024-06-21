package com.example.api.repository;

import com.example.api.model.ClienteTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteTagRepository extends JpaRepository<ClienteTag, Long> {

    List<ClienteTag> findByClienteId(Long clienteId);

    void deleteByClienteId(Long cienteId);
}

