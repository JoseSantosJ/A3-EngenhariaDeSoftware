//essa classe determina serviços como cadastrar, alterar,remover, e retorno sobre o serviço prestado

package br.com.projeto1.financeiro.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.Repository.FonteRepository;
import br.com.projeto1.financeiro.Repository.GastoRepository;
import br.com.projeto1.financeiro.Repository.InfoRepository;
import br.com.projeto1.financeiro.model.Fonte;
import br.com.projeto1.financeiro.model.Gasto;
import br.com.projeto1.financeiro.model.Info;
import br.com.projeto1.financeiro.model.Resposta;

@Service
public class GastoService {
    
    @Autowired
    private GastoRepository gr;

    @Autowired
    private InfoRepository ir;

    @Autowired
    private Resposta rm;

    //@Autowired
    Fonte fm;

   // @Autowired
    private FonteRepository fr;


    /*metodos para listar */

    //metodo para listar todos os gastos
    public Iterable<Gasto> listargastos(){
        return gr.findByOrderByData();//gr.findAll();
    }

    public Iterable<Gasto> listarGastosPorFonte(Long fonte){
        return gr.findByFonte(fonte);
    }


    // Método para listar gastos por mês e ano
    public Iterable<Gasto> listargastosPorMesEAno(int mes, int ano) {
        return gr.findByMesEAno(mes, ano);
    }

    //_____________________________________________________________________________________________________________________

