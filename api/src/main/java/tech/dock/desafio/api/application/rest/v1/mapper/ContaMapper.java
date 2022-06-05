package tech.dock.desafio.api.application.rest.v1.mapper;

import lombok.AllArgsConstructor;
import tech.dock.desafio.api.application.rest.v1.request.ContaRequest;
import tech.dock.desafio.api.application.rest.v1.response.ContaResponse;
import tech.dock.desafio.api.application.rest.v1.response.ContaSaldoResponse;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.infrastructure.enums.TipoConta;

import java.math.BigDecimal;

@AllArgsConstructor
public class ContaMapper extends MapperGeneric {


    public static Conta toEntity(ContaRequest request) {
        return Conta.builder()
                .idPessoa(request.getIdPessoa())
                .pessoa(Pessoa.builder().idPessoa(request.getIdPessoa()).build())
                .limiteSaqueDiario(request.getLimiteSaqueDiario())
                .flagAtivo(Boolean.FALSE)
                .tipoConta(request.getTipoConta().getIdTipoConta())
                .saldo(BigDecimal.ZERO)
                .build();
//        return mapper.typeMap(ContaRequest.class, Conta.class).addMappings(map -> {
//            map.map(ContaRequest::getIdPessoa, Conta::setPessoa);
//            map.map(ContaRequest::getLimiteSaqueDiario, Conta::setLimiteSaqueDiario);
//        });
    }

    public static ContaResponse toResponse(Conta entity) {
        return ContaResponse.builder()
                .idConta(entity.getIdConta())
                .nomePessoa(entity.getPessoa().getNome())
                .limiteSaqueDiario(entity.getLimiteSaqueDiario())
                .tipoConta(TipoConta.of(entity.getTipoConta()))
                .build();
    }

    public static ContaSaldoResponse toSaldoResponse(Conta entity) {
        return ContaSaldoResponse.builder()
                .idConta(entity.getIdConta())
                .saldoAtual(entity.getSaldo())
                .build();
    }

}
