package br.com.projeto1.financeiro.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fonte")
@Getter
@Setter
public class Fonte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigofonte;

    private String nomefonte;

    private int dialimite;

    private int diadopagamento;

    @OneToMany(mappedBy = "fonte_id")
    private List<GastoFonte> gastoFonte;

    



}
