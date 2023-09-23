package br.ueg.prog.webi.faculdade.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class DiscenteDTO {

    private Long cpf;

    private String nome;

    private String telefone;

    private String email;

    private String anoIngresso;

    private String curso;
}
