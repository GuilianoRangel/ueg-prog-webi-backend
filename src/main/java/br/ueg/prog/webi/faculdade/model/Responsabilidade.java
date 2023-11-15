package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.interfaces.ISearchFieldData;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "TBL_RESPONSABILIDADE")
@Entity
@IdClass(PkResponsabilidade.class)
public @Data class Responsabilidade extends BaseEntidade<PkResponsabilidade> implements ISearchFieldData<PkResponsabilidade> {

    @Id
    @SequenceGenerator(
            name = "responsabilidade_sequence",
            sequenceName = "seq_responsabilidade",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "responsabilidade_sequence"
    )
    @Column(name = "sequencia", nullable = false, updatable = false)
    private Long sequencia;

    @Id
    @ManyToOne
    @JoinColumn(name = "local_numero_sala", referencedColumnName = "numero_sala", nullable = false, foreignKey = @ForeignKey(name = "fk_responsabilidade_local"))
    @ToString.Exclude
    private Local local;

    @ManyToOne
    @JoinColumn(name = "funcionario_pessoa_cpf", referencedColumnName = "cpf", nullable = false, foreignKey = @ForeignKey(name = "fk_responsabilidade_funcionario"))
    @Searchable(label = "Funcionário", listEntityValues = true)
    private Funcionario funcionario;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Transient
    public Boolean getAtivo(){
        if(Objects.isNull(this.dataFim)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public String getDescription() {
        return this.getFuncionario().getPessoa().getNome();
    }
}


