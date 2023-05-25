package br.ueg.prog.webi.faculdade;

import br.ueg.prog.webi.faculdade.model.Tipo;
import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
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

    public AppStartupRunner(TipoRepository tipoRepository){
        System.out.println("Executado");

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
}
