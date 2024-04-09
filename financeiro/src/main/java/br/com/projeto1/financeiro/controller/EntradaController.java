package br.com.projeto1.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.model.Entrada;
import br.com.projeto1.financeiro.model.Resposta;
import br.com.projeto1.financeiro.service.EntradaService;

@RestController
@CrossOrigin("*")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;


    @DeleteMapping("/removerentrada/{codigo}")
    public ResponseEntity<Resposta> remover(@PathVariable long codigo){
        return entradaService.removerentrada(codigo);
    }

    @PutMapping("/alterarentrada")
    public ResponseEntity<?> alterar(@RequestBody Entrada entrada){
        return entradaService.cadastrarentrada(entrada, "alteraentrada");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrarentrada")
    public ResponseEntity<?> cadastrar(@RequestBody Entrada entrada){
        return entradaService.cadastrarentrada(entrada, "cadastrarentrada");
    }

    @GetMapping("/listarentrada")
    public Iterable<Entrada> listar(){
        return entradaService.listarentrada();
    }
    @GetMapping("/listarentradas/{ano}/{mes}")
    public Iterable<Entrada> listarPorMesEAnoe(@PathVariable int ano, @PathVariable int mes) {
        return entradaService.listarEntradasPorMesEAno(mes, ano);
    }


    @GetMapping("/somadasentradas")
    public Double somar(){
        return entradaService.somadasentradas();
    }

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/somadasentradas/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return entradaService.somaDasEntradasPorMesEAno(mes, ano);
    }
    
}
