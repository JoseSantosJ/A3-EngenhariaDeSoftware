package br.com.projeto1.financeiro.servico.compra;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.modelo.FontesModelo;
import br.com.projeto1.financeiro.modelo.GastosModelo;
import br.com.projeto1.financeiro.modelo.InfoModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.repositorio.FontesRepositorio;
import br.com.projeto1.financeiro.repositorio.GastosRepositorio;
import br.com.projeto1.financeiro.repositorio.InfoRepositorio;

@Service
public class CreditoServico {
    @Autowired
    RespostaModelo rm;

    @Autowired
    FontesRepositorio fr;

    @Autowired
    InfoRepositorio ir;

    
    FontesModelo fm;

    @Autowired
    GastosRepositorio gr;

    
   //metodo para cadastrar ou alterar gastos no credito
    public ResponseEntity<?> cadastrargastocredito (GastosModelo gastosModelo,Long fonte, String acao){
       
            if(gastosModelo.getData() == null){
                rm.setMensagem("a data  da compra é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(gastosModelo.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            } else if(gastosModelo.getValor() == 0){
                rm.setMensagem("o valor é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else{
                InfoModelo infoModelo = new InfoModelo();
                
                if(acao.equals("cadastrargastocredito")){
                    infoModelo.setDatac(gastosModelo.getData());
                    infoModelo.setValor(gastosModelo.getValor());
                    infoModelo.setMotivo(gastosModelo.getMotivo());
                    infoModelo.setValordp(gastosModelo.getValor());
                    infoModelo.setNdp(1);
                    infoModelo.setTipo('i');
                    infoModelo.setFonte(fonte);
                    ir.save(infoModelo);
                    LocalDate datapagamento = gastosModelo.getData();
                    fm = fr.findById(fonte).orElse(null);
                    
                    if(gastosModelo.getData().getDayOfMonth() >= fm.getDialimite()){
                        
                        gastosModelo.setData(gastosModelo.getData().plusMonths(2).withDayOfMonth(fm.getDiadopagamento()));
                    }else {
                        gastosModelo.setData(gastosModelo.getData().plusMonths(1).withDayOfMonth(fm.getDiadopagamento()));
                    }
                    
                    gastosModelo.setInfo(infoModelo.getCodigoinf());
                    gastosModelo.setTipo('c');
                    gastosModelo.setFonte(fm.getCodigofonte());
                   
                    return new ResponseEntity<GastosModelo>(gr.save(gastosModelo),HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<GastosModelo>(gr.save(gastosModelo),HttpStatus.OK);
                }
            }
        
        
    } 
}
