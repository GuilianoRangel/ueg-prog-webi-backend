package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TBL_AMIGO")
public class Amigo  extends BaseEntidade<Long> {
    @SequenceGenerator(
            name="a_gerador_sequence",
            sequenceName = "amigo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_amigo", length = 200, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "tipo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_amigo_tipo"))
    private Tipo tipo;
}
