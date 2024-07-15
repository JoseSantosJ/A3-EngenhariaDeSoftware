//essa classe determina serviços como cadastrar, alterar,remover, e retorno sobre o serviço prestado

package br.com.financeiro.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.financeiro.Repository.FonteMesRepository;
import br.com.financeiro.Repository.FonteRepository;
import br.com.financeiro.Repository.GastoRepository;
import br.com.financeiro.Repository.InfoRepository;
import br.com.financeiro.model.Fonte;
import br.com.financeiro.model.FonteMes;
import br.com.financeiro.model.Gasto;
import br.com.financeiro.model.Info;
import br.com.financeiro.model.Resposta;

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

    /**
     * lista todos os gastos
     * @return retorna todos os gastos registrados
     */
    public Iterable<Gasto> listargastos(){
        return gastoRepository.findByOrderByData();//gastoRepository.findAll();
    }

    /**
     * lista todos os gastos da com o mesmo codigo da fonte fornecida
     * @param fonte codigofonte da fonte que se deseja obter os gastos
     * @return retorna todos os gastos que tem como codigofonte o numero fornecida
     */
    public Iterable<Gasto> listarGastosPorFonte(Long fonte){
        return gastoRepository.findByFonte(fonte);
    }

    /**
     * lista todos os gastos registrados em uma fonte em um mês especifico
     * @param fonte codigofonte da fonte que se deseja obter os gastos cadastrados
     * @param mes numero do mês que se deseja obter os gastos cadastrados
     * @param ano ano que se deseja obter os gastos cadastrados
     * @return retorna todos os gastos que possuem o codigofonte igual ao Long fornecida e no ano e mês requisitados
     */
    public Iterable<Gasto> listarGastosPorMesEFonte(Long fonte,int mes,int ano){
        return fonteMesRepository.GastosMesEFonte(fonte, mes, ano);
    }


    /**
     * lista todos os gastos no ano e mês informado
     * @param mes numero do mês que se deseja obter os gastos cadastrados
     * @param ano ano que se deseja obter os gastos cadastrados
     * @return retorna todos os gastos no ano e mês requisitados
     */
    public Iterable<Gasto> listargastosPorMesEAno(int mes, int ano) {
        return gastoRepository.findByMesEAno(mes, ano);
    }

    /**
     * lista todos os gastos que não possuem fonte
     * @return retorna todos os gastos que tem codigoFonte igual a zero
     */
    public Iterable<Gasto>listarGastosSemFontes(){
        return gastoRepository.gastoSemFonte();
    }

    //_____________________________________________________________________________________________________________________

    /*metodos para cadastrar */

    /**
     * Cadastra ou altera um gasto
     * @param gasto gasto que se deseja cadastrar 
     * @param acao ação que sera exercida .:"cadastrar" ; "alterar"
     * @return retorna o status da requisição
     */
    public ResponseEntity<?> cadastrargasto (Gasto gasto, String acao){
       
            if(gasto.getData() == null){
                rm.setMensagem("a data é obrigatoria!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else if(gasto.getMotivo().equals("")){
                rm.setMensagem("o motivo é obrigatório!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            } else if(gasto.getValor() == 0){
                rm.setMensagem("o valor é obrigatorio!");
                return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
            }else{
                if(gasto.getCodigo() == 0){
                    gasto.setTipo('a');
                    
                    return new ResponseEntity<Gasto>(gastoRepository.save(gasto),HttpStatus.CREATED);
                }else{
    
                    return new ResponseEntity<Gasto>(gastoRepository.save(gasto),HttpStatus.OK);
                }
                
            }
        
        
    }

    /**
     * Cadastra ou altera um gasto com cartão de credito(Gasto com fonte)
     * @param gasto gasto que se deseja cadastrar 
     * @param codigoFonte codigoFonte da fonte na qual se quer cadastrar o gasto
     * @param acao ação que sera exercida .:"cadastrar" ; "alterar"
     * @return retorna o status da requisição
     */
    public ResponseEntity<?> cadastrargastocredito (Gasto gasto,Long codigoFonte, String acao){
       
        if(gasto.getData() == null){
            rm.setMensagem("a data  da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(gasto.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        } else if(gasto.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            Info infoModelo = new Info();
            
            if(acao.equals("cadastrargastocredito")){

                fonte = fonteRepository.findById(codigoFonte).orElse(null);

                LocalDate diaDoPagamento;

                infoModelo.setDatac(gasto.getData());
                
                if(gasto.getData().getDayOfMonth() >= fonte.getDialimite()){
                    diaDoPagamento = gasto.getData().plusMonths(2).withDayOfMonth(fonte.getDiadopagamento());
                    gasto.setData(infoModelo.getDatac());
                }else {
                    diaDoPagamento = gasto.getData().plusMonths(1).withDayOfMonth(fonte.getDiadopagamento());
                    gasto.setData(infoModelo.getDatac());
                }

                FonteMes fonteMes = GastosFonteMes(codigoFonte, diaDoPagamento.getMonthValue(), diaDoPagamento.getYear());
                
                infoModelo.setValor(gasto.getValor());
                infoModelo.setMotivo(gasto.getMotivo());
                infoModelo.setValordp(gasto.getValor());
                infoModelo.setNdp(1);
                infoModelo.setTipo('i');
                infoModelo.setFonte(codigoFonte);
                infoModelo.setFonteMes(fonteMes.getId());
                infoRepository.save(infoModelo);
                

                
                
                gasto.setInfo(infoModelo.getCodigoinf());
                gasto.setTipo('c');
                gasto.setFonte(fonte.getCodigofonte());
                gasto.setFontemes(fonteMes);
                gastoRepository.save(gasto);
                Gasto gastoFonteMes =  gastoRepository.findByFontemesId(""+codigoFonte+diaDoPagamento.getMonthValue()+diaDoPagamento.getYear());
                double valorFonteMes = fonteMesRepository.somaDosGastosMesEFonte(fonteMes.getId());
                gastoFonteMes.setValor(valorFonteMes);
                gastoRepository.save(gastoFonteMes);
                infoModelo.setGastoId(gastoFonteMes.getCodigo());
                infoRepository.save(infoModelo);

                fonteMesRepository.save(fonteMes);

                return new ResponseEntity<Gasto>(gastoRepository.save(gasto),HttpStatus.CREATED);  
            }else{
                return new ResponseEntity<Info>(infoRepository.save(infoModelo),HttpStatus.OK);
            }
        }

        
    
    
    }

    /**
     * verifica se há Gastos cadastrado na fonte informada no mês requisitado
     * @param codigoFonte codigoFonte da fonte que se deseja verificar
     * @param mes mês que se deseja verificar
     * @param ano ano em que se deseja verificar
     * @return retorna uma FonteMes
     */
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
    
    /**
     * cadastra ou altera um gasto parcelado
     * @param info informação sobre o gasto parcelado que se deseja cadastrar
     * @param acao ação que sera exercida .:"cadastrar" ; "alterar"
     * @return retorna o status da requisição
     */
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


    /**
     * cadastra ou altera um gasto parcelados com fonte (Gasto parcelado no cartão credito)
     * @param info informação sobre o gasto parcelado que se deseja cadastrar
     * @param acao acao ação que sera exercida .:"cadastrar" ; "alterar"
     * @param fonte codigoFonte da fonte na qual se quer cadastrar os gastos
     * @return retorna o status da requisição
     */
    public ResponseEntity<?> cadastrocreditoparcelado (Info info, String acao ){
    
   
        if(info.getDatac() == null){
            rm.setMensagem("a data da compra é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(info.getDatac() == null){
            rm.setMensagem("a data do pagamento é obrigatorio!");
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

    /**
     * caso seja um gasto avista  remove um gasto
     * caso seja um gasto parecelado remove todas as parcelas
     * @param codigo codigo do gasto que se deseja remover
     * @return retorna o status da requisição
     */
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


    /**
     * apresenta o soma de todos os valores de gastos registrados
     * @return retorna o valor total de todos os gastos cadastrados
     */
    public double somaDosGastos(){
        return gastoRepository.somaDosGastos();
    }


    /**
     * apresenta a soma dos gastos por mês e ano
     * @param mes mês que se deseja obter a soma
     * @param ano ano que se deseja obter a soma
     * @return retorna o valor total de todos os gastos cadastrado no mês informado
     */
    public Double somaDosGastosPorMesEAno(int mes, int ano) {
        return gastoRepository.somaDosGastosPorMesEAno(mes, ano);
    }

    /**
     * apresenta a soma de todos os gastos cadastrado na fonte e no mês informado
     * @param id id da fonteMes desejado
     * @return retorna o valor dos total dos gastos cadastrados na fonte e mês informados
     */
    public Double somaDosGastosPorMesEFonte(String id){
        return fonteMesRepository.somaDosGastosMesEFonte(id);
    }

    //_____________________________________________________________________________________________________________________






    

}
