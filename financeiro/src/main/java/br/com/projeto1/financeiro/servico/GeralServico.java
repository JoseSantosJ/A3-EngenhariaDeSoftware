package br.com.projeto1.financeiro.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto1.financeiro.repositorio.EntradasRepositorio;
import br.com.projeto1.financeiro.repositorio.GastosRepositorio;

@Service
public class GeralServico {
    @Autowired
    private EntradasRepositorio entradasrepositorio;
    
    @Autowired 
    private GastosRepositorio gastosRepositorio;


    public double total(){
     double total = entradasrepositorio.somadasentradas() - gastosRepositorio.somaDosGastos();
     return total;
    }

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

        totalMensal = Math.round(totalMensal * 100.0) / 100.0;

        return totalMensal;
    }
    
}
