package tech.dock.desafio.api.builder.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.builder.ConstrutorDeEntidade;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.infrastructure.repository.PessoaRepository;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PessoaBuilder extends ConstrutorDeEntidade<Pessoa, BigInteger> {

    @Autowired
    private PessoaRepository repository;

    @Override
    public Pessoa construirEntidade() throws ParseException {
        setCustomizacao(null);
        return Pessoa.builder()
                .idPessoa(BigInteger.ONE)
                .nome("Nome Pessoa")
                .cpf("58382140076")
                .dataNascimento(LocalDate.of(1989, 05, 14))
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    @Override
    public Mono<Pessoa> persistir(Pessoa entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Flux<Pessoa> obterTodos() {
        return repository.findAll();
    }

    @Override
    protected Mono<Pessoa> obterPorId(BigInteger id) {
        return repository.findById(id);
    }

}
