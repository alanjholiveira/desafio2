package tech.dock.desafio.api.builder.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.builder.ConstrutorDeEntidade;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.infrastructure.enums.TipoConta;
import tech.dock.desafio.api.infrastructure.repository.ContaRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;

@Component
public class ContaBuilder extends ConstrutorDeEntidade<Conta, BigInteger> {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private PessoaBuilder pessoaBuilder;

    @Override
    public Conta construirEntidade() throws ParseException {
        setCustomizacao(null);
        return Conta.builder()
                .pessoa(pessoaBuilder.construirEntidade())
                .idConta(BigInteger.TWO)
                .idPessoa(BigInteger.ONE)
                .flagAtivo(Boolean.TRUE)
                .tipoConta(TipoConta.CONTA_CORRENTE.getIdTipoConta())
                .saldo(BigDecimal.valueOf(100))
                .limiteSaqueDiario(BigDecimal.valueOf(1000))
                .dataCriacao(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<Conta> persistir(Conta entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Flux<Conta> obterTodos() {
        return repository.findAll();
    }

    @Override
    protected Mono<Conta> obterPorId(BigInteger id) {
        return repository.findById(id);
    }

}
