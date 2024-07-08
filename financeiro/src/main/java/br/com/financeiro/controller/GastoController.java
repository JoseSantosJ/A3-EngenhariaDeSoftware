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

import br.com.financeiro.Repository.GastoRepository;
import br.com.financeiro.Repository.InfoRepository;
import br.com.financeiro.model.Gasto;
import br.com.financeiro.model.Info;
import br.com.financeiro.model.Resposta;
import br.com.financeiro.service.GastoService;

@RestController
@RequestMapping("/gastos")
@CrossOrigin("*")
public class GastoController {

    @Autowired
    private GastoService gastoService;
    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private InfoRepository infoRepository;



    // Listar
    /**
     * Metodo para Listar todos os Gastos registrados
     * @return retorna todos os gastos registrados
     */
    @GetMapping("")
    public Iterable<Gasto> listar(){
        return gastoService.listargastos();
    }

    /**
     * metodo para listar Gastos cadastrados em uma fonte especifica
     * @param fonte (codigofonte da fonte que esta sendo requisitado os gastos)
     * @return retorna todos os gastos registrado na fonte requisitada
     */
    @GetMapping("/fonte/{fonte}")
    public Iterable<Gasto> listarPorFonte(@PathVariable Long fonte){
        return gastoService.listarGastosPorFonte(fonte);
    }

    /**
     * Metodo para listar os gasto de um determinado mês
     * @param ano (ano que esta sendo requisitado o mês)
     * @param mes (o mês especifico do qual se quer saber os gastos) 
     * @return retorna todos os gastos registrado no mês requisitado
     */
    @GetMapping("/{ano}/{mes}")
    public Iterable<Gasto> listarPorMesEAno(@PathVariable int ano, @PathVariable int mes) {
        return gastoService.listargastosPorMesEAno(mes, ano);
    }

    /**
     * metodo para listar todos os gastos de uma fonte em um mês especifico
     * @param fonte codigoFonte da fonte que se deseja obter os gastos
     * @param ano ano em que se encontra o mês desejado
     * @param mes mes que se deseja obter os gastos
     * @return retorna todos os gastos da fonte no mês solicitado
     */
    @GetMapping("/fontes/{fonte}/{ano}/{mes}")
    public Iterable<Gasto> listarGastosPorFonteEMes(@PathVariable long fonte,@PathVariable int ano, @PathVariable int mes){
        return gastoService.listarGastosPorMesEFonte(fonte, mes, ano);
    }

    /**
     * metodo para listar os gastos que não possuem fontes
     * @return retorna todos os gastos que tem codigoFonte igual a zero
     */
    @GetMapping("/semfontes")
    public Iterable<Gasto> listarGastosSemFonte(){
        return gastoService.listarGastosSemFontes();
    }

    /**
     * 
     * @param fonteMesId
     * @return
     */
    @GetMapping("/{fonteMesId}")
    public Iterable<Gasto> lista(@PathVariable String fonteMesId){
        return gastoRepository.listarGastosFonte(fonteMesId);
    }



    //_____________________________________________________________________________________________________________________

    //cadastrar
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Gasto gm){
        return gastoService.cadastrargasto(gm, "cadastrargasto");
    }
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
    
    @GetMapping("/somadafonte/{fonteMesId}")
    public Double somaDosGastosPorMesEFonte(@PathVariable String fonteMesId){
        return gastoService.somaDosGastosPorMesEFonte(fonteMesId);
    }
    //_____________________________________________________________________________________________________________________





    
}
