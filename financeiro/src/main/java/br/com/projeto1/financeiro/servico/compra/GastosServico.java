//essa classe determina serviços como cadastrar, alterar,remover, e retorno sobre o serviço prestado

package br.com.projeto1.financeiro.servico.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.model.GastosModelo;
import br.com.projeto1.financeiro.model.InfoModelo;
import br.com.projeto1.financeiro.model.RespostaModelo;
import br.com.projeto1.financeiro.repositorio.GastosRepositorio;
import br.com.projeto1.financeiro.repositorio.InfoRepositorio;

@Service
public class GastosServico {
    
    @Autowired
    private GastosRepositorio gr;

    @Autowired
    private InfoRepositorio ir;

    @Autowired
    private RespostaModelo rm;

    @Autowired
    private CreditoServico creditoServico;


    

    //metodo para listar todos os gastos
    public Iterable<GastosModelo> listargastos(){
        return gr.findByOrderByData();//gr.findAll();
    }

    public Iterable<GastosModelo> listarGastosPorFonte(Long fonte){
        return gr.findByFonte(fonte);
    }


    // Método para listar gastos por mês e ano
    public Iterable<GastosModelo> listargastosPorMesEAno(int mes, int ano) {
        return gr.findByMesEAno(mes, ano);
    }


    //metodo para cadastrar ou alterar gastos
    public ResponseEntity<?> cadastrargasto (GastosModelo gm, String acao){
       
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
                if(acao.equals("cadastrargasto")){
                    gm.setTipo('a');
                    return new ResponseEntity<GastosModelo>(gr.save(gm),HttpStatus.CREATED);
                }else{
    
                    return new ResponseEntity<GastosModelo>(gr.save(gm),HttpStatus.OK);
                }
            }
        
        
    }

    //metodo para remover gastos
    public ResponseEntity<RespostaModelo> removergasto(long codigo){
        GastosModelo gm = gr.findById(codigo).orElse(null);

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
            return new ResponseEntity<RespostaModelo>(rm, HttpStatus.OK);
        } else {
            rm.setMensagem("Gasto não encontrado");
            return new ResponseEntity<RespostaModelo>(rm, HttpStatus.NOT_FOUND);
        }
    }


    



    public double somaDosGastos(){
        return gr.somaDosGastos();
    }


    // Método para obter a soma dos gastos por mês e ano
    public Double somaDosGastosPorMesEAno(int mes, int ano) {
        return gr.somaDosGastosPorMesEAno(mes, ano);
    }






    

}
