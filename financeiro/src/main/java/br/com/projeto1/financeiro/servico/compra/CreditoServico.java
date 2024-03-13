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
    public ResponseEntity<?> cadastrargastocredito (InfoModelo im, String acao){
       
            if(im.getDatac() == null){
                rm.setMensagem("a data  da compra é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            } else if(im.getValor() == 0){
                rm.setMensagem("o valor é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else{
                
                
                if(acao.equals("cadastrargastocredito")){
                    im.setNdp(1);
                    im.setValordp(im.getValor());
                    im.setTipo('i');
                    ir.save(im);
                    fm = fr.findById(im.getFonte()).orElse(null);
                    GastosModelo gm = new GastosModelo();
                    LocalDate datapagamento = im.getDatac();
                    if(im.getDatac().getDayOfMonth() >= fm.getDialimite()){
                        
                        gm.setData(datapagamento = datapagamento.plusMonths(2).withDayOfMonth(fm.getDiadopagamento()));
                    }else {
                        gm.setData(datapagamento = datapagamento.plusMonths(1).withDayOfMonth(fm.getDiadopagamento()));
                    }
                    
                    gm.setInfo(im.getCodigoinf());
                    gm.setMotivo(im.getMotivo());
                    gm.setValor(im.getValor()) ;
                    gm.setTipo('c');
                    gm.setFonte(im.getFonte());
                    gr.save(gm);






                    
                    return new ResponseEntity<InfoModelo>(ir.save(im),HttpStatus.CREATED);
                }else{

                    im.setNdp(1);
                    im.setValordp(im.getValor());
                    im.setTipo('i');
                    ir.save(im);
                    fm = fr.findById(im.getFonte()).orElse(null);
                    GastosModelo gm = new GastosModelo();
                    LocalDate datapagamento = im.getDatac();
                    if(im.getDatac().getDayOfMonth() >= fm.getDialimite()){
                        
                        gm.setData(datapagamento = datapagamento.plusMonths(2).withDayOfMonth(fm.getDiadopagamento()));
                    }else {
                        gm.setData(datapagamento = datapagamento.plusMonths(1).withDayOfMonth(fm.getDiadopagamento()));
                    }
                    
                    gm.setInfo(im.getCodigoinf());
                    gm.setMotivo(im.getMotivo());
                    gm.setValor(im.getValor()) ;
                    gm.setTipo('c');
                    gm.setFonte(im.getFonte()); 
                    gr.save(gm);



                    im.setTipo('c');
                    return new ResponseEntity<InfoModelo>(ir.save(im),HttpStatus.OK);
                }
            }
        
        
    } 
}
