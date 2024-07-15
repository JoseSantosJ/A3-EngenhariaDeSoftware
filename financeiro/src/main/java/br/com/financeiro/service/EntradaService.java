package br.com.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.financeiro.Repository.EntradaRepository;
import br.com.financeiro.model.Entrada;
import br.com.financeiro.model.Resposta;
@Service
public class EntradaService {
    

     @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private Resposta rm;

    /**
     * lista todos as receitas
     * @return retorna todas as entradas cadastradas
     */
    public Iterable<Entrada> listarentrada(){
        return entradaRepository.findByOrderByData();
    }

    /**
     * lista as receitascadastradas no mês e ano especificado
     * @param mes mês que se deseja verificar
     * @param ano ano que se deseja verificar
     * @return retorna as receitas cadastradas no mês e ano informados
     */
    public Iterable<Entrada> listarEntradasPorMesEAno(int mes, int ano) {
        return entradaRepository.findByMesEAno(mes, ano);
    }

    /**
     * apresenta o valor da soma de todas as receitas cadastradas
     * @return retorna a soma dos valores das receitas cadastradas
     */
    public double somadasentradas(){
        return entradaRepository.somadasentradas();
    }

    /**
     * retorna o valor da soma das receitas do mês do ano requisitado
     * @param mes mês que se deseja verificar
     * @param ano ano do mês que se deseja verificar
     * @return retorna o valor da soma das receitas no mês requisitada
     */
    public Double somaDasEntradasPorMesEAno(int mes, int ano) {
        return entradaRepository.somaDasEntradasPorMesEAno(mes, ano);
    }

    /**
     * cadastra ou altera uma nova receita
     * @param entrada receita que se deseja cadastrar/alterar
     * @param acao ação a ser executada .: "cadastrar"; "alterar"
     * @return retorna o status da requisição
     */
    public ResponseEntity<?> cadastrarentrada (Entrada entrada, String acao){
        if(entrada.getData() == null){
            rm.setMensagem("a data é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else if(entrada.getMotivo().equals("")){
            rm.setMensagem("o motivo é obrigatório!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        } else if(entrada.getValor() == 0){
            rm.setMensagem("o valor é obrigatorio!");
            return new ResponseEntity<Resposta>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if(acao.equals("cadastrarentrada")){
                return new ResponseEntity<Entrada>(entradaRepository.save(entrada),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<Entrada>(entradaRepository.save(entrada),HttpStatus.OK);
            }
        }
        
    }

    /**
     * remove uma receita
     * @param codigo codigo da entrada
     * @return retorna status da requisiçãoo
     */
    public ResponseEntity<Resposta> removerentrada(long codigo){
        entradaRepository.deleteById(codigo);

        rm.setMensagem("Gasto removido com sucesso");
        return new ResponseEntity<Resposta>(rm, HttpStatus.OK);
    }
}
