package br.ueg.prog.webi.faculdade.dto;


import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.Local;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class ChaveDTO{

    private Long numero;

    @Size( max = 500)
    private String qrCode;
}
