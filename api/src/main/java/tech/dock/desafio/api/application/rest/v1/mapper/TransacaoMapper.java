package tech.dock.desafio.api.application.rest.v1.mapper;

import lombok.AllArgsConstructor;
import tech.dock.desafio.api.application.rest.v1.response.TransacaoResponse;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public final class TransacaoMapper {


    public static TransacaoResponse toResponse(Transacao transacao) {
        return TransacaoResponse.builder()
                .idConta(transacao.getIdConta())
                .valorTransacao(transacao.getValor())
                .tipoTransacao(TipoTransacao.of(transacao.getTipoTransacao()))
                .dataTransacao(transacao.getDataTransacao())
                .horaTrasacao(transacao.getHoraTransacao())
                .build();
    }

    public static Transacao toTransacao(TipoTransacao tipoTransacao, Conta conta, BigDecimal valorTransacao) {
        return Transacao.builder()
                .conta(conta)
                .idConta(conta.getIdConta())
                .valor(valorTransacao)
                .dataTransacao(LocalDate.now())
                .horaTransacao(LocalTime.now())
                .tipoTransacao(tipoTransacao.getIdTransacao())
                .build();
    }

}
