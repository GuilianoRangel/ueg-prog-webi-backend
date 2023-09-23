package br.ueg.prog.webi.faculdade.dto;


import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class CargoDTO{

    private Long codigo;

    private String nome;
}
