package br.com.financeiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.financeiro.model.Entrada;
import br.com.financeiro.model.Resposta;
import br.com.financeiro.service.EntradaService;

@RestController
@RequestMapping("/entradas")
@CrossOrigin("*")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;


    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Resposta> remover(@PathVariable long codigo){
        return entradaService.removerentrada(codigo);
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Entrada entrada){
        return entradaService.cadastrarentrada(entrada, "alteraentrada");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Entrada entrada){
        return entradaService.cadastrarentrada(entrada, "cadastrarentrada");
    }

    @GetMapping("")
    public Iterable<Entrada> listar(){
        return entradaService.listarentrada();
    }
    @GetMapping("/{ano}/{mes}")
    public Iterable<Entrada> listarPorMesEAnoe(@PathVariable int ano, @PathVariable int mes) {
        return entradaService.listarEntradasPorMesEAno(mes, ano);
    }


    @GetMapping("/soma")
    public Double somar(){
        return entradaService.somadasentradas();
    }

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/soma/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return entradaService.somaDasEntradasPorMesEAno(mes, ano);
    }
    
}
