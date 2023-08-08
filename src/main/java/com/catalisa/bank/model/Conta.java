package com.catalisa.bank.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "numero_conta")
    private String numeroConta;

    @Column(nullable = false)
    private Double saldo;



}
