-- Drop table

-- DROP TABLE tb_conta;

CREATE SEQUENCE tb_conta_id_conta_seq;

CREATE TABLE tb_conta (
    "id_conta" bigint NOT NULL DEFAULT nextval('tb_conta_id_conta_seq'),
    "id_pessoa" int8 NOT NULL,
    saldo numeric NOT NULL DEFAULT 0,
    "limite_saque_diario" numeric NOT NULL DEFAULT 0,
    "flag_ativo" bool NOT NULL DEFAULT false,
    "tipo_conta" int4 NOT NULL,
    "data_criacao" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "last_modified_date" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tb_conta_pk PRIMARY KEY ("id_conta"),
    CONSTRAINT tb_conta_tb_pessoa_id_pessoa_fk FOREIGN KEY ("id_pessoa") REFERENCES tb_pessoa("id_pessoa")
);
CREATE UNIQUE INDEX tb_conta_id_conta_uindex ON tb_conta USING btree ("id_conta");
ALTER SEQUENCE tb_conta_id_conta_seq OWNED BY tb_conta.id_conta;