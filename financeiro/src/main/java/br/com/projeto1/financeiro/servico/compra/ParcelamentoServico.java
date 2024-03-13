package br.com.projeto1.financeiro.servico.compra;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.modelo.GastosModelo;
import br.com.projeto1.financeiro.modelo.InfoModelo;
import br.com.projeto1.financeiro.modelo.RespostaModelo;
import br.com.projeto1.financeiro.repositorio.GastosRepositorio;
import br.com.projeto1.financeiro.repositorio.InfoRepositorio;

@Service
public class ParcelamentoServico {
    @Autowired
    private GastosRepositorio gr;

    @Autowired
    private InfoRepositorio ir;
    

    @Autowired
    private RespostaModelo rm;
    
    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrargastoparcelado (InfoModelo im, String acao ){
        
       
            if(im.getDatac() == null){
                rm.setMensagem("a data da compra é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }
            else if(im.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getValordp() == 0 && (im.getValor() == 0 || im.getNdp() == 0)){
                
                
                rm.setMensagem("o valor da parcela ou valor total é obrigatorio!1");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
                
            } else if(im.getValor() == 0 && (im.getValordp() == 0 || im.getNdp() == 0)){
                rm.setMensagem("o valor total ou o valor da parcela é obrigatorio!2");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getNdp() == 0){
                rm.setMensagem("o valor da parcela é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if (acao.equals("cadastrargastoparcelado")) {
                    if(im.getValor() == 0) {
                        im.setValor(im.getValordp() * im.getNdp());
                    }
                    if(im.getValordp() == 0) {
                        im.setValordp(im.getValor() / im.getNdp());
                    }
                    int p = 0;
                    String motivoo = im.getMotivo();
                    LocalDate dataParcela = im.getDatac();
                    InfoModelo infoModelo = im;
                    ir.save(infoModelo);

                    for (int i = im.getNdp(); i > 0; i--) {
                        p++;
                        motivoo = im.getMotivo()+" " + p + "/" + im.getNdp();
                        dataParcela = dataParcela.plusMonths(1);
                        
                        GastosModelo novoGasto = new GastosModelo();
                        novoGasto.setData(dataParcela);
                        novoGasto.setMotivo(motivoo);
                        novoGasto.setValor(im.getValordp()); 
                        novoGasto.setTipo('p');
                        novoGasto.setInfo(im.getCodigoinf());

                        gr.save(novoGasto);
                                    
                    }
                    im.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<InfoModelo>(ir.save(im), HttpStatus.CREATED);
                    
                } else {
                    if(im.getValor() == 0) {
                        im.setValor(im.getValordp() * im.getNdp());
                    }
                    if(im.getValordp() == 0) {
                        im.setValordp(im.getValor() / im.getNdp());
                    } 
                    int p = 0;
                    String motivoo = im.getMotivo();
                    LocalDate dataParcela = im.getDatac();
                    InfoModelo infoModelo = im;
                    ir.save(infoModelo);

                    for (int i = im.getNdp(); i > 0; i--) {
                        p++;
                        motivoo = im.getMotivo() +" "+ p + "/" + im.getNdp();
                        dataParcela = dataParcela.plusMonths(1);

                        GastosModelo novoGasto = new GastosModelo();
                        novoGasto.setData(dataParcela);
                        novoGasto.setMotivo(motivoo);
                        novoGasto.setValor(im.getValordp());
                        novoGasto.setTipo('p');
                        novoGasto.setInfo(im.getCodigoinf());

                        gr.save(novoGasto);
                                    
                        }
                        im.setTipo('i');
                        rm.setMensagem("ops algo deu errado");
                        return new ResponseEntity<InfoModelo>(ir.save(im), HttpStatus.CREATED);
                    }
                }
            
        
            
        
    }


        //metodo para cadastrar ou alterar gastos
        public ResponseEntity<?> cadastrocredito (InfoModelo im, String acao ){
        
       
            if(im.getDatac() == null){
                rm.setMensagem("a data da compra é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getDatac() == null){
                rm.setMensagem("a data do pagamento é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }
            else if(im.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getValordp() == 0 && (im.getValor() == 0 || im.getNdp() == 0)){
                
                
                rm.setMensagem("o valor da parcela ou valor total é obrigatorio!1");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
                
            } else if(im.getValor() == 0 && (im.getValordp() == 0 || im.getNdp() == 0)){
                rm.setMensagem("o valor total ou o valor da parcela é obrigatorio!2");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else if(im.getNdp() == 0){
                rm.setMensagem("o valor da parcela é obrigatorio!");
                return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if (acao.equals("cadastrargastoparcelado")) {
                    if(im.getValor() == 0) {
                        im.setValor(im.getValordp() * im.getNdp());
                    }
                    if(im.getValordp() == 0) {
                        im.setValordp(im.getValor() / im.getNdp());
                    }
                    int p = 0;
                    String motivoo = im.getMotivo();
                    LocalDate dataParcela = im.getDatac();
                    InfoModelo infoModelo = im;
                    ir.save(infoModelo);

                    for (int i = im.getNdp(); i > 0; i--) {
                        p++;
                        motivoo = im.getMotivo()+" " + p + "/" + im.getNdp();
                        dataParcela = dataParcela.plusMonths(1);
                        
                        GastosModelo novoGasto = new GastosModelo();
                        novoGasto.setData(dataParcela);
                        novoGasto.setMotivo(motivoo);
                        novoGasto.setValor(im.getValordp()); 
                        novoGasto.setTipo('p');
                        novoGasto.setInfo(im.getCodigoinf());

                        gr.save(novoGasto);
                                    
                    }
                    im.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<InfoModelo>(ir.save(im), HttpStatus.CREATED);
                    
                } else {
                    if(im.getValor() == 0) {
                        im.setValor(im.getValordp() * im.getNdp());
                    }
                    if(im.getValordp() == 0) {
                        im.setValordp(im.getValor() / im.getNdp());
                    } 
                    int p = 0;
                    String motivoo = im.getMotivo();
                    LocalDate dataParcela = im.getDatac();
                    InfoModelo infoModelo = im;
                    ir.save(infoModelo);

                    for (int i = im.getNdp(); i > 0; i--) {
                        p++;
                        motivoo = im.getMotivo() +" "+ p + "/" + im.getNdp();
                        dataParcela = dataParcela.plusMonths(1);

                        GastosModelo novoGasto = new GastosModelo();
                        novoGasto.setData(dataParcela);
                        novoGasto.setMotivo(motivoo);
                        novoGasto.setValor(im.getValordp());
                        novoGasto.setTipo('p');
                        novoGasto.setInfo(im.getCodigoinf());

                        gr.save(novoGasto);
                                    
                        }
                        im.setTipo('i');
                        rm.setMensagem("ops algo deu errado");
                        return new ResponseEntity<InfoModelo>(ir.save(im), HttpStatus.CREATED);
                    }
                }
            
        
            
        
    }
}

/*ideia para preencher a compr  por credito
    a pessoa vai comprar dando a informação de dia da compra o valor e o nome do objeto
    se ela selecionar um cartão de credito o cartão de credito vai cadastrar automaticamente a data do pagamento
 
 */