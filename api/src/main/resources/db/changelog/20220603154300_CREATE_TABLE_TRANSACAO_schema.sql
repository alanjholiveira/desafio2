-- Drop table

-- DROP TABLE tb_transacao;

CREATE SEQUENCE tb_transacao_id_transacao_seq;

CREATE TABLE tb_transacao (
    "id_transacao" bigint NOT NULL DEFAULT nextval('tb_transacao_id_transacao_seq'),
    "id_conta" bigint NOT NULL,
    "tipo_transacao" int4 NOT NULL,
    valor numeric NOT NULL,
    "data_transacao" date NOT NULL DEFAULT CURRENT_DATE,
    "hora_transacao" time NOT NULL DEFAULT CURRENT_TIME(2),
    CONSTRAINT tb_transacao_pk PRIMARY KEY ("id_transacao"),
    CONSTRAINT tb_transacao_tb_conta_id_conta_fk FOREIGN KEY ("id_conta") REFERENCES tb_conta("id_conta")
);
CREATE UNIQUE INDEX tb_transacao_id_transacao_uindex ON tb_transacao USING btree ("id_transacao");
ALTER SEQUENCE tb_transacao_id_transacao_seq OWNED BY tb_transacao.id_transacao;