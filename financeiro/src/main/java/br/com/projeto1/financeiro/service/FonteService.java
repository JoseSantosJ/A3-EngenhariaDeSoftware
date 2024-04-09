package br.com.projeto1.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.Repository.FonteRepository;
import br.com.projeto1.financeiro.model.Fonte;
import br.com.projeto1.financeiro.model.Resposta;

@Service
public class FonteService {
    @Autowired
    FonteRepository fr;

    @Autowired
    Resposta rm;

    //metodo para cadastrar ou alterar Fontes
    public ResponseEntity<?> cadastrarfonte (Fonte fm, String acao){
       
            if(fm.getNomefonte().equals("")){
                rm.setMensagem("a nome é obrigatorio!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else if(fm.getDiadopagamento() == 0){
                rm.setMensagem("o dia do pagamento é obrigatório!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            } else if(fm.getDialimite() == 0){
                rm.setMensagem("a data limite é obrigatoria!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if(acao.equals("cadastrarfonte")){
                    rm.setMensagem("deu");
                    return new ResponseEntity<Fonte>(fr.save(fm),HttpStatus.CREATED);
                }else{
                    rm.setMensagem("deu");
                    return new ResponseEntity<Fonte>(fr.save(fm),HttpStatus.OK);
                }
            }
        
        
    }

    //metodo para listar todos os gastos
    public Iterable<Fonte> listarfontes(){
        return fr.findAll();
    }
}
