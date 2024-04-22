package br.com.projeto1.financeiro.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.Repository.FonteMesRepository;
import br.com.projeto1.financeiro.model.FonteMes;

@RestController
@RequestMapping("/fontesMes")
@CrossOrigin("*")
public class FonteMesController {
    @Autowired
    private FonteMesRepository fonteMesRepository;

    @GetMapping("")
    public Iterable<FonteMes> listar(){
        return fonteMesRepository.findByOrderById();
    }

    @GetMapping("/{id}")
    public Optional<FonteMes> findById(@PathVariable String id){
        return fonteMesRepository.findById(id);
    }
}
