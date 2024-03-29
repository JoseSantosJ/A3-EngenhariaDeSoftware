package br.com.projeto1.financeiro.repositorio;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.modelo.GastosModelo;
import jakarta.transaction.Transactional;

@Repository
public interface GastosRepositorio extends CrudRepository<GastosModelo, Long>{



    Iterable<GastosModelo>  findByOrderByData(); 

    @Transactional
    void deleteByInfo(long info);

    @Query("SELECT SUM(g.valor) FROM GastosModelo g")
    Double somaDosGastos();

    @Query("SELECT g FROM GastosModelo g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    Iterable<GastosModelo> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(g.valor) FROM GastosModelo g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    Double somaDosGastosPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);

}
