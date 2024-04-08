package br.com.projeto1.financeiro.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.EntradasModelo;

@Repository
public interface EntradasRepositorio extends CrudRepository<EntradasModelo, Long>{

    Iterable<EntradasModelo> findByOrderByData();

    @Query("SELECT SUM(e.valor) FROM EntradasModelo e")
    Double somadasentradas(); 

    @Query("SELECT e FROM EntradasModelo e WHERE MONTH(e.data) = :mes AND YEAR(e.data) = :ano")
    Iterable<EntradasModelo> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(e.valor) FROM EntradasModelo e WHERE MONTH(e.data) = :mes AND YEAR(e.data) = :ano")
    Double somaDasEntradasPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);
}
