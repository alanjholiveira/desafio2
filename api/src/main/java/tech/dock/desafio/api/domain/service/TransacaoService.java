package tech.dock.desafio.api.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.application.rest.v1.mapper.TransacaoMapper;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.domain.utils.Utils;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;
import tech.dock.desafio.api.infrastructure.exception.ContaNaoEncontradaException;
import tech.dock.desafio.api.infrastructure.exception.LimiteDiarioException;
import tech.dock.desafio.api.infrastructure.repository.ContaRepository;
import tech.dock.desafio.api.infrastructure.repository.PessoaRepository;
import tech.dock.desafio.api.infrastructure.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;
    private final ContaRepository contaRepository;
    private final PessoaRepository pessoaRepository;

    /**
     * Método responsável por realizar transação de valores na conta.
     * @param tipoTransacao TipoTransacao
     * @param conta Conta
     * @param valorTransacao BigDecimal
     * @return Mono<Conta>
     */
    public Mono<Conta> realizarTransacao(TipoTransacao tipoTransacao, Conta conta,
                                         BigDecimal valorTransacao) {
        log.info("Iniciando transação do tipo: {} da conta: {}, no valor: {}",
                tipoTransacao.getDescricao(), conta.getIdConta(), valorTransacao);

        return validarUsoLimiteDiarioSaque(conta, tipoTransacao)
                .map(validation -> TransacaoMapper.toTransacao(tipoTransacao, conta, valorTransacao))
                .flatMap(repository::save)
                .doOnNext(t -> log.info("Transação realizada com sucesso"))
                .thenReturn(conta)
                .doOnSuccess(t -> log.info("Transação finalizada"))
                .doOnError(Utils::getOnError);
    }

    /**
     * Método responsável por mostrar extrato total de uma conta
     * @param idConta BigInteger
     * @return Flux<Transacao>
     */
    public Flux<Transacao> extratoTransacaoConta(BigInteger idConta) {
        return buscarTransacaoByIdConta(idConta);
    }

    public Flux<Transacao> extratoTransacaoPorPeriodo(BigInteger idConta, LocalDate dataInicial, LocalDate dataFinal) {
        return repository.getTransacoesPorPeriodo(idConta, dataInicial, dataFinal);
    }

    /**
     * Realiza validação ser o cliente já usou limite diario para saque
     * @param conta Conta
     * @param tipoTransacao TipoTransacao
     * @return Mono<Boolean>
     */
    private Mono<Boolean> validarUsoLimiteDiarioSaque(Conta conta, TipoTransacao tipoTransacao) {
        log.info("Verificando ser cliente possui limite diario para saque");

        if (TipoTransacao.DEPOSITO.equals(tipoTransacao)) {
            return Mono.just(Boolean.TRUE);
        }
        return Mono.just(conta)
                .flatMap(c -> repository.getValorTransacoesPorDia(c.getIdConta(), TipoTransacao.SAQUE.getIdTransacao(),
                        LocalDate.now()))
                .filter(totalSaque -> Utils.isLower(totalSaque, conta.getLimiteSaqueDiario()))
                .switchIfEmpty(Mono.error(new LimiteDiarioException()))
                .flatMap(a -> Mono.just(Boolean.TRUE));
    }

    /**
     * Responsavel por fazer a buscar da conta usando numero da conta
     * @param idConta BigInteger
     * @return Mono<Conta>
     * @throws ContaNaoEncontradaException Exception
     */
    private Flux<Transacao> buscarTransacaoByIdConta(BigInteger idConta) {
        log.info("Buscando transacação da conta: {}", idConta);
        return repository.findByIdConta(idConta)
                .switchIfEmpty(Flux.error(new ContaNaoEncontradaException()))
                .flatMap(this::loadRelations)
                .doOnComplete(() -> log.info("Busca por transações da Conta: {} encontrada com sucesso", idConta));
    }

    private Mono<Transacao> loadRelations(final Transacao transacao) {
        log.info("Buscando relacionamento com tabela conta");
        return Mono.just(transacao)
                .filter(c -> Objects.isNull(c.getConta()))
                .switchIfEmpty(Mono.just(transacao))
                .zipWith(contaRepository.findById(transacao.getIdConta()))
                .map(result -> result.getT1().setConta(result.getT2()));
    }

}
