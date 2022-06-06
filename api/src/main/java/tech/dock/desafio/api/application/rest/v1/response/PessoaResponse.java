package tech.dock.desafio.api.application.rest.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponse {

    private BigInteger idPessoa;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

}
