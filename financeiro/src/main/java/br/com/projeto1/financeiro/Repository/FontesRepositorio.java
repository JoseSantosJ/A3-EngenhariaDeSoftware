package br.com.projeto1.financeiro.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.FontesModelo;

@Repository
public interface FontesRepositorio extends CrudRepository<FontesModelo, Long>{
    
}
