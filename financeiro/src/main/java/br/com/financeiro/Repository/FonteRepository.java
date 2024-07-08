package br.com.financeiro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.financeiro.model.Fonte;

@Repository
public interface FonteRepository extends CrudRepository<Fonte, Long>{
    
}
