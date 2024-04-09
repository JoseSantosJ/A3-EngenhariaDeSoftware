package br.com.projeto1.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.model.Fonte;
import br.com.projeto1.financeiro.service.FonteService;

@RestController
@CrossOrigin(origins = "*")
public class FonteController {
    @Autowired
    FonteService fonteService;

    @PostMapping("/cadastrarfonte")
    public ResponseEntity<?> cadastrar(@RequestBody Fonte fonte){
        return fonteService.cadastrarfonte(fonte, "cadastrarfonte");
    }

    @GetMapping("/listarfontes")
    public Iterable<Fonte> listar(){
        return fonteService.listarfontes();
    }


}
