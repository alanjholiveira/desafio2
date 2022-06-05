package tech.dock.desafio.api.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.infrastructure.repository.PessoaRepository;

import java.math.BigInteger;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PessoaService {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    private final PessoaRepository repository;

    public Flux<Pessoa> findAll() {
        return repository.findAll(DEFAULT_SORT);
    }

    public Mono<Pessoa> create(Pessoa entity) {
        return repository.save(entity);
    }

    public Mono<Pessoa> findByIdPessoa(String idPessoa) {
        return repository.findByIdPessoa(idPessoa);
    }

    public Mono<Pessoa> findByIdPessoa(BigInteger idPessoa) {
        return repository.findById(idPessoa);
    }

}
