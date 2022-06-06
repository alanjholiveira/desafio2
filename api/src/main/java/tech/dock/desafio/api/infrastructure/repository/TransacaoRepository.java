package tech.dock.desafio.api.infrastructure.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.domain.entity.Transacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Repository
public interface TransacaoRepository extends ReactiveSortingRepository<Transacao, BigInteger> {

    /**
     * Query Method buscar transações pelo id Conta
     * @param idConta BigInteger
     * @return Flux<Transacao>
     */
    Flux<Transacao> findByIdConta(BigInteger idConta);

    /**
     * Query retorna transações de uma conta por periodo.
     * @param idConta BigInteger
     * @param dataInicial LocalDate
     * @param dataFinal LocalDate
     * @return Flux<Transacao>
     */
    @Query("SELECT * FROM tb_transacao t "
            + "WHERE t.id_conta = :idConta "
            + "AND t.data_transacao BETWEEN :dataInicial AND :dataFinal ")
    Flux<Transacao> getTransacoesPorPeriodo(BigInteger idConta, LocalDate dataInicial, LocalDate dataFinal);

    /**
     * Query para retornar o valor total das transações de uma determinada conta em um dia especifico.
     *
     * @param idConta BigInteger
     * @param dataAtual LocalDate
     * @return Mono<BigDecimal>
     */
    @Query("SELECT SUM(t.valor) FROM tb_transacao t " +
            "WHERE t.id_conta = :idConta " +
            "AND t.tipo_transacao = :tipoTransacao " +
            "AND t.data_transacao = :dataAtual " )
    Mono<BigDecimal> getValorTransacoesPorDia(BigInteger idConta, Integer tipoTransacao,
                                              LocalDate dataAtual);

}
