package br.com.projeto1.financeiro.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.InfoModelo;

@Repository
public interface InfoRepositorio extends CrudRepository <InfoModelo,Long>{
    
    
}
