package br.com.financeiro.model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Gastos")
@Getter
@Setter
public class Gasto {
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

    @ManyToOne
    @JoinColumn(name = "fontemes_id")
    @JsonBackReference
    private FonteMes fontemes;

    @JsonProperty("fontemes_id")
    public String getFonteId() {
        return this.fontemes != null ? this.fontemes.getId() : null;
    }
    
}
