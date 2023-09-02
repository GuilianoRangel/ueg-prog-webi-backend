package br.ueg.prog.webi.adminmodule.model;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "TBL_MODULO_SISTEMA")
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_MODULO_SISTEMA", sequenceName = "TBL_S_MODULO_SISTEMA", allocationSize = 1)
public @Data
class ModuloSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_MODULO_SISTEMA")
    @Column(name = "id_modulo_sistema", nullable = false)
    private Long id;

    @Column(name = "nome_modulo", length = 200, nullable = false)
    private String nome;

    @Column(name = "mnemonico_modulo", length = 40, nullable = false)
    private String mnemonico;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "moduloSistema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Funcionalidade> funcionalidades;

}
