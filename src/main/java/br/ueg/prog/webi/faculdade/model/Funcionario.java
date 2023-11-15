package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.interfaces.ISearchFieldData;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

@Table(name = "TBL_FUNCIONARIO", indexes = {@Index(name = "IDX_PK_FUNCIONARIO", columnList = "cpf", unique = true)})
@Entity(name = "Funcionario")
@ToString()
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public @Data class Funcionario extends BaseEntidade<Long>  implements Persistable<Long>, ISearchFieldData<Long> {

    @Id
    @Searchable(label = "CPF")
    private Long cpf;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "cpf",
            referencedColumnName = "cpf",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_funcionario_pessoa")
    )
    @Searchable
    private Pessoa pessoa;

    @Column(name = "alocacao", nullable = false, length = 40)
    private String alocacao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cargo_codigo",
            referencedColumnName = "codigo",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_funcionario_cargo")
    )
    private Cargo cargo;

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
        if(Objects.isNull(this.getPessoa())){
            this.setPessoa(Pessoa.builder().build());
        }
        this.getPessoa().setCpf(cpf);
    }

    @Override
    public String getDescription() {
        return this.getPessoa().getNome();
    }
}
