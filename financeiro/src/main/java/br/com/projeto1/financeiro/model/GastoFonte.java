package br.com.projeto1.financeiro.model;

import java.time.LocalDate;

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
@Table(name = "GastoFote")
@Getter
@Setter
public class GastoFonte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long codigo;

    private LocalDate dataCompra;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private double valor;

    private String motivo;

    @ManyToOne
    @JoinColumn(name = "Fonte_id")
    private Long fonte;
}
