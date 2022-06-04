package tech.dock.desafio.api.infrastructure.enums;


import java.util.Arrays;

public enum TipoConta {

    CONTA_CORRENTE(1, "Conta Corrente"),
    CONTA_POUPANCA(2, "Conta Poupança"),
    CONTA_SALARIO(3, "Conta Salario");

    private Integer idTipoConta;
    private String descricao;

    TipoConta(int idTipoConta, String descricao) {
        this.idTipoConta = idTipoConta;
        this.descricao = descricao;
    }

    public Integer getIdTipoConta() {
        return idTipoConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConta of(Integer idTipoConta) {
        return Arrays.stream(values())
                .filter(t -> t.getIdTipoConta().equals(idTipoConta))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum tipo de conta encontrada"));
    }
}
