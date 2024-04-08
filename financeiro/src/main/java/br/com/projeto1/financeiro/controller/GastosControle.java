//controle esta controlando as rotas(endereço)

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

import br.com.projeto1.financeiro.model.FontesModelo;
import br.com.projeto1.financeiro.model.GastosModelo;
import br.com.projeto1.financeiro.model.InfoModelo;
import br.com.projeto1.financeiro.model.RespostaModelo;
import br.com.projeto1.financeiro.service.compra.CreditoServico;
import br.com.projeto1.financeiro.service.compra.GastosServico;
import br.com.projeto1.financeiro.service.compra.ParcelamentoServico;

@RestController
@CrossOrigin(origins = "*")
public class GastosControle {

    @Autowired
    private GastosServico gs;
    
    @Autowired
    private ParcelamentoServico ps;

    @Autowired
    private CreditoServico cs;

   

    @DeleteMapping("/removergasto/{codigo}")
    public ResponseEntity<RespostaModelo> remover(@PathVariable long codigo){
        return gs.removergasto(codigo);
    }

    @PutMapping("/alterargasto")
    public ResponseEntity<?> alterar(@RequestBody GastosModelo gm){
        return gs.cadastrargasto(gm, "alterargasto");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrargasto")
    public ResponseEntity<?> cadastrar(@RequestBody GastosModelo gm){
        return gs.cadastrargasto(gm, "cadastrargasto");
    }
    
    //metodo post é para cadastrar
    @PostMapping("/cadastrargastoparcelado")
    public ResponseEntity<?> cadastrargastoparcelado(@RequestBody InfoModelo im){
        return ps.cadastrargastoparcelado(im, "cadastrargastoparcelado");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrargastocredito/{fonte}")
    public ResponseEntity<?> cadastrargastocredito(@RequestBody GastosModelo gastosModelo,@PathVariable Long fonte){
        return cs.cadastrargastocredito(gastosModelo,fonte, "cadastrargastocredito");
    }
    
    @GetMapping("/listargastos")
    public Iterable<GastosModelo> listar(){
        return gs.listargastos();
    }

    // Endpoint para listar gastos por mês e ano
    @GetMapping("/listargastos/{ano}/{mes}")
    public Iterable<GastosModelo> listarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gs.listargastosPorMesEAno(mes, ano);
    }
    @GetMapping("/gastosPorFonte/{fonte}")
    public Iterable<GastosModelo> listarPorFonte(@PathVariable Long fonte){
        return gs.listarGastosPorFonte(fonte);
    }

    @GetMapping("")
    public String rota(){
        return "API do projeto funcionando!";
    }



    @GetMapping("/somadosgasto")
    public Double somar(){
        return gs.somaDosGastos();
    }
    

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/somadosgasto/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gs.somaDosGastosPorMesEAno(mes, ano);
    }





    
}
