package com.sistems.pdv_ws.controller;

import com.sistems.pdv_ws.model.Funcionario;
import com.sistems.pdv_ws.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> adicionarFuncionario(@RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(funcionarioService.salvarFuncionario(funcionario));
    }
}
