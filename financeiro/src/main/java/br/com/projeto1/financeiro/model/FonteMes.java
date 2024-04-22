package br.com.projeto1.financeiro.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "FonteMes")
@Getter
@Setter
public class FonteMes {
    @Id 
    private String id;

    private String nome;

    @OneToMany(mappedBy = "fontemes")
    @JsonManagedReference
    private List<Gasto> gastos;
}
