package br.com.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.financeiro.Repository.FonteRepository;
import br.com.financeiro.model.Fonte;
import br.com.financeiro.model.Resposta;

@Service
public class FonteService {
    @Autowired
    FonteRepository fonteRepository;

    @Autowired
    Resposta rm;

    /**
     * cadastra ou altera uma Fontes
     * @param fonte fonte que se deseja cadastrar ou alterar
     * @param acao ação que se deseja exercer .: cadastrar,alterar
     * @return
     */
    public ResponseEntity<?> cadastrarfonte (Fonte fonte, String acao){
       
            if(fonte.getNomefonte().equals("")){
                rm.setMensagem("a nome é obrigatorio!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else if(fonte.getDiadopagamento() == 0){
                rm.setMensagem("o dia do pagamento é obrigatório!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            } else if(fonte.getDialimite() == 0){
                rm.setMensagem("a data limite é obrigatoria!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if(acao.equals("cadastrarfonte")){
                    rm.setMensagem("deu");
                    return new ResponseEntity<Fonte>(fonteRepository.save(fonte),HttpStatus.CREATED);
                }else{
                    rm.setMensagem("deu");
                    return new ResponseEntity<Fonte>(fonteRepository.save(fonte),HttpStatus.OK);
                }
            }
        
        
    }

    /***
     * lista todas as fontes
     * @return retorna todas as fontes cadastradas
     */
    public Iterable<Fonte> listarfontes(){
        return fonteRepository.findAll();
    }
}
