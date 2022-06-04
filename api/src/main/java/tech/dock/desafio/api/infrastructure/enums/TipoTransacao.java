package tech.dock.desafio.api.infrastructure.enums;

import java.util.Arrays;

public enum TipoTransacao {

    DEPOSITO	(1, "Deposito"),
    SAQUE 		(2, "Saque");

    private final Integer idTipoTransacao;
    private final String descricao;


    TipoTransacao(Integer idTipoTransacao, String descricao) {
        this.idTipoTransacao = idTipoTransacao;
        this.descricao = descricao;
    }

    public Integer getIdTransacao() {
        return idTipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoTransacao of(Integer idTipoTransacao) {
        return Arrays.stream(values())
                .filter(t -> t.getIdTransacao().equals(idTipoTransacao))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum tipo de transação encontrada"));
    }

}
