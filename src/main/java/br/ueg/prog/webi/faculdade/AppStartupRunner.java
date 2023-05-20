package br.ueg.prog.webi.faculdade;

import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.faculdade.repository.AlunoRepository;
import br.ueg.prog.webi.faculdade.repository.TipoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AppStartupRunner {

    public static final String NONE="none";
    public static final String CREATE_DROP="create-drop";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);

    public AppStartupRunner(AlunoRepository alunoRepository, TipoRepository tipoRepository){
        System.out.println("Executado");
        System.out.println(alunoRepository);
        Aluno a1 = new Aluno();
        a1.setPrimeiroNome("João");
        a1.setSegundoNome("Pereira");
        a1.setIdade(30);

        try {
            alunoRepository.save(a1);
        }catch(Exception e) {
            e.printStackTrace();
        }
        a1.setId(1L);
        a1 = alunoRepository.save(a1);
        System.out.println("Aluno:"+a1);
        a1.setEMail("Teste@teste.com.br");
        a1 = alunoRepository.save(a1);
        System.out.println("Aluno: "+a1);

        a1 = new Aluno();
        a1.setId(2L);
        a1.setPrimeiroNome("Outro");
        a1.setSegundoNome("Pereira");
        a1.setEMail("Teste@teste.com.br");
        boolean email_duplicado = false;
        Integer totalUsoEmail = alunoRepository.countUtilizacaoEMail(a1.getEMail());
        if(totalUsoEmail>0){
            System.out.println("O e-mail:"+a1.getEMail()+" não pode ser utilizado!!");
            System.out.println("Total de utilização: "+totalUsoEmail);
        }else{
            alunoRepository.save(a1);
        }

        Optional<Aluno> alunoByEMail = alunoRepository.findAlunoByeMail(a1.getEMail());
        if(alunoByEMail.isPresent()){
            Aluno aluno = alunoByEMail.get();
            System.out.println("Não é possível utilizar o emaili:"+a1.getEMail());
            System.out.println("Porque ele pertence ao aluno:"+aluno.getPrimeiroNome());
        }else{
            a1 = alunoRepository.save(a1);
        }
        try {
            a1 = alunoRepository.save(a1);
        } catch (Exception e){
            System.out.println(e.getMessage());
            email_duplicado = e.getMessage().contains(Aluno.UK_AMIGO_MAIL);
        }
        if(email_duplicado){
            System.out.println("Email duplicado:"+ a1.getEMail());
        }

        System.out.println("Aluno2: "+ a1);
        imprimirLista(alunoRepository);
        alunoRepository.delete(a1);
        imprimirLista(alunoRepository);

        Tipo t1 = new Tipo();
        t1.setNome("Tipo1");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.ATIVO);
        tipoRepository.save(t1);

        t1 = new Tipo();
        t1.setNome("Tipo2");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.INATIVO);
        tipoRepository.save(t1);
    }

    private static void imprimirLista(AlunoRepository alunoRepository) {
        List<Aluno> lista = alunoRepository.findAll();
        lista.forEach(item -> {
            System.out.println("Aluno: "+item);
        });
    }
}
