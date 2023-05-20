package br.ueg.prog.webi.faculdade.model;

import br.ueg.prog.webi.api.model.IEntidade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TBL_ALUNO",
    uniqueConstraints = {
            @UniqueConstraint(name= Aluno.UK_AMIGO_MAIL, columnNames = "mail" )
    }
)
public class Aluno implements IEntidade<Long> {
    public static final String UK_AMIGO_MAIL = "uk_amigo_mail";
    @SequenceGenerator(
            name="a_gerador_sequence",
            sequenceName = "amigo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "a_gerador_sequence"
    )
    @Id
    @Column(name = "codigo")
    private Long id;

    @Column(name = "nome", length = 200, nullable = false)
    private String primeiroNome;

    @Column(name = "sobrenome", length = 200, nullable = false)
    private String segundoNome;

    @Column(name = "mail", length = 300)
    private String eMail;
    @Column(name = "idade")
    private Integer idade;

    @Column(name = "status_matricula")
    private String statusMatricula;

    @Override
    public String getTabelaNome() {
        return "Aluno";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Aluno aluno = (Aluno) o;
        return id != null && Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
