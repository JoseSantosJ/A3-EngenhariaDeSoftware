//essa classe determina serviços como cadastrar, alterar,remover, e retorno sobre o serviço prestado

package br.com.projeto1.financeiro.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.Repository.FonteMesRepository;
import br.com.projeto1.financeiro.Repository.FonteRepository;
import br.com.projeto1.financeiro.Repository.GastoRepository;
import br.com.projeto1.financeiro.Repository.InfoRepository;
import br.com.projeto1.financeiro.model.Fonte;
import br.com.projeto1.financeiro.model.FonteMes;
import br.com.projeto1.financeiro.model.Gasto;
import br.com.projeto1.financeiro.model.Info;
import br.com.projeto1.financeiro.model.Resposta;

@Service
public class GastoService {
    
    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private Resposta rm;

    //@Autowired
    private Fonte fonte;

    @Autowired
    private FonteRepository fonteRepository;

    @Autowired
    private FonteMesRepository fonteMesRepository;


    /*metodos para listar */

    //metodo para listar todos os gastos
    public Iterable<Gasto> listargastos(){
        return gastoRepository.findByOrderByData();//gastoRepository.findAll();
    }

    public Iterable<Gasto> listarGastosPorFonte(Long fonte){
        return gastoRepository.findByFonte(fonte);
    }

    public Iterable<Gasto> listarGastosPorMesEFonte(Long fonte,int mes,int ano){
        return fonteMesRepository.GastosMesEFonte(fonte, mes, ano);
    }


    // Método para listar gastos por mês e ano
    public Iterable<Gasto> listargastosPorMesEAno(int mes, int ano) {
        return gastoRepository.findByMesEAno(mes, ano);
    }

    public Iterable<Gasto>listarGastosSemFontes(){
        return gastoRepository.gastoSemFonte();
    }

    //_____________________________________________________________________________________________________________________

