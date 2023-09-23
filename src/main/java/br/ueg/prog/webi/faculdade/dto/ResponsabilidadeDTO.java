package br.ueg.prog.webi.faculdade.dto;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class ResponsabilidadeDTO{

    private Long sequencia;

    private FuncionarioDTO funcionario;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dataFim;

    private Boolean ativo;
}


