package br.com.projeto1.financeiro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.Info;



@Repository
public interface InfoRepository extends CrudRepository <Info,Long>{
    
    Iterable<Info> findByFonteMes(String fonteMes);
    
}
