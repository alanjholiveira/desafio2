package tech.dock.desafio.api.application.rest.v1.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.dock.desafio.api.application.rest.v1.request.ContaRequest;
import tech.dock.desafio.api.application.rest.v1.response.ContaResponse;
import tech.dock.desafio.api.application.rest.v1.response.ContaSaldoResponse;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.infrastructure.enums.TipoConta;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContaMapper {


    /**
     * Realiza conversão da request para entidade
     * @param request ContaRequest
     * @return Conta
     */
    public static Conta toEntity(ContaRequest request) {
        return Conta.builder()
                .idPessoa(request.getIdPessoa())
                .pessoa(Pessoa.builder().idPessoa(request.getIdPessoa()).build())
                .limiteSaqueDiario(request.getLimiteSaqueDiario())
                .flagAtivo(Boolean.FALSE)
                .tipoConta(request.getTipoConta().getIdTipoConta())
                .saldo(BigDecimal.ZERO)
                .build();
    }

    /**
     * Realiza a conversão de entidade para um response
     * @param entity Conta
     * @return ContaResponse
     */
    public static ContaResponse toResponse(Conta entity) {
        return ContaResponse.builder()
                .idConta(entity.getIdConta())
                .nomePessoa(entity.getPessoa().getNome())
                .limiteSaqueDiario(entity.getLimiteSaqueDiario())
                .tipoConta(TipoConta.of(entity.getTipoConta()))
                .build();
    }

    /**
     * Realiza conversão da entidade para toSaldoResponse
     * @param entity Conta
     * @return ContaSaldoResponse
     */
    public static ContaSaldoResponse toSaldoResponse(Conta entity) {
        return ContaSaldoResponse.builder()
                .idConta(entity.getIdConta())
                .saldoAtual(entity.getSaldo())
                .build();
    }

}
