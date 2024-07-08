package br.com.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.financeiro.Repository.EntradaRepository;
import br.com.financeiro.model.Entrada;
import br.com.financeiro.model.Resposta;
@Service
public class EntradaService {
    

     @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private Resposta rm;

    //metodo para listar todos as entradas
    public Iterable<Entrada> listarentrada(){
        return entradaRepository.findByOrderByData();
    }

    // Método para listar gastos por mês e ano
    public Iterable<Entrada> listarEntradasPorMesEAno(int mes, int ano) {
        return entradaRepository.findByMesEAno(mes, ano);
    }

    public double somadasentradas(){
        return entradaRepository.somadasentradas();
    }

    // Método para obter a soma dos gastos por mês e ano
    public Double somaDasEntradasPorMesEAno(int mes, int ano) {
        return entradaRepository.somaDasEntradasPorMesEAno(mes, ano);
    }

    //metodo para cadastrar ou alterar entradas
    public ResponseEntity<?> cadastrarentrada (Entrada entrada, String acao){
        if(entrada.getData() == null){
            rm.setMensagem("a data é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(entrada.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        } else if(entrada.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if(acao.equals("cadastrarentrada")){
                return new ResponseEntity<Entrada>(entradaRepository.save(entrada),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<Entrada>(entradaRepository.save(entrada),HttpStatus.OK);
            }
        }
        
    }

    //metodo para remover entradas
    public ResponseEntity<Resposta> removerentrada(long codigo){
        entradaRepository.deleteById(codigo);

        rm.setMensagem("Gasto removido com sucesso");
        return new ResponseEntity<Resposta>(rm, HttpStatus.OK);
    }
}
