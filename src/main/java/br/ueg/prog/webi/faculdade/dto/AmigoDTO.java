package br.ueg.prog.webi.faculdade.dto;

import br.ueg.prog.webi.faculdade.model.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class  AmigoDTO {
    private Long id;

    private String nome;

    private Long tipo_id;

    private String tipo_nome;
}
