package br.com.projeto1.financeiro.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.modelo.FontesModelo;

@Repository
public interface FontesRepositorio extends CrudRepository<FontesModelo, Long>{
    
}
