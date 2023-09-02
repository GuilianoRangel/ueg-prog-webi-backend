package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.IEntidade;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TBL_TIPO",
        uniqueConstraints = {
                @UniqueConstraint(name= Tipo.UK_TIPO_NOME, columnNames = "nome" )
        }
)
public class Tipo extends BaseEntidade<Long> {
    public static final String UK_TIPO_NOME = "uk_tipo_nome";
    @SequenceGenerator(
            name="a_gerador_sequence",
            sequenceName = "tipo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status_tipo", length = 1)
    private StatusAtivoInativo status;
}
