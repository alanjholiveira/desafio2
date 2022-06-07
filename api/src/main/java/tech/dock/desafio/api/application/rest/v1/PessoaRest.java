package tech.dock.desafio.api.application.rest.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
@Api(tags = "Pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/v1/pessoas")
@RequiredArgsConstructor
public class PessoaRest {

    private final PessoaService service;

    @ApiOperation(value = "Uma lista contendo todas as pessoa cadastrada", response = PessoaResponse.class,
            responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping()
    public Flux<PessoaResponse> findAll() {
        log.info("Listando todos dados cadastrado na base de dados");
        return service.findAll()
                .map(PessoaMapper::toResponse);
    }

    @ApiOperation(value = "Busca uma pessoa pelo IDPessoa", response = PessoaResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{idPessoa}")
    public Mono<PessoaResponse> findByIdPessoa(@PathVariable("idPessoa") BigInteger idPessoa) {
        log.info("Iniciando busca por pessoa através do idPessoa");
        return service.findByIdPessoa(idPessoa)
                .map(PessoaMapper::toResponse);
    }


}
