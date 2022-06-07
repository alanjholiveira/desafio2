package tech.dock.desafio.api.builder.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.builder.ConstrutorDeEntidade;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;
import tech.dock.desafio.api.infrastructure.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TransacaoBuilder extends ConstrutorDeEntidade<Transacao, BigInteger> {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private ContaBuilder contaBuilder;

    @Override
    public Transacao construirEntidade() throws ParseException {
        setCustomizacao(null);
        return Transacao.builder()
                .conta(contaBuilder.construirEntidade())
                .idConta(BigInteger.TWO)
                .idTransacao(BigInteger.TWO)
                .tipoTransacao(TipoTransacao.SAQUE.getIdTransacao())
                .horaTransacao(LocalTime.now())
                .dataTransacao(LocalDate.now())
                .valor(BigDecimal.valueOf(100))
                .build();
    }

    @Override
    public Mono<Transacao> persistir(Transacao entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Flux<Transacao> obterTodos() {
        return repository.findAll();
    }

    @Override
    protected Mono<Transacao> obterPorId(BigInteger id) {
        return repository.findById(id);
    }

}
