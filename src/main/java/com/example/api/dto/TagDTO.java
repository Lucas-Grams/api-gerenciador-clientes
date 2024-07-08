package com.example.api.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    private Long id;

    private String nome;;

    private boolean ativo = true;
}
