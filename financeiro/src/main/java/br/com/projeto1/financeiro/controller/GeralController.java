package br.com.projeto1.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.service.GeralService;

@RestController
@CrossOrigin("*")
public class GeralController {
    
    @Autowired
    private GeralService geralServico;

    @GetMapping("")
    public String rota(){
        return "API do projeto funcionando!";
    }

    
    @GetMapping("/total")
    public Double total(){
        return geralServico.total();
    }

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/totalMensal/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return geralServico.totalMensal(mes, ano);
    }
}