    /*metodos para cadastrar */

    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrargasto (Gasto gm, String acao){
       
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
                if(acao.equals("cadastrargasto")){
                    gm.setTipo('a');
                    return new ResponseEntity<Gasto>(gr.save(gm),HttpStatus.CREATED);
                }else{
    
                    return new ResponseEntity<Gasto>(gr.save(gm),HttpStatus.OK);
                }
            }
        
        
    }

    //metodo para cadastrar ou alterar gastos no credito
    public ResponseEntity<?> cadastrargastocredito (Gasto gastosModelo,Long fonte, String acao){
       
        if(gastosModelo.getData() == null){
            rm.setMensagem("a data  da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(gastosModelo.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        } else if(gastosModelo.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            Info infoModelo = new Info();
            
            if(acao.equals("cadastrargastocredito")){
                infoModelo.setDatac(gastosModelo.getData());
                infoModelo.setValor(gastosModelo.getValor());
                infoModelo.setMotivo(gastosModelo.getMotivo());
                infoModelo.setValordp(gastosModelo.getValor());
                infoModelo.setNdp(1);
                infoModelo.setTipo('i');
                infoModelo.setFonte(fonte);
                ir.save(infoModelo);
                fm = fr.findById(fonte).orElse(null);
                
                if(gastosModelo.getData().getDayOfMonth() >= fm.getDialimite()){
                    
                    gastosModelo.setData(gastosModelo.getData().plusMonths(2).withDayOfMonth(fm.getDiadopagamento()));
                }else {
                    gastosModelo.setData(gastosModelo.getData().plusMonths(1).withDayOfMonth(fm.getDiadopagamento()));
                }
                
                gastosModelo.setInfo(infoModelo.getCodigoinf());
                gastosModelo.setTipo('c');
                gastosModelo.setFonte(fm.getCodigofonte());
               
                return new ResponseEntity<Gasto>(gr.save(gastosModelo),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<Info>(ir.save(infoModelo),HttpStatus.OK);
            }
        }

        
    
    
    }
    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrargastoparcelado (Info im, String acao ){
        
       
        if(im.getDatac() == null){
            rm.setMensagem("a data da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }
        else if(im.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(im.getValordp() == 0 && (im.getValor() == 0 || im.getNdp() == 0)){
            
            
            rm.setMensagem("o valor da parcela ou valor total é obrigatorio!1");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            
        } else if(im.getValor() == 0 && (im.getValordp() == 0 || im.getNdp() == 0)){
            rm.setMensagem("o valor total ou o valor da parcela é obrigatorio!2");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(im.getNdp() == 0){
            rm.setMensagem("o valor da parcela é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
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
                Info infoModelo = im;
                ir.save(infoModelo);

                for (int i = im.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = im.getMotivo()+" " + p + "/" + im.getNdp();
                    dataParcela = dataParcela.plusMonths(1);
                    
                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(im.getValordp()); 
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(im.getCodigoinf());

                    gr.save(novoGasto);
                                
                }
                im.setTipo('i');
                rm.setMensagem("ops algo deu errado");
                return new ResponseEntity<Info>(ir.save(im), HttpStatus.CREATED);
                
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
                Info infoModelo = im;
                ir.save(infoModelo);

                for (int i = im.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = im.getMotivo() +" "+ p + "/" + im.getNdp();
                    dataParcela = dataParcela.plusMonths(1);

                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(im.getValordp());
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(im.getCodigoinf());

                    gr.save(novoGasto);
                                
                    }
                    im.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<Info>(ir.save(im), HttpStatus.CREATED);
                }
            }
        
    
        
    
}


    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrocreditoparcelado (Info im, String acao ){
    
   
        if(im.getDatac() == null){
            rm.setMensagem("a data da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(im.getDatac() == null){
            rm.setMensagem("a data do pagamento é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }
        else if(im.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(im.getValordp() == 0 && (im.getValor() == 0 || im.getNdp() == 0)){
            
            
            rm.setMensagem("o valor da parcela ou valor total é obrigatorio!1");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            
        } else if(im.getValor() == 0 && (im.getValordp() == 0 || im.getNdp() == 0)){
            rm.setMensagem("o valor total ou o valor da parcela é obrigatorio!2");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(im.getNdp() == 0){
            rm.setMensagem("o valor da parcela é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
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
                Info infoModelo = im;
                ir.save(infoModelo);

                for (int i = im.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = im.getMotivo()+" " + p + "/" + im.getNdp();
                    dataParcela = dataParcela.plusMonths(1);
                    
                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(im.getValordp()); 
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(im.getCodigoinf());

                    gr.save(novoGasto);
                                
                }
                im.setTipo('i');
                rm.setMensagem("ops algo deu errado");
                return new ResponseEntity<Info>(ir.save(im), HttpStatus.CREATED);
                
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
                Info infoModelo = im;
                ir.save(infoModelo);

                for (int i = im.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = im.getMotivo() +" "+ p + "/" + im.getNdp();
                    dataParcela = dataParcela.plusMonths(1);

                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(im.getValordp());
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(im.getCodigoinf());

                    gr.save(novoGasto);
                                
                    }
                    im.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<Info>(ir.save(im), HttpStatus.CREATED);
                }
            }
        
    
        
    
    } 
    //_____________________________________________________________________________________________________________________

    /*metodos para remover */

    //metodo para remover gastos
    public ResponseEntity<Resposta> removergasto(long codigo){
        Gasto gm = gr.findById(codigo).orElse(null);

        if (gm != null ) {
            //caso seja do tipo parcelado
            if (gm.getTipo() == 'p') {
                //deletar da tabela info
                ir.deleteById(gm.getInfo());

                //deletar todos os registro com o mesmo codigoinfo
                gr.deleteByInfo(gm.getInfo());
            }else if (gm.getTipo() == 'c') {
                //deletar da tabela info
                ir.deleteById(gm.getInfo());

                //deletar todos os registro com o mesmo codigoinfo
                gr.deleteById(codigo);
            }else{
                gr.deleteById(codigo);
            }

           

            rm.setMensagem("Gasto removido com sucesso");
            return new ResponseEntity<Resposta>(rm, HttpStatus.OK);
        } else {
            rm.setMensagem("Gasto não encontrado");
            return new ResponseEntity<Resposta>(rm, HttpStatus.NOT_FOUND);
        }
    }

    

    //_____________________________________________________________________________________________________________________


    /*metodos para somar */



    /*public double somaDosGastos(){
        return gr.somaDosGastos();
    }


    // Método para obter a soma dos gastos por mês e ano
    public Double somaDosGastosPorMesEAno(int mes, int ano) {
        return gr.somaDosGastosPorMesEAno(mes, ano);
    }
*/

    //_____________________________________________________________________________________________________________________






    

}
