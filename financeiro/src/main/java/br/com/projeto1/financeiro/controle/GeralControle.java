package br.com.projeto1.financeiro.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.servico.GeralServico;

@RestController
@CrossOrigin(origins = "*")
public class GeralControle {
    
    @Autowired
    private GeralServico geralServico;

    @GetMapping("/total")
    public Double total(){
        return geralServico.total();
    }


}
