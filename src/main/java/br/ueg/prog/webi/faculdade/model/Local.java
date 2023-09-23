package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "TBL_LOCAL")
@Entity
@Builder
@AllArgsConstructor
@ToString
public @Getter
@Setter
@RequiredArgsConstructor
class Local extends BaseEntidade<Long> {
    @Id
    @Column(name = "numero_sala")
    private Long numeroSala;

    @Column(name = "nome", nullable = false,	length = 20)
    private String nome;

    @Column(name = "descricao",	length = 100)
    private String descricao;

    //TODO mostrar mapeamento bidirecional
    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Chave> chaves = new HashSet<>();

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Responsabilidade> responsaveis = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return  Objects.equals(getNumeroSala(), local.getNumeroSala()) &&
                Objects.equals(getNome(), local.getNome()) &&
                Objects.equals(getDescricao(), local.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroSala(), getNome(), getDescricao());
    }
}
