package tech.dock.desafio.api.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import tech.dock.desafio.api.domain.entity.Pessoa;

import java.math.BigInteger;

@Repository
public interface PessoaRepository extends ReactiveSortingRepository<Pessoa, BigInteger> {

}
