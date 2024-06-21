package com.example.api.service;

import com.example.api.dto.ClienteDTO;
import com.example.api.dto.ClienteTagDTO;
import com.example.api.dto.ResponseDTO;
import com.example.api.model.Cliente;
import com.example.api.model.ClienteTag;
import com.example.api.model.Tag;
import com.example.api.repository.ClienteRepository;
import com.example.api.repository.ClienteTagRepository;
import com.example.api.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TagRepository tagRepository;
    private final ClienteTagRepository clienteTagRepository;

    public ClienteService(
            ClienteRepository clienteRepository,
            TagRepository tagRepository,
            ClienteTagRepository clienteTagRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.tagRepository = tagRepository;
        this.clienteTagRepository = clienteTagRepository;
    }

    @Transactional
    public ResponseDTO<List<ClienteDTO>> getClientes() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        List<Cliente> clientes = this.clienteRepository.findAll();
        clientes.forEach(cliente -> {
            clientesDTO.add(ClienteDTO.builder()
                    .id(cliente.getId())
                    .uuid(cliente.getUuid())
                    .nome(cliente.getNome())
                    .email(cliente.getEmail())
                    .ativo(cliente.isAtivo())
                    .clienteTags(cliente.getClienteTags().stream().map(ct -> {
                        return ClienteTagDTO.builder()
                                .tagId(ct.getTag().getId())
                                .build();
                    }).collect(Collectors.toList()))
                    .build());
        });
        try{
            if(clientes.isEmpty()) return ResponseDTO.ok("Nenhum cliente encontrada");
            return ResponseDTO.ok(clientesDTO);
        } catch (Exception e) {
            return ResponseDTO.err("Erro ao buscar clientes");
        }
    }

    @Transactional
    public ResponseDTO<ClienteDTO> getCliente(String uuid) {
        try {
            Cliente cliente = clienteRepository.findByUuid(UUID.fromString(uuid))
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            if(cliente == null) return ResponseDTO.ok("Cliente não encontrado");

            ClienteDTO clienteDTO = ClienteDTO.builder()
                    .id(cliente.getId())
                    .nome(cliente.getNome())
                    .uuid(cliente.getUuid())
                    .email(cliente.getEmail())
                    .ativo(cliente.isAtivo())
                    .clienteTags(cliente.getClienteTags().stream().map(ct -> {
                        return ClienteTagDTO.builder()
                                .tagId(ct.getTag().getId())
                                .build();
                    }).collect(Collectors.toList()))
                    .build();

            return ResponseDTO.ok(clienteDTO);
        } catch (Exception e) {
            return ResponseDTO.err("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseDTO postCliente(ClienteDTO clienteDTO) {
        try {
            Cliente clienteSave;
            if (clienteDTO.getId() != null) {
                clienteSave = clienteRepository.findById(clienteDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                clienteSave.updateFromDTO(clienteDTO);
                if(!clienteDTO.getClienteTags().isEmpty()) clienteSave = this.setClienteTags(clienteSave, clienteDTO.getClienteTags());
            } else {
                clienteSave = new Cliente(clienteDTO);
                if(!clienteDTO.getClienteTags().isEmpty()) clienteSave = this.setClienteTags(clienteSave, clienteDTO.getClienteTags());
            }

            List<ClienteTag> clienteTagsAssociadas = new ArrayList<>();
            if (clienteDTO.getClienteTags() != null && !clienteDTO.getClienteTags().isEmpty()) {
                for (ClienteTagDTO ctg : clienteDTO.getClienteTags()) {
                    Tag tag = tagRepository.findById(ctg.getTagId())
                            .orElseThrow(() -> new RuntimeException("Tag não encontrada: " + ctg.getTagId()));
                    ClienteTag clienteTag = new ClienteTag();
                    clienteTag.setCliente(clienteSave);
                    clienteTag.setTag(tag);
                    clienteTagsAssociadas.add(clienteTag);
                }
            }

            // Atualizar as referências de ClienteTag
            this.clienteTagRepository.deleteByClienteId(clienteSave.getId());
            if(clienteSave.getClienteTags() != null) {
                clienteSave.getClienteTags().clear();
                clienteSave.getClienteTags().addAll(clienteTagsAssociadas);
            } else {
                clienteSave.setClienteTags(new ArrayList<>());
            }


            // Persistir o cliente atualizado
            clienteSave = clienteRepository.save(clienteSave);

            String message = (clienteDTO.getId() != null) ? "Cliente atualizado com sucesso" : "Cliente cadastrado com sucesso";
            return ResponseDTO.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.err("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }


    @Transactional
    public ResponseDTO deleteCliente(String uuid) {
        Cliente cliente = clienteRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        try {
            this.clienteTagRepository.deleteByClienteId(cliente.getId());
            clienteRepository.deleteById(cliente.getId());
            return ResponseDTO.ok("Cliente deletado com sucesso");
        } catch (Exception e) {
            return ResponseDTO.err("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    private List<ClienteTag> getClienteTags(Long clienteId) {
        return this.clienteTagRepository.findByClienteId(clienteId);
    }

    private Cliente setClienteTags(Cliente cliente, List<ClienteTagDTO> clienteTags) {
        List<ClienteTag> clienteTagsAssociadas = new ArrayList<>();
        if (clienteTags != null && !clienteTags.isEmpty()) {
            for (ClienteTagDTO ctg : clienteTags) {
                Tag tag = tagRepository.findById(ctg.getTagId())
                        .orElseThrow(() -> new RuntimeException("Tag não encontrada: " + ctg.getTagId()));
                ClienteTag clienteTag = new ClienteTag();
                clienteTag.setCliente(cliente);
                clienteTag.setTag(tag);
                clienteTagsAssociadas.add(clienteTag);
            }
        }
        cliente.setClienteTags(clienteTagsAssociadas);
        return cliente;
    }
}