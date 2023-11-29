package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import br.ueg.prog.webi.api.interfaces.ISearchFieldData;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TBL_TIPO",
        uniqueConstraints = {
                @UniqueConstraint(name= Tipo.UK_TIPO_NOME, columnNames = "nome_tipo" )
        }
)
@FieldNameConstants
public class Tipo extends BaseEntidade<Long> implements ISearchFieldData<Long> {
    public static final String UK_TIPO_NOME = "uk_tipo_nome";
    @SequenceGenerator(
            name="a_gerador_sequence",
            sequenceName = "tipo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "id")
    @Searchable(label = "Código")
    private Long id;

    @Column(name = "nome_tipo", length = 200, nullable = false)
    @Searchable(autoComplete = true)
    private String nome;

    @Column(name = "data_criacao")
    @Searchable(label = "Dta. Criação")
    private LocalDate dataCriacao;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status_tipo", length = 1)
    @Searchable()
    private StatusAtivoInativo status;

    public String getDescription(){
        return this.nome + " - " + this.status;
    }
}
