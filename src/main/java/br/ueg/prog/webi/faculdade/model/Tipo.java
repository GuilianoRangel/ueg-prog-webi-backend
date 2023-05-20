package br.ueg.prog.webi.faculdade.model;


import br.ueg.prog.webi.api.model.IEntidade;
import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.faculdade.model.enums.converter.StatusAtivoInativoConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TBL_TIPO",
        uniqueConstraints = {
                @UniqueConstraint(name= Tipo.UK_TIPO_NOME, columnNames = "nome" )
        }
)
public class Tipo implements IEntidade<Long> {
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
    private Long id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status_tipo", length = 1)
    private StatusAtivoInativo status;

    @Override
    public String getTabelaNome() {
        return "Tipo";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tipo tipo = (Tipo) o;
        return id != null && Objects.equals(id, tipo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
