package com.example.api.repository;

import com.example.api.model.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUuid(UUID uuid);

    @Query(value = "SELECT * FROM cliente c " +
            "WHERE UPPER(unaccent(c.nome)) LIKE unaccent(concat('%', :nome, '%')) " +
            "OR UPPER(unaccent(c.email)) LIKE unaccent(concat('%', :email, '%'))",
            nativeQuery = true)
    List<Cliente> searchClienteByNomeOrEmail(@Param("nome") String nome, @Param("email") String email);

}
