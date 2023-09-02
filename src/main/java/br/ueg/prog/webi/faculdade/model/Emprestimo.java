package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import br.ueg.prog.webi.faculdade.model.pks.PkEmprestimo;
import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "TBL_EMPESTIMO")
@Entity
@IdClass(PkEmprestimo.class)
@Check(constraints = "chave_local_numero_sala=pessoa_permissao_responsabilidade_local_numero_sala")
public @Data class Emprestimo  extends BaseEntidade<PkEmprestimo> {

    @Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(
                    name="chave_local_numero_sala",//"pessoa_permissao_responsabilidade_local_numero_sala"
                    referencedColumnName = "local_numero_sala"
            ),
            @JoinColumn(
                    name="chave_numero",
                    referencedColumnName = "numero"
            )
    },foreignKey = @ForeignKey(name = "fk_emprestimo_chave"))
    private Chave chave;

    @Id
    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(
                    name = "pessoa_permissao_responsabilidade_sequencia",
                    referencedColumnName = "responsabilidade_sequencia"
            ),
            @JoinColumn(
                    name = "pessoa_permissao_responsabilidade_local_numero_sala",
                    referencedColumnName = "responsabilidade_local_numero_sala"
            ),
            @JoinColumn(
                    name = "pessoa_permissao_sequencia",
                    referencedColumnName = "sequencia"
            )

    }, foreignKey = @ForeignKey(name = "fk_emprestimo_pessoa_permissao"))
    private PessoaPermissao pessoaPermissao;

    @Column(name = "data_hora_retirada", nullable = false)
    private LocalDateTime dataHoraRetirada;

    @Column(name = "data_hora_devolucao")
    private LocalDateTime dataHoraDevolucao;
}
