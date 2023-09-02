package br.ueg.prog.webi.adminmodule.model;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "TBL_FUNCIONALIDADE_MODULO")
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_FUNC_MODULO", sequenceName = "TBL_S_FUNC_MODULO", allocationSize = 1)
public @Data
class Funcionalidade implements Serializable {
    private static final long serialVersionUID = 4381258342853410159L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_FUNC_MODULO")
    @Column(name = "id_funcionalidade_modulo", nullable = false)
    private Long id;

    @Column(name = "nome_funcionalidade_modulo", length = 200, nullable = false)
    private String nome;

    @Column(name = "mnemonico_funcionalidade", length = 40, nullable = false)
    private String mnemonico;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo_sistema", referencedColumnName = "id_modulo_sistema", nullable = false,
    foreignKey = @ForeignKey(name = "FK_FUNCIONALIDADE_MODULO"))
    private ModuloSistema moduloSistema;
}
