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

    public double totalMensal(){
        return gastosRepositorio.somaDosGastos();
    }
    
}
