package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.faculdade.model.pks.PkChave;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "TBL_CHAVE")
@Entity
@IdClass(PkChave.class)
public @Data class Chave extends BaseEntidade<PkChave> {

    @Id
    @ManyToOne
    @JoinColumn(name = "local_numero_sala", referencedColumnName = "numero_sala", nullable = false, foreignKey = @ForeignKey(name = "fk_chave_local"))
    private Local local;

    @Id
    @Column(name = "numero", nullable = false)
    private Long numero;

    @Column(name = "qr_code", length = 500)
    private String qrCode;
}
