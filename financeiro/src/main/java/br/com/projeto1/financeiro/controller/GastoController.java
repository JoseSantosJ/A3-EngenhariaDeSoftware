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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto1.financeiro.Repository.InfoRepository;
import br.com.projeto1.financeiro.model.Gasto;
import br.com.projeto1.financeiro.model.Info;
import br.com.projeto1.financeiro.model.Resposta;
import br.com.projeto1.financeiro.service.GastoService;

@RestController
@RequestMapping("/gastos")
@CrossOrigin("*")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private InfoRepository infoRepository;



    // Listar
    @GetMapping("")
    public Iterable<Gasto> listar(){
        return gastoService.listargastos();
    }

    @GetMapping("/fonte/{fonte}")
    public Iterable<Gasto> listarPorFonte(@PathVariable Long fonte){
        return gastoService.listarGastosPorFonte(fonte);
    }

    // Endpoint para listar gastos por mês e ano
    @GetMapping("/{ano}/{mes}")
    public Iterable<Gasto> listarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gastoService.listargastosPorMesEAno(mes, ano);
    }

    @GetMapping("/fontes/{fonte}/{ano}/{mes}")
    public Iterable<Gasto> listarGastosPorFonteEMes(@PathVariable long fonte,@PathVariable int ano, @PathVariable int mes){
        return gastoService.listarGastosPorMesEFonte(fonte, mes, ano);
    }

    @GetMapping("/semfontes")
    public Iterable<Gasto> listarGastosSemFonte(){
        return gastoService.listarGastosSemFontes();
    }

    @GetMapping("/{fonteMesId}")
    public Iterable<Info> listarInfoFonteMes(@PathVariable String fonteMesId){
        return infoRepository.findByFonteMes(fonteMesId);
    }



    //_____________________________________________________________________________________________________________________

    //cadastrar
    //metodo post é para cadastrar
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Gasto gm){
        return gastoService.cadastrargasto(gm, "cadastrargasto");
    }
    //metodo post é para cadastrar
    @PostMapping("/cadastrarparcelamento")
    public ResponseEntity<?> cadastrargastoparcelado(@RequestBody Info im){
        return gastoService.cadastrargastoparcelado(im, "cadastrargastoparcelado");
    }

    //metodo post é para cadastrar
    @PostMapping("/cadastrargastocredito/{fonte}")
    public ResponseEntity<?> cadastrargastocredito(@RequestBody Gasto gasto,@PathVariable Long fonte){
        return gastoService.cadastrargastocredito(gasto,fonte, "cadastrargastocredito");
    }

    //_____________________________________________________________________________________________________________________

    //remover
    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<Resposta> remover(@PathVariable long codigo){
        return gastoService.removergasto(codigo);
    }
    //_____________________________________________________________________________________________________________________
    
    //alterar
    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody Gasto gm){
        return gastoService.cadastrargasto(gm, "alterargasto");
    }
    //_____________________________________________________________________________________________________________________

    //somar
    @GetMapping("/soma")
    public Double somar(){
        return gastoService.somaDosGastos();
    }
    

    // a soma dos gastos por mês e ano
    @GetMapping("/soma/{ano}/{mes}")
    public Double somarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gastoService.somaDosGastosPorMesEAno(mes, ano);
    }
    
    @GetMapping("/somadafonte/{fonte}/{ano}/{mes}")
    public Double somaDosGastosPorMesEFonte(@PathVariable long fonte,@PathVariable int ano, @PathVariable int mes){
        return gastoService.somaDosGastosPorMesEFonte(fonte, mes, ano);
    }
    //_____________________________________________________________________________________________________________________





    
}
