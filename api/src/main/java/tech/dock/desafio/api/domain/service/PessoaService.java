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

    /**
     * Método publico responsável por buscar todas pessoas cadastrada na base de dados
     * @return Flux<Pessoa>
     */
    public Flux<Pessoa> findAll() {
        return repository.findAll(DEFAULT_SORT);
    }

    /**
     * Método publico responsável em buscar uma pessoa pelo idPessoa
     * @param idPessoa BigInteger
     * @return Mono<Pessoa>
     */
    public Mono<Pessoa> findByIdPessoa(BigInteger idPessoa) {
        return repository.findById(idPessoa);
    }

}
