package br.ueg.prog.webi.faculdade.dto;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

public @Data class TipoDTO {
    private Long id;
    private String nome;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataCriacao;
    private StatusAtivoInativo status;
}
