package br.com.projeto1.financeiro.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.modelo.EntradasModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.repositorio.EntradasRepositorio;
@Service
public class EntradasServico {
    

     @Autowired
    private EntradasRepositorio er;

    @Autowired
    private RespostaModelo rm;

    //metodo para listar todos as entradas
    public Iterable<EntradasModelo> listarentrada(){
        return er.findAll();
    }

  /*   public double somadasentradas(){
        return er.somadasentradas();
    }*/

    //metodo para cadastrar ou alterar entradas
    public ResponseEntity<?> cadastrarentrada (EntradasModelo gm, String acao){
        if(gm.getData() == null){
            rm.setMensagem("a data é obrigatorio!");
            return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
        }else if(gm.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
        } else if(gm.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if(acao.equals("cadastrarentrada")){
                return new ResponseEntity<EntradasModelo>(er.save(gm),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<EntradasModelo>(er.save(gm),HttpStatus.OK);
            }
        }
        
    }

    //metodo para remover entradas
    public ResponseEntity<RespostaModelo> removerentrada(long codigo){
        er.deleteById(codigo);

        rm.setMensagem("Gasto removido com sucesso");
        return new ResponseEntity<RespostaModelo>(rm, HttpStatus.OK);
    }
}
