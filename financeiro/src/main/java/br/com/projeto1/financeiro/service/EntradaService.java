package br.com.projeto1.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.Repository.EntradaRepository;
import br.com.projeto1.financeiro.model.Entrada;
import br.com.projeto1.financeiro.model.Resposta;
@Service
public class EntradaService {
    

     @Autowired
    private EntradaRepository er;

    @Autowired
    private Resposta rm;

    //metodo para listar todos as entradas
    public Iterable<Entrada> listarentrada(){
        return er.findByOrderByData();
    }

    // Método para listar gastos por mês e ano
    public Iterable<Entrada> listarEntradasPorMesEAno(int mes, int ano) {
        return er.findByMesEAno(mes, ano);
    }

    public double somadasentradas(){
        return er.somadasentradas();
    }

    // Método para obter a soma dos gastos por mês e ano
    public Double somaDasEntradasPorMesEAno(int mes, int ano) {
        return er.somaDasEntradasPorMesEAno(mes, ano);
    }

    //metodo para cadastrar ou alterar entradas
    public ResponseEntity<?> cadastrarentrada (Entrada gm, String acao){
        if(gm.getData() == null){
            rm.setMensagem("a data é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(gm.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        } else if(gm.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if(acao.equals("cadastrarentrada")){
                return new ResponseEntity<Entrada>(er.save(gm),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<Entrada>(er.save(gm),HttpStatus.OK);
            }
        }
        
    }

    //metodo para remover entradas
    public ResponseEntity<Resposta> removerentrada(long codigo){
        er.deleteById(codigo);

        rm.setMensagem("Gasto removido com sucesso");
        return new ResponseEntity<Resposta>(rm, HttpStatus.OK);
    }
}
