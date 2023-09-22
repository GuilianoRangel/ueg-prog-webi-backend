package br.ueg.prog.webi.faculdade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class FuncionarioDTO {

    private Long cpf;

    private String nome;

    private String telefone;

    private String email;

    private Long cargo_codigo;

    private String cargo_nome;

    private String alocacao;
}
