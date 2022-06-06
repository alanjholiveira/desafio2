package tech.dock.desafio.api.application.rest.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.application.rest.v1.mapper.PessoaMapper;
import tech.dock.desafio.api.application.rest.v1.response.PessoaResponse;
import tech.dock.desafio.api.domain.service.PessoaService;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaRest {

    private final PessoaService service;


    @GetMapping()
    public Flux<PessoaResponse> findAll() {
        log.info("Listando todos dados cadastrado na base de dados");
        return service.findAll()
                .map(PessoaMapper::toResponse);
    }

    @GetMapping("/{idPessoa}")
    public Mono<PessoaResponse> findByIdPessoa(@PathVariable("idPessoa") BigInteger idPessoa) {
        log.info("Iniciando busca por pessoa através do idPessoa");
        return service.findByIdPessoa(idPessoa)
                .map(PessoaMapper::toResponse);
    }


}
