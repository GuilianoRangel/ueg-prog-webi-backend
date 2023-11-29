package br.ueg.prog.webi.faculdade;

import br.ueg.prog.webi.adminmodule.service.InicializarService;
import br.ueg.prog.webi.faculdade.model.*;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.faculdade.repository.*;
import br.ueg.prog.webi.faculdade.service.InicializarShareKeysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AppStartupRunner implements ApplicationRunner {

    public static final String NONE="none";
    public static final String CREATE_DROP="create-drop";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private AmigoRepository amigoRepository;


    @Autowired
    private InicializarService inicializarService;

    @Autowired
    private InicializarShareKeysService inicializarShareKeysService;

    public void initDados(){

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

        t1 = new Tipo();
        t1.setNome("Amigo");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.ATIVO);
        tipoRepository.save(t1);

        t1 = new Tipo();
        t1.setNome("Pilantra");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.ATIVO);
        tipoRepository.save(t1);

        t1 = new Tipo();
        t1.setNome("Amissíssimo");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.ATIVO);
        tipoRepository.save(t1);

        t1 = new Tipo();
        t1.setNome("Conhecido");
        t1.setDataCriacao(LocalDate.now());
        t1.setStatus(StatusAtivoInativo.ATIVO);
        t1 = tipoRepository.save(t1);

        Amigo a2 ;
        for(int x=0; x < 10; x++){
            a2 = new Amigo();
            a2.setTipo(t1);
            a2.setNome("Amigo "+x);
            amigoRepository.save(a2);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            this.initDados();
            this.inicializarService.inicializar();
            this.inicializarShareKeysService.inicializar();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
