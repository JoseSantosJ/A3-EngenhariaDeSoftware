package br.com.financeiro.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class Resposta {
    
    private String mensagem;
}
