package br.com.projeto1.financeiro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.GastoFonte;

@Repository
public interface GastoFonteRepository extends CrudRepository<GastoFonte, Long>{
    
}
