package com.example.api.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    private UUID uuid;

    private String nome;

    private String email;

    private List<ClienteTagDTO> clienteTags;

    private boolean ativo = true;
}
