package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.IEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkFuncionario;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;

@Table(name = "TBL_FUNCIONARIO")
@Entity(name = "Funcionario")
@ToString()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class Funcionario extends BaseEntidade<Long>  implements Persistable<Long> {

    @Id
    private Long cpf;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "cpf",
            referencedColumnName = "cpf",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_funcionario_pessoa")
    )
    private Pessoa pessoa;

    @Column(name = "alocacao", nullable = false, length = 40)
    private String alocacao;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Transient
    private boolean isNew = false;
    public void setNew(){
        this.isNew = true;
    }
    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
