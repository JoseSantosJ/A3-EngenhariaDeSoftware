package br.com.projeto1.financeiro.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.modelo.FontesModelo;
import br.com.projeto1.financeiro.servico.FontesServico;

@RestController
@CrossOrigin(origins = "*")
public class FontesControle {
    @Autowired
    FontesServico fs;

    @PostMapping("/cadastrarfonte")
    public ResponseEntity<?> cadastrar(@RequestBody FontesModelo fm){
        return fs.cadastrarfonte(fm, "cadastrarfonte");
    }

    @GetMapping("/listarfontes")
    public Iterable<FontesModelo> listar(){
        return fs.listarfontes();
    }


}
