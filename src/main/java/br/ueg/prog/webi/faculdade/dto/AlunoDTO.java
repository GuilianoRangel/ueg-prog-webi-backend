package br.ueg.prog.webi.faculdade.dto;

import lombok.Data;

public @Data class AlunoDTO {
    private Long id;
    private String primeiroNome;
    private String segundoNome;
    private String eMail;
    private Integer idade;
    private String statusMatricula;
}
