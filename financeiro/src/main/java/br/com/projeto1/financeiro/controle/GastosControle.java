//controle esta controlando as rotas(endereço)

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

import br.com.projeto1.financeiro.modelo.GastosModelo;
import br.com.projeto1.financeiro.modelo.InfoModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.servico.compra.CreditoServico;
import br.com.projeto1.financeiro.servico.compra.GastosServico;
import br.com.projeto1.financeiro.servico.compra.ParcelamentoServico;

@RestController
@CrossOrigin(origins = "https://fronta3.onrender.com/")
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
    @PostMapping("/cadastrargastocredito")
    public ResponseEntity<?> cadastrargastocredito(@RequestBody InfoModelo im){
        return cs.cadastrargastocredito(im, "cadastrargastocredito");
    }
    
    @GetMapping("/listargastos")
    public Iterable<GastosModelo> listar(){
        return gs.listargastos();
    }

    @GetMapping("")
    public String rota(){
        return "API do projeto funcionando!";
    }



    @GetMapping("/somadosgasto")
    public Double somar(){
        return gs.somaDosGastos();
    }




    // Endpoint para listar gastos por mês e ano
    @GetMapping("/listargastos/{ano}/{mes}")
    public Iterable<GastosModelo> listarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gs.listargastosPorMesEAno(mes, ano);
    }

    // Endpoint para obter a soma dos gastos por mês e ano
    @GetMapping("/somadosgasto/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gs.somaDosGastosPorMesEAno(mes, ano);
    }





    
}
