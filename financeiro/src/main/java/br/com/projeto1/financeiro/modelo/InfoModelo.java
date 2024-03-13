package br.com.projeto1.financeiro.modelo;

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
@Table(name = "Info")
@Getter
@Setter
public class InfoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigoinf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datac;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private double valor;

    private String motivo;

    //valor da parcela
    @Column(columnDefinition = "DECIMAL(10,2)")
    private double valordp;

    //numero de parcelas
    private int ndp;

    //tipo
    private char tipo;

    private Long fonte;
}
