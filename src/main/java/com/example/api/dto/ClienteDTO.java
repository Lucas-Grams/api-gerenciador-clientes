package com.example.api.dto;

import com.example.api.model.ClienteTag;
import com.example.api.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
