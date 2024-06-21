package com.example.api.model;

import com.example.api.dto.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
