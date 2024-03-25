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
@Getter
@Setter
public class EntradasModelo {
    @Id
    private long codigo;
   // @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;
   // @Column(columnDefinition = "DECIMAL(10,2)")
    private double valor;
    private String motivo;
    
}
