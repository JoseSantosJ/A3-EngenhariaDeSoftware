package br.com.projeto1.financeiro.model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Gastos")
@Getter
@Setter
public class GastosModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;
    //data da compra

    @Column(columnDefinition = "DECIMAL(10,2)")
    private double valor;

    private String motivo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    //tipo
    private char tipo;

    //chave estrangeira codigo info    
    private long info;

    //chave estrangeira codigo fonte
    private long fonte;

    
}
