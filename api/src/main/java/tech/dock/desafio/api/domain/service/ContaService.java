package tech.dock.desafio.api.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;
import tech.dock.desafio.api.infrastructure.exception.*;
import tech.dock.desafio.api.infrastructure.repository.ContaRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository repository;
    private final TransacaoService transacaoService;
    private final PessoaService pessoaService;

    /**
     * Método publico responsável por criar uma nova conta
     * @param conta Conta
     * @return Mono<Conta>
     */
    public Mono<Conta> criarConta(Conta conta) {
        log.info("Criando uma conta");
        return persistirBase(conta)
                .doOnSuccess(c -> log.info("Conta {} criada com sucesso", c.getIdConta()))
                .doOnError(this::getOnError);
    }

    /**
     * Método publico responsável por habilitar uma conta
     * @param idConta BigInteger
     * @param flagAtivo Boolean
     * @return Mono<Void>
     */
    public Mono<Void> alterarFlag(BigInteger idConta, Boolean flagAtivo) {
        return buscarContaByIdConta(idConta)
                .filter(c -> c.getFlagAtivo().equals(Boolean.FALSE))
                .flatMap(c -> {
                    c.setFlagAtivo(flagAtivo);
                    return persistirBase(c);
                })
                .then();
    }

    /**
     * Método publico responsável por consultar saldo e uma conta
     * @param idConta BigInteger
     * @return Mono<Conta>
     */
    public Mono<Conta> consultarSaldo(BigInteger idConta) {
        log.info("Consultado Saldo da conta: {}", idConta);
        return buscarContaByIdConta(idConta)
                .doOnSuccess(c -> log.info("O saldo atual da conta: {} e de: {}", idConta, c.getSaldo()))
                .doOnError(ErroConsultarException::new);
    }

    /**
     * Método publico responsável por realizar deposito de valores na conta
     * @param idConta BigInteger
     * @param valorDeposito BigDecimal
     * @return Mono<Conta>
     */
    public Mono<Conta> depositoConta(BigInteger idConta, BigDecimal valorDeposito) {
        log.info("Realizando deposito na conta: {}", idConta);
        return buscarContaByIdConta(idConta)
                .flatMap(this::validarContaAtiva)
                .flatMap(c -> realizarDepositoConta(c, valorDeposito))
                .flatMap(c -> transacaoService.realizarTransacao(TipoTransacao.DEPOSITO, c, valorDeposito))
                .doOnSuccess(c -> log.info("Deposito do valor: {} realizado na conta: {} com sucesso.",
                        valorDeposito, idConta))
                .doOnError(this::getOnError);
    }

    /**
     * Método publico responsável por realizar transação de debido em uma conta
     * @param idConta BigInteger
     * @param valorSaque BigDecimal
     * @return Mono<Conta>
     */
    public Mono<Conta> saqueConta(BigInteger idConta, BigDecimal valorSaque) {
        log.info("Realizando Saque da conta: {}", idConta);
        return buscarContaByIdConta(idConta)
                .flatMap(this::validarContaAtiva)
                .flatMap(c -> validarSaldo(c, valorSaque))
                .flatMap(c -> realizarDebitoConta(c, valorSaque))
                .flatMap(c -> transacaoService.realizarTransacao(TipoTransacao.SAQUE, c, valorSaque))
                .doOnSuccess(c -> log.info("Saque da conta: {} realizado com sucesso.", c.getIdConta()))
                .doOnError(this::getOnError);
    }

    /**
     * Realiza deposito de valores na conta e persiste na base de dado.
     * @param conta Conta
     * @param valorDeposito BigDecimal
     * @return Mono<Conta>
     */
    private Mono<Conta> realizarDepositoConta(Conta conta, BigDecimal valorDeposito) {
        return Mono.just(conta)
                .flatMap(c -> {
                    c.setSaldo(c.getSaldo().add(valorDeposito));
                    return persistirBase(c);
                })
                .doOnSuccess(c -> log.info("Realizado deposito do valor: {} na conta {} com sucesso.",
                        valorDeposito, c.getIdConta()));
    }

    /**
     * Realiza o debito de valores na conta e persiste na base de dados
     * @param conta Conta
     * @param valorDebito BigDecimal
     * @return Mono<Conta>
     */
    private Mono<Conta> realizarDebitoConta(Conta conta, BigDecimal valorDebito) {
        return Mono.just(conta)
                .flatMap(c -> {
                    c.setSaldo(c.getSaldo().subtract(valorDebito));
                    return persistirBase(c);
                })
                .doOnSuccess(c -> log.info("Valor: {} debitado da conta: {} com sucesso",
                        valorDebito, c.getIdConta()));
    }

    /**
     * Responsavel por fazer a buscar da conta usando numero da conta
     * @param idConta BigInteger
     * @return Mono<Conta>
     * @throws ContaNaoEncontradaException Exception
     */

    private Mono<Conta> buscarContaByIdConta(BigInteger idConta) {
        return repository.findById(idConta)
                .flatMap(this::loadRelations)
                .switchIfEmpty(Mono.error(new ContaNaoEncontradaException()))
                .doOnSuccess(c -> log.info("Conta: {} encontrada com sucesso", idConta));
    }

    /**
     * Realiza a validação do saldo para saque
     * @param conta Conta
     * @param valor Valor
     * @return Mono<Conta>
     */
    private Mono<Conta> validarSaldo(Conta conta, BigDecimal valor) {
        return Mono.just(conta)
                .filter(c -> isLower(valor, c.getSaldo()))
                .switchIfEmpty(Mono.error(new SaldoInsuficienteException()))
                .thenReturn(conta);
    }

    /**
     * Realiza a validação ser a conta está ativa
     * @param conta Conta
     * @return Mono<Conta>
     * @throws ContaInativaException Exception
     */
    private Mono<Conta> validarContaAtiva(Conta conta) {
        return Mono.just(conta).filter(Conta::getFlagAtivo)
                .switchIfEmpty(Mono.error(new ContaInativaException()))
                .thenReturn(conta);
    }

    /**
     * Retorna erro em caso de falha na tarnsação
     * @param error Throwable
     * @throws ErroTransacaoException Exception
     */
    private void getOnError(Throwable error) {
        log.error("[ERROR] Ocorreu erro na tentativa de realizar uma transação, erro: {}", error.getMessage());
        throw new ErroTransacaoException(error);
    }

    /**
     * Realiza calculo para verificar ser o valor e inferior
     * @param value BigDecimal
     * @param max BigDecimal
     * @return boolean
     */
    private boolean isLower(BigDecimal value, BigDecimal max) {
        return value.compareTo(max) <= 0;
    }

    private Mono<Conta> persistirBase(Conta conta) {
        log.info("Iniciando processo de persistência na base de dados.");
        return repository.save(conta)
                .doOnSuccess(c -> log.info("Conta salva com sucesso na base de dado"))
                .doOnError(this::getOnError);
    }

    private Mono<Conta> loadRelations(final Conta conta) {
        log.info("Buscando relacionamento com tabela pessoa");
        return Mono.just(conta)
                .filter(c -> Objects.isNull(c.getPessoa()))
                .switchIfEmpty(Mono.just(conta))
                .zipWith(pessoaService.findByIdPessoa(conta.getIdPessoa()))
                .map(result -> result.getT1().setPessoa(result.getT2()));
    }


}
