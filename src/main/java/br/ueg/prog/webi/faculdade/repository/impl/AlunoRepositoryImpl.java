package br.ueg.prog.webi.faculdade.repository.impl;

import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.repository.AlunoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlunoRepositoryImpl implements AlunoRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Aluno> localizarPorAluno(Aluno aluno) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT aluno FROM Aluno aluno");

        jpql.append(" WHERE 1=1 ");

        if (Strings.isNotEmpty(aluno.getPrimeiroNome())) {
            jpql.append(" AND UPPER(aluno.primeiroNome) LIKE UPPER('%' || :primeiroNome || '%')  ");
            parametros.put("primeiroNome", aluno.getPrimeiroNome());
        }
        if (Strings.isNotEmpty(aluno.getSegundoNome())) {
            jpql.append(" AND UPPER(aluno.segundoNome) LIKE UPPER('%' || :segundoNome || '%')  ");
            parametros.put("segundoNome", aluno.getSegundoNome());
        }
        if(Objects.nonNull(aluno.getIdade())){
            jpql.append(" AND aluno.idade = :idade");
            parametros.put("idade", aluno.getIdade());
        }

        TypedQuery<Aluno> query = entityManager.createQuery(jpql.toString(), Aluno.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
