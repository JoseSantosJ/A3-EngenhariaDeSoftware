package br.com.projeto1.financeiro.controle;

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

import br.com.projeto1.financeiro.modelo.EntradasModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.servico.EntradasServico;

@RestController
@CrossOrigin("*")
public class EntradasControle {
    @Autowired
    private EntradasServico es;


    @DeleteMapping("/removerentrada/{codigo}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable long codigo){
        return es.removerentrada(codigo);
    }

    @PutMapping("/alterarentrada")
    public ResponseEntity<?> alterar(@RequestBody EntradasModelo gm){
        return es.cadastrarentrada(gm, "alterargasto");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrarentrada")
    public ResponseEntity<?> cadastrar(@RequestBody EntradasModelo gm){
        return es.cadastrarentrada(gm, "cadastrarentrada");
    }

    @GetMapping("/listarentrada")
    public Iterable<EntradasModelo> listar(){
        return es.listarentrada();
    }
    @GetMapping("/listarentradas/{ano}/{mes}")
    public Iterable<EntradasModelo> listarPorMesEAnoe(@PathVariable int ano, @PathVariable int mes) {
        return es.listarEntradasPorMesEAno(mes, ano);
    }


    @GetMapping("/somadasentradas")
    public Double somar(){
        return es.somadasentradas();
    }

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/somadasentradas/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return es.somaDasEntradasPorMesEAno(mes, ano);
    }
    
}
