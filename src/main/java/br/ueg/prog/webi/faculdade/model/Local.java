package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import lombok.Data;

import jakarta.persistence.*;

@Table(name = "TBL_LOCAL")
@Entity
public @Data
class Local extends BaseEntidade<Long> {
    @Id
    @SequenceGenerator(
            name = "local_sequence",
            sequenceName = "seq_local",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "local_sequence"
    )
    @Column(name = "numero_sala")
    private Long numeroSala;

    @Column(name = "nome", nullable = false,	length = 20)
    private String nome;

    @Column(name = "descricao",	length = 50)
    private String descricao;

}
