package tech.dock.desafio.api.infrastructure.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.domain.entity.Conta;

import java.math.BigInteger;

@Repository
public interface ContaRepository extends ReactiveSortingRepository<Conta, BigInteger> {

}
