package tech.dock.desafio.api.application.rest.v1.mapper;

import lombok.AllArgsConstructor;
import tech.dock.desafio.api.application.rest.v1.request.PessoaRequest;
import tech.dock.desafio.api.application.rest.v1.response.PessoaResponse;
import tech.dock.desafio.api.domain.entity.Pessoa;

@AllArgsConstructor
public class PessoaMapper extends MapperGeneric {

    public static PessoaResponse toResponse(Pessoa entity) {
        return mapper.map(entity, PessoaResponse.class);
    }

    public static Pessoa toEntity(PessoaRequest request) {
        return mapper.map(request, Pessoa.class);
    }
}
