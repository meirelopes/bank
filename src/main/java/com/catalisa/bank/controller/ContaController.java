package com.catalisa.bank.controller;

import com.catalisa.bank.model.Conta;
import com.catalisa.bank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    ContaService contaService;

    @GetMapping
    public List<Conta> listar() {

        return contaService.listar();

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {

        Optional<Conta> conta = contaService.buscarPorId(id);

        if (conta.isPresent()) {

            return ResponseEntity.ok(conta.get());

        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @DeleteMapping(path = "/{id}")
    public void excluir(@PathVariable Long id) {

        contaService.excluir(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Conta adicionar(@RequestBody Conta conta) {

        return contaService.salvar(conta);

    }

    @PostMapping("/sacar/{id}")
    public ResponseEntity<?> sacarDinheiro(@PathVariable Long id, @RequestParam Double valorSaque) {
            boolean saqueBemSucedido = contaService.sacarDinheiro(id, valorSaque);

            if (saqueBemSucedido) {
                return ResponseEntity.ok("Saque realizado com sucesso!");
            } else{

                throw  new EmptyResultDataAccessException(1);

        }

    }

    @PostMapping("/depositar/{id}")
    public ResponseEntity<String> depositarDinheiro(@PathVariable Long id,
                                                    @RequestParam Double valorDeposito) {

        boolean saqueBemSucedido = contaService.depositarDinheiro(id, valorDeposito);

        if (saqueBemSucedido) {
            return ResponseEntity.ok("Depósito realizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada!");
        }
    }

   @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> tratarExceção(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Número de conta não encontrado!");

    }
}
