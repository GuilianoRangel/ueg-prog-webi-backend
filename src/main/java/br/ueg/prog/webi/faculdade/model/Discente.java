package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import br.ueg.prog.webi.faculdade.model.pks.PkDiscente;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

@Table(name = "TBL_DISCENTE")
@Entity(name = "Discente")
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(PkDiscente.class)
public @Data class Discente extends BaseEntidade<Long> {

    @Id
    private long cpf;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "cpf",
            referencedColumnName = "cpf",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_dicente_pessoa")
    )
    private Pessoa pessoa;

    @Column(name = "ano_ingresso", nullable = false, length = 6)
    private String anoIngresso;

    @Column(name = "curso", nullable = false, length = 50)
    private String curso;
}
