package com.catalisa.bank.service;

import com.catalisa.bank.model.Conta;
import com.catalisa.bank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public List<Conta> listar() {

        return contaRepository.findAll();

    }

    public Optional<Conta> buscarPorId(Long id){

        return contaRepository.findById(id);

    }

    public void excluir(Long id) {

        contaRepository.deleteById(id);

    }

    public Conta salvar(Conta conta){

        return contaRepository.save(conta);

    }


    public boolean sacarDinheiro(Long idConta, Double valorSaque) {

        boolean encontrouConta = false;

        Optional<Conta> optionalConta = contaRepository.findById(idConta);

        if (optionalConta.isPresent()) {
            Conta conta = optionalConta.get();

            if (conta.getSaldo() >= valorSaque) {
                conta.setSaldo(conta.getSaldo() - valorSaque);
                contaRepository.save(conta);
                encontrouConta = true;

            } else if (conta.getSaldo() < valorSaque) {
                System.out.println("Saldo insuficiente");
                encontrouConta = false;
            }
        }
        return encontrouConta;
    }

    public boolean depositarDinheiro(Long idConta, Double valorDeposito) {
        Optional<Conta> optionalConta = contaRepository.findById(idConta);

        if (optionalConta.isPresent()) {
            Conta conta = optionalConta.get();

            conta.setSaldo(conta.getSaldo() + valorDeposito);
            contaRepository.save(conta);
            return true;

        } else {
            return false;
        }

    }

}
