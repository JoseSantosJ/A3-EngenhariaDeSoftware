package br.com.financeiro.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.financeiro.model.Entrada;

@Repository
public interface EntradaRepository extends CrudRepository<Entrada, Long>{

    Iterable<Entrada> findByOrderByData();

    @Query("SELECT SUM(e.valor) FROM Entrada e")
    Double somadasentradas(); 

    @Query("SELECT e FROM Entrada e WHERE MONTH(e.data) = :mes AND YEAR(e.data) = :ano")
    Iterable<Entrada> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(e.valor) FROM Entrada e WHERE MONTH(e.data) = :mes AND YEAR(e.data) = :ano")
    Double somaDasEntradasPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);
}
