package br.com.projeto1.financeiro.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.modelo.FontesModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.repositorio.FontesRepositorio;

@Service
public class FontesServico {
    @Autowired
    FontesRepositorio fr;

    @Autowired
    RespostaModelo rm;

    //metodo para cadastrar ou alterar Fontes
    public ResponseEntity<?> cadastrarfonte (FontesModelo fm, String acao){
       
            if(fm.getNomefonte().equals("")){
                rm.setMensagem("a nome é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(fm.getDiadopagamento() == 0){
                rm.setMensagem("o dia do pagamento é obrigatório!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            } else if(fm.getDialimite() == 0){
                rm.setMensagem("a data limite é obrigatoria!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if(acao.equals("cadastrarfonte")){
                    rm.setMensagem("deu");
                    return new ResponseEntity<FontesModelo>(fr.save(fm),HttpStatus.CREATED);
                }else{
                    rm.setMensagem("deu");
                    return new ResponseEntity<FontesModelo>(fr.save(fm),HttpStatus.OK);
                }
            }
        
        
    }

    //metodo para listar todos os gastos
    public Iterable<FontesModelo> listarfontes(){
        return fr.findAll();
    }
}
