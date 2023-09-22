package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TBL_PESSOA")
public @Data class Pessoa extends BaseEntidade<Long> implements Persistable<Long> {
    @Id
    @Column(name = "cpf", updatable = false, nullable = false)
    private Long cpf;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "email", length = 255)
    private String email;

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
