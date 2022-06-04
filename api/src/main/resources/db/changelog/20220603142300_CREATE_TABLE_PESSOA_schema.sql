-- Drop table

-- DROP TABLE tb_pessoa;

CREATE SEQUENCE tb_pessoa_id_pessoa_seq;

CREATE TABLE tb_pessoa (
    "id_pessoa" bigint NOT NULL DEFAULT nextval('tb_pessoa_id_pessoa_seq'),
    nome varchar NOT NULL,
    cpf varchar NOT NULL DEFAULT 13,
    "data_nascimento" date NOT NULL,
    "created_date" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "last_modified_date" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tb_pessoa_pk PRIMARY KEY ("id_pessoa")
);
CREATE UNIQUE INDEX tb_pessoa_cpf_uindex ON tb_pessoa USING btree (cpf);
CREATE UNIQUE INDEX tb_pessoa_id_pessoa_uindex ON tb_pessoa USING btree ("id_pessoa");
ALTER SEQUENCE tb_pessoa_id_pessoa_seq OWNED BY tb_pessoa.id_pessoa;