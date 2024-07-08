package br.com.financeiro.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.financeiro.model.FonteMes;
import br.com.financeiro.model.Gasto;



@Repository
public interface FonteMesRepository extends CrudRepository<FonteMes,String>{
    Iterable<FonteMes> findByOrderById();

    Optional<FonteMes> findById(String id);

    @Query("SELECT g FROM Gasto g WHERE g.fonte = :fonte AND MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    Iterable<Gasto> GastosMesEFonte(@Param("fonte") long fonte, @Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE g.fontemes.id = :fontemesId AND g.tipo != 'f'")
    Double somaDosGastosMesEFonte(@Param("fontemesId") String fonteMesId);
}
