package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Table(name = "TBL_CHAVE")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PkChave.class)
@ToString
public @Data class Chave extends BaseEntidade<PkChave> {

    @Id
    @ManyToOne
    @JoinColumn(name = "local_numero_sala", referencedColumnName = "numero_sala", nullable = false, foreignKey = @ForeignKey(name = "fk_chave_local"))
    @ToString.Exclude
    private Local local;

    @Id
    @Column(name = "numero", nullable = false)
    private Long numero;

    @Column(name = "qr_code", length = 500)
    private String qrCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Chave chave = (Chave) o;
        return local != null && local.equals(chave.local)
                && numero != null && Objects.equals(numero, chave.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
