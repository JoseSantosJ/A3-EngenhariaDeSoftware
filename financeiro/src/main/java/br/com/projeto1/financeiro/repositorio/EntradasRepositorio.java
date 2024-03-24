package br.com.projeto1.financeiro.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.modelo.EntradasModelo;

@Repository
public interface EntradasRepositorio extends CrudRepository<EntradasModelo, Long>{
  /*   @Query("SELECT SUM(e.valor) FROM EntradasModelo e")
    Double somadasentradas(); */
}
