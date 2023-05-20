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
@Table(name = "TBL_LIVRO"
)
public class Livro implements IEntidade<Long> {
    @SequenceGenerator(
            name = "a_gerador_sequence",
            sequenceName = "livror_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_LIVRO", referencedColumnName = "ID", nullable = false)
    @ToString.Exclude
    private Tipo tipo;

    @Convert(converter = StatusAtivoInativoConverter.class)
    @Column(name = "status_matricula", length = 1)
    private StatusAtivoInativo status;

    @Override
    public String getTabelaNome() {
        return "Livro";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Livro livro = (Livro) o;
        return id != null && Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
