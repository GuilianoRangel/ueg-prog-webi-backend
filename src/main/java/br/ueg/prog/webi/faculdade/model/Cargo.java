package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.IEntidade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TBL_CARGO",
        uniqueConstraints = {
                @UniqueConstraint(name= Cargo.UK_CARGO_NOME, columnNames = "nome" )
        }
)
public class Cargo extends BaseEntidade<Long> {
    public static final String UK_CARGO_NOME = "uk_cargo_nome";
    @SequenceGenerator(
            name="a_gerador_sequence",
            sequenceName = "cargo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
}
