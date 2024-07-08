package br.com.financeiro.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.financeiro.model.FonteMes;
import br.com.financeiro.model.Gasto;
import jakarta.transaction.Transactional;
import java.util.List;


@Repository
public interface GastoRepository extends CrudRepository<Gasto, Long>{



    Iterable<Gasto>  findByOrderByData(); 

    Iterable<Gasto>  findByFonte(long fonte);

    @Query("SELECT g FROM Gasto g WHERE g.tipo != 'f' AND g.fontemes.id = :fontemesId  ORDER BY g.data")
    Iterable<Gasto> listarGastosFonte(@Param("fontemesId") String fonteMesId);

    

    @Query("SELECT g FROM Gasto g WHERE g.tipo = 'f' AND g.fontemes.id = :fontemesId  ORDER BY g.data")
    Gasto findByFontemesId(@Param("fontemesId") String fonteMesId);

    @Query("SELECT g FROM Gasto g WHERE g.fonte = 0 ORDER BY g.data")
    Iterable<Gasto> gastoSemFonte();

    @Transactional
    void deleteByInfo(long info);

    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE (g.tipo) != 'f' ")
    Double somaDosGastos();

    @Query("SELECT g FROM Gasto g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano AND (g.tipo) != 'c'")
    Iterable<Gasto> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano AND (g.tipo) != 'f'")
    Double somaDosGastosPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);

}
