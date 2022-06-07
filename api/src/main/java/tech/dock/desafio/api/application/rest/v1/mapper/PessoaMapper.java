package tech.dock.desafio.api.application.rest.v1.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.dock.desafio.api.application.rest.v1.response.PessoaResponse;
import tech.dock.desafio.api.domain.entity.Pessoa;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PessoaMapper extends MapperGeneric {

    /**
     * Realiza conversão da entidade Pessoa para Pessoa Response
     * @param entity Pessoa
     * @return PessoaResponse
     */
    public static PessoaResponse toResponse(Pessoa entity) {
        return mapper.map(entity, PessoaResponse.class);
    }

}
