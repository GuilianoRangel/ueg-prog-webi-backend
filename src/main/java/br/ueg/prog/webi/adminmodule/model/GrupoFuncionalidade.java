package br.ueg.prog.webi.adminmodule.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_GRUPO_FUNCIONALIDADE")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_GRUPO_FUNCIONALIDADE", sequenceName = "TBL_S_GRUPO_FUNCIONALIDADE", allocationSize = 1)
public @Data
class GrupoFuncionalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_GRUPO_FUNCIONALIDADE")
    @Column(name = "id_grupo_funcionalidade", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo", nullable = false,
    foreignKey = @ForeignKey(name = "FK_GRUPO_FUNCIONALIDADE_GRUPO"))
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionalidade", referencedColumnName = "id_funcionalidade_modulo", nullable = false,
    foreignKey = @ForeignKey(name = "FK_GRUPO_FUNCIONALIDADE_FUNCIONALIDADE"))
    private Funcionalidade funcionalidade;

}
