package br.ueg.prog.webi.adminmodule.model;


import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TBL_GRUPO_USUARIO")
@EqualsAndHashCode
@SequenceGenerator(name = "TBL_S_GRUPO_USUARIO", sequenceName = "TBL_S_GRUPO_USUARIO", allocationSize = 1)
public @Data
class Grupo implements Serializable {
    private static final long serialVersionUID = -8899975090870463525L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_GRUPO_USUARIO")
    @Column(name = "id_grupo", nullable = false)
    @Max(99999999L)
    private Long id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status", nullable = false, length = 1)
    private StatusAtivoInativo status;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GrupoFuncionalidade> grupoFuncionalidades;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    private Set<UsuarioGrupo> usuarioGrupos;
}
