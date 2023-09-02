package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkPessoaPermissao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "TBL_PESSOA_PERMISSAO")
@Entity
@IdClass(PkPessoaPermissao.class)
public @Data class PessoaPermissao  extends BaseEntidade<PkPessoaPermissao> {

    @Id
    @SequenceGenerator(
            name = "pessoa_permissao_sequence",
            sequenceName = "seq_pessoa_permissao",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pessoa_permissao_sequence"
    )
    @Column(name="sequencia", nullable = false, updatable = false)
    private Long sequencia;

    @Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(
                    name = "responsabilidade_sequencia",
                    referencedColumnName = "sequencia"
                    ),
            @JoinColumn(
                    name = "responsabilidade_local_numero_sala",
                    referencedColumnName = "local_numero_sala"
                    )

    }, foreignKey = @ForeignKey(name="fk_pessoa_permissao_responsabilidae")
    )
    private Responsabilidade responsabilidade;

    @ManyToOne
    @JoinColumn(name = "pessoa_cpf", referencedColumnName = "cpf", foreignKey = @ForeignKey(name = "fk_responsabilidade_pessoa"))
    private Pessoa pessoa;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;
}
