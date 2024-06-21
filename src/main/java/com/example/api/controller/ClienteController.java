package com.example.api.controller;
import com.example.api.dto.ClienteDTO;
import com.example.api.dto.ResponseDTO;
import com.example.api.model.Cliente;
import com.example.api.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(
            ClienteService clienteService
    ) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseDTO<List<ClienteDTO>> getClientes() {
        return this.clienteService.getClientes();
    }

    @GetMapping("/{uuid}")
    public ResponseDTO<ClienteDTO> getCliente(@PathVariable String uuid) {
        return this.clienteService.getCliente(uuid);
    }

    @PostMapping
    public ResponseDTO postCliente(@RequestBody ClienteDTO cliente) {
        return this.clienteService.postCliente(cliente);
    }

    @DeleteMapping("/{uuid}")
    public ResponseDTO deleteCliente(@PathVariable String uuid) {
        return this.clienteService.deleteCliente(uuid);
    }
}
