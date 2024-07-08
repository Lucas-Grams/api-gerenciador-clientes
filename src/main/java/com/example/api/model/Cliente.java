package com.example.api.model;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.example.api.dto.ClienteDTO;

import java.util.List;
import java.util.UUID;

@Table(name = "cliente", schema = "public")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<ClienteTag> clienteTags;

    private boolean ativo = true;

    public Cliente(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.getNome();
        this.email = clienteDTO.getEmail();
        this.ativo = clienteDTO.isAtivo();
    }

    public void updateFromDTO(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.getNome();
        this.email = clienteDTO.getEmail();
        this.ativo = clienteDTO.isAtivo();
    }
}
