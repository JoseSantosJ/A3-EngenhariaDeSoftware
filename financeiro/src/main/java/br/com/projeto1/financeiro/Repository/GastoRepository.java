package br.com.projeto1.financeiro.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projeto1.financeiro.model.FonteMes;
import br.com.projeto1.financeiro.model.Gasto;
import jakarta.transaction.Transactional;
import java.util.List;


@Repository
public interface GastoRepository extends CrudRepository<Gasto, Long>{



    Iterable<Gasto>  findByOrderByData(); 

    Iterable<Gasto>  findByFonte(long fonte);

    Iterable<Gasto> findByFontemes(FonteMes fontemes);

    @Query("SELECT g FROM Gasto g WHERE g.fonte = 0")
    Iterable<Gasto> gastoSemFonte();

    @Transactional
    void deleteByInfo(long info);

    @Query("SELECT SUM(g.valor) FROM Gasto g")
    Double somaDosGastos();

    @Query("SELECT g FROM Gasto g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    Iterable<Gasto> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    Double somaDosGastosPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);

}
