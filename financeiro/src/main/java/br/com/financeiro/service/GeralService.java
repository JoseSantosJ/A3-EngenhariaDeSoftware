package br.com.financeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.financeiro.Repository.EntradaRepository;
import br.com.financeiro.Repository.GastoRepository;

@Service
public class GeralService {
    @Autowired
    private EntradaRepository entradasrepositorio;
    
    @Autowired 
    private GastoRepository gastosRepositorio;

    /**
     * apresenta o resto entre o valor de todas as receitas cadastradas e as despesas
     * @return retorna o resto entre todos os valores das entradas e todos os valores dos gastos (soma das entradas - soma dos gastos)
     */
    public double total(){
     double total = entradasrepositorio.somadasentradas() - gastosRepositorio.somaDosGastos();
     return total;
    }

    /**
     * apresenta o resto entre o valor de todas as receitas  e as despesas cadastradas no mês desejado
     * @param mes mês que se deseja verificar
     * @param ano ano do mês que se deseja verificar
     * @return retorna o resto entre a soma dos valores das receitas no mes especificado e os gastos do mes especificado
     */
    public double totalMensal(int mes, int ano){
        double entradas;
        double saidas;
        

        if(entradasrepositorio.somaDasEntradasPorMesEAno(mes ,ano) != null && entradasrepositorio.somaDasEntradasPorMesEAno(mes ,ano) > 0){
            entradas = entradasrepositorio.somaDasEntradasPorMesEAno(mes ,ano);
        }else{
            entradas = 0;
        }
        if(gastosRepositorio.somaDosGastosPorMesEAno(mes,ano)  != null && gastosRepositorio.somaDosGastosPorMesEAno(mes,ano) > 0){
            saidas = gastosRepositorio.somaDosGastosPorMesEAno(mes,ano);
        }else{
            saidas = 0;
        }
        double totalMensal = entradas - saidas;
        
        return totalMensal;
    }
    
}