    /*metodos para cadastrar */

    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrargasto (Gasto gm, String acao){
       
            if(gm.getData() == null){
                rm.setMensagem("a data é obrigatoria!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else if(gm.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            } else if(gm.getValor() == 0){
                rm.setMensagem("o valor é obrigatorio!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if(gm.getCodigo() == 0){
                    gm.setTipo('a');
                    
                    return new ResponseEntity<Gasto>(gastoRepository.save(gm),HttpStatus.CREATED);
                }else{
    
                    return new ResponseEntity<Gasto>(gastoRepository.save(gm),HttpStatus.OK);
                }
                
            }
        
        
    }

    //metodo para cadastrar ou alterar gastos no credito
    public ResponseEntity<?> cadastrargastocredito (Gasto gastosModelo,Long codigoFonte, String acao){
       
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

                fonte = fonteRepository.findById(codigoFonte).orElse(null);

                LocalDate diaDoPagamento;

                infoModelo.setDatac(gastosModelo.getData());
                
                if(gastosModelo.getData().getDayOfMonth() >= fonte.getDialimite()){
                    diaDoPagamento = gastosModelo.getData().plusMonths(2).withDayOfMonth(fonte.getDiadopagamento());
                    gastosModelo.setData(infoModelo.getDatac());
                }else {
                    diaDoPagamento = gastosModelo.getData().plusMonths(1).withDayOfMonth(fonte.getDiadopagamento());
                    gastosModelo.setData(infoModelo.getDatac());
                }

                FonteMes fonteMes = GastosFonteMes(codigoFonte, diaDoPagamento.getMonthValue(), diaDoPagamento.getYear());
                
                infoModelo.setValor(gastosModelo.getValor());
                infoModelo.setMotivo(gastosModelo.getMotivo());
                infoModelo.setValordp(gastosModelo.getValor());
                infoModelo.setNdp(1);
                infoModelo.setTipo('i');
                infoModelo.setFonte(codigoFonte);
                infoModelo.setFonteMes(fonteMes.getId());
                infoRepository.save(infoModelo);
                

                
                
                gastosModelo.setInfo(infoModelo.getCodigoinf());
                gastosModelo.setTipo('c');
                gastosModelo.setFonte(fonte.getCodigofonte());
                gastosModelo.setFontemes(fonteMes);
                gastoRepository.save(gastosModelo);
                Gasto gastoFonteMes =  gastoRepository.findByFontemesId(""+codigoFonte+diaDoPagamento.getMonthValue()+diaDoPagamento.getYear());
                double valorFonteMes = fonteMesRepository.somaDosGastosMesEFonte(fonteMes.getId());
                gastoFonteMes.setValor(valorFonteMes);
                gastoRepository.save(gastoFonteMes);
                infoModelo.setGastoId(gastoFonteMes.getCodigo());
                infoRepository.save(infoModelo);

                fonteMesRepository.save(fonteMes);

                return new ResponseEntity<Gasto>(gastoRepository.save(gastosModelo),HttpStatus.CREATED);  
            }else{
                return new ResponseEntity<Info>(infoRepository.save(infoModelo),HttpStatus.OK);
            }
        }

        
    
    
    }

    public FonteMes GastosFonteMes(long codigoFonte, int mes, int ano){
        String id = (""+codigoFonte+mes+ano);
        FonteMes fonteMes = fonteMesRepository.findById(id).orElse(null);

        if (fonteMes == null) {
            Fonte fonte = fonteRepository.findById(codigoFonte).orElse(null);
            Gasto gastoFonteMes = new Gasto();
            LocalDate data = LocalDate.of(ano, mes, fonte.getDiadopagamento());
            gastoFonteMes.setMotivo(fonte.getNomefonte());
            gastoFonteMes.setTipo('f');
            gastoFonteMes.setData(data);
            
            

            if (fonte != null) {
                fonteMes = new FonteMes();
                fonteMes.setId(id);
                fonteMes.setNome(fonte.getNomefonte());
                fonteMesRepository.save(fonteMes);
            }
            gastoFonteMes.setFontemes(fonteMes);
            gastoRepository.save(gastoFonteMes);

            

        }
        return fonteMes;

        
        /*Double soma = somaDosGastosPorMesEFonte(codigoFonte, mes, ano);
        if (soma == null) {
            Fonte fonte = fonteRepository.findById(codigoFonte).orElse(null);
            if (fonte != null) {
                //cadastrarFonteMes(codigoFonte, mes, ano);
                

                FonteMes fonteMes = new FonteMes();

                String id = (""+codigoFonte+mes+ano);
                fonteMes.setId(id);

                fonteMes.setFonte(fonte); 
                
                fonteMesRepository.save(fonteMes);
                LocalDate data = LocalDate.of(ano, mes, fonte.getDiadopagamento());
                Gasto novoGasto = new Gasto();
                novoGasto.setData(data);
                novoGasto.setValor(0.0); // Defina o valor inicial como 0.0
                novoGasto.setMotivo(fonte.getNomefonte());
                novoGasto.setTipo('f');
                gastoRepository.save(novoGasto);
            }
        } else {
            String id = (""+codigoFonte+mes+ano);
            FonteMes fonteMes = fonteMesRepository.findById(id).orElse(null);
            fonteMesRepository.save(fonteMes);
        }*/

    }
    //metodo para cadastrar ou alterar gastos parcelados
    public ResponseEntity<?> cadastrargastoparcelado (Info info, String acao ){
        
       
        if(info.getDatac() == null){
            rm.setMensagem("a data da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }
        else if(info.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(info.getValordp() == 0 && (info.getValor() == 0 || info.getNdp() == 0)){
            
            
            rm.setMensagem("o valor da parcela ou valor total é obrigatorio!1");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            
        } else if(info.getValor() == 0 && (info.getValordp() == 0 || info.getNdp() == 0)){
            rm.setMensagem("o valor total ou o valor da parcela é obrigatorio!2");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(info.getNdp() == 0){
            rm.setMensagem("o valor da parcela é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if (acao.equals("cadastrargastoparcelado")) {
                if(info.getValor() == 0) {
                    info.setValor(info.getValordp() * info.getNdp());
                }
                if(info.getValordp() == 0) {
                    info.setValordp(info.getValor() / info.getNdp());
                }
                int p = 0;
                String motivoo = info.getMotivo();
                LocalDate dataParcela = info.getDatac();
                Info infoModelo = info;
                infoRepository.save(infoModelo);

                for (int i = info.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = info.getMotivo()+" " + p + "/" + info.getNdp();
                    dataParcela = dataParcela.plusMonths(1);
                    
                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(info.getValordp()); 
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(info.getCodigoinf());

                    gastoRepository.save(novoGasto);
                                
                }
                info.setTipo('i');
                rm.setMensagem("ops algo deu errado");
                return new ResponseEntity<Info>(infoRepository.save(info), HttpStatus.CREATED);
                
            } else {
                if(info.getValor() == 0) {
                    info.setValor(info.getValordp() * info.getNdp());
                }
                if(info.getValordp() == 0) {
                    info.setValordp(info.getValor() / info.getNdp());
                } 
                int p = 0;
                String motivoo = info.getMotivo();
                LocalDate dataParcela = info.getDatac();
                Info infoModelo = info;
                infoRepository.save(infoModelo);

                for (int i = info.getNdp(); i > 0; i--) {
                    p++;
                    motivoo = info.getMotivo() +" "+ p + "/" + info.getNdp();
                    dataParcela = dataParcela.plusMonths(1);

                    Gasto novoGasto = new Gasto();
                    novoGasto.setData(dataParcela);
                    novoGasto.setMotivo(motivoo);
                    novoGasto.setValor(info.getValordp());
                    novoGasto.setTipo('p');
                    novoGasto.setInfo(info.getCodigoinf());

                    gastoRepository.save(novoGasto);
                                
                    }
                    info.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<Info>(infoRepository.save(info), HttpStatus.CREATED);
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
                infoRepository.save(infoModelo);

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

                    gastoRepository.save(novoGasto);
                                
                }
                im.setTipo('i');
                rm.setMensagem("ops algo deu errado");
                return new ResponseEntity<Info>(infoRepository.save(im), HttpStatus.CREATED);
                
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
                infoRepository.save(infoModelo);

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

                    gastoRepository.save(novoGasto);
                                
                    }
                    im.setTipo('i');
                    rm.setMensagem("ops algo deu errado");
                    return new ResponseEntity<Info>(infoRepository.save(im), HttpStatus.CREATED);
                }
            }
    } 

   /* public void cadastrarFonteMes(long codigoFonte, int mes, int ano){
        FonteMes fonteMes = new FonteMes();
        fonteMes.setFonte_id(codigoFonte);        

        String id = (""+codigoFonte+mes+ano);
        fonteMes.setId(id);

        fonte = fonteRepository.findById(codigoFonte).orElse(null);

        fonteMes.setNome(fonte.getNomefonte());
        
    }*/

    //_____________________________________________________________________________________________________________________

    /*metodos para remover */

    //metodo para remover gastos
    public ResponseEntity<Resposta> removergasto(long codigo){
        Gasto gm = gastoRepository.findById(codigo).orElse(null);

        if (gm != null ) {
            //caso seja do tipo parcelado
            if (gm.getTipo() == 'p') {
                //deletar da tabela info
                infoRepository.deleteById(gm.getInfo());

                //deletar todos os registro com o mesmo codigoinfo
                gastoRepository.deleteByInfo(gm.getInfo());
            }else if (gm.getTipo() == 'c') {
                //deletar da tabela info
                infoRepository.deleteById(gm.getInfo());

                //deletar todos os registro com o mesmo codigoinfo
                gastoRepository.deleteById(codigo);
            }else{
                gastoRepository.deleteById(codigo);
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



    public double somaDosGastos(){
        return gastoRepository.somaDosGastos();
    }


    // Método para obter a soma dos gastos por mês e ano
    public Double somaDosGastosPorMesEAno(int mes, int ano) {
        return gastoRepository.somaDosGastosPorMesEAno(mes, ano);
    }

    public Double somaDosGastosPorMesEFonte(String id){
        return fonteMesRepository.somaDosGastosMesEFonte(id);
    }

    //_____________________________________________________________________________________________________________________






    

}
