package br.com.projeto1.financeiro.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fonte")
@Getter
@Setter
public class FontesModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigofonte;

    private String nomefonte;

    private int dialimite;

    private int diadopagamento;

    



}
