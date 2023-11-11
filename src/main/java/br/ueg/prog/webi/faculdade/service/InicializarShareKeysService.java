package br.ueg.prog.webi.faculdade.service;

import br.ueg.prog.webi.faculdade.dto.LocalDTO;
import br.ueg.prog.webi.faculdade.mapper.LocalMapper;
import br.ueg.prog.webi.faculdade.model.*;
import br.ueg.prog.webi.faculdade.model.pks.PkPessoaPermissao;
import br.ueg.prog.webi.faculdade.model.pks.PkResponsabilidade;
import br.ueg.prog.webi.faculdade.repository.*;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class InicializarShareKeysService {
    private static final Logger LOG =
            LoggerFactory.getLogger(InicializarShareKeysService.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private DiscenteRepository discenteRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private ResponsabilidadeRepository responsabilidadeRepository;

    @Autowired
    private PessoaPermissaoRepository pessoaPermissaoRepository;

    @Autowired
    private ChaveRepository chaveRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ResponsabilidadeService responsabilidadeService;

    @Autowired
    private LocalMapper localMapper;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private CargoRepository cargoRepository;

    public void inicializar(){
        LOG.info("initiateKeyShareInstance samples");
        Cargo c = Cargo.builder()
                .nome("Professor")
                .build();
        c = cargoRepository.saveAndFlush(c);

        Pessoa p = Pessoa.builder()
                .cpf(123L)
                .nome("João da Silva")
                .telefone("11-1111-1111")
                .build();


        Funcionario f = new Funcionario();
        f.setPessoa(p);
        f.setCargo(c);
        f.setAlocacao("Alocação");
        f.setNew();
        f = funcionarioRepository.saveAndFlush(f);
        System.out.println(f);

        Optional<Funcionario> f2 = funcionarioRepository.findByCPF(123L);
        if (f2.isPresent()) {
            System.out.println(f2.get().toString());
        }

        Discente d = new Discente();
        d.setPessoa(Pessoa.builder()
                .cpf(124L)
                .nome("Tereza da silva")
                .telefone("22-2222-22222")
                .build());
        d.setAnoIngresso("2023");
        d.setCurso("SI");
        d.setNew();
        d = discenteRepository.saveAndFlush(d);
        System.out.println(d);

        Optional<Discente> d2 = discenteRepository.findByCPF(123L);
        if (d2.isPresent()) {
            System.out.println("Discente:");
            System.out.println(d2.get().toString());
        }

        Local l = new Local();
        l.setNome("Laboratório IV");
        l.setDescricao("Laboratório IV - Informática");
        l.setNumeroSala(10L);

        l = localRepository.saveAndFlush(l);
        System.out.println(l);

        Chave ch = new Chave();
        ch.setLocal(l);
        ch.setNumero(100L);
        ch.setQrCode("L10C100");

        /*ch = chaveRepository.save(ch);
        System.out.println(ch);
        System.out.println(ch.getId());

        Optional<Chave> ch2 = chaveRepository.findById(ch.getId());
        System.out.println(ch2.orElseGet(() -> new Chave()));*/

        l.getChaves().add(ch);
        l = localRepository.saveAndFlush(l);

        Responsabilidade r = new Responsabilidade();
        r.setLocal(l);
        r.setFuncionario(f);
        r.setDataInicio(LocalDate.now(ZoneId.systemDefault()));

        r = responsabilidadeRepository.saveAndFlush(r);
        PkResponsabilidade id = r.getId();
        System.out.println(id);
        Optional<Responsabilidade> r2 = responsabilidadeRepository.findById(id);
        if (r2.isPresent()) {
            System.out.println(r2.get());
        }

        PessoaPermissao pp = new PessoaPermissao();
        pp.setResponsabilidade(r2.get());
        pp.setPessoa(p);
        pp.setDataInicio(LocalDate.now(ZoneId.systemDefault()));
        pp.setDataFim(pp.getDataInicio().plusDays(180));
        pp = pessoaPermissaoRepository.saveAndFlush(pp);
        System.out.println(pp);
        PkPessoaPermissao ppid = pp.getId();

        Optional<PessoaPermissao> pp2 = pessoaPermissaoRepository.findById(ppid);
        if (pp2.isPresent()) {
            System.out.println(pp2.get());
            System.out.println(pp2.get().getId());
            String idHash = pp2.get().getIdHash();
            System.out.println(idHash);
            System.out.println(pp2.get().getIdFromHash(idHash));

            Emprestimo emp = new Emprestimo();
            emp.setChave(ch);
            emp.setPessoaPermissao(pp2.get());
            emp.setDataHoraRetirada(LocalDateTime.now(ZoneId.systemDefault()));

            emp = emprestimoRepository.saveAndFlush(emp);
            System.out.println(emp);
        }

        /* teste service Resposabilidade */
        Local loc = new Local();
        loc.setNumeroSala(10L);

        Funcionario func = new Funcionario();
        func.setCpf(123L);

        Responsabilidade resp = new Responsabilidade();
        resp.setLocal(loc);
        resp.setFuncionario(func);
        resp.setDataInicio(LocalDate.now(ZoneId.systemDefault()));

        Responsabilidade respInc = responsabilidadeService.incluir(resp);
        System.out.println(respInc);
        String pkResp = respInc.getIdHash();
        Long seq = respInc.getSequencia();
        LocalDate inicio = respInc.getDataInicio();

        resp = new Responsabilidade();
        resp.setLocal(
                Local.builder()
                        .numeroSala(10L)
                        .build());
        resp.setFuncionario(
                Funcionario.builder()
                        .cpf(123L)
                        .build()
        );
        resp.setSequencia(seq);
        resp.setDataInicio(inicio);
        resp.setDataFim(LocalDate.now().plusDays(90));
        respInc = responsabilidadeService.alterar(resp, respInc.getIdFromHash(pkResp));
        System.out.println(respInc);

        resp = new Responsabilidade();
        resp.setLocal(
                Local.builder()
                        .numeroSala(10L)
                        .build());
        resp.setFuncionario(
                Funcionario.builder()
                        .cpf(123L)
                        .build()
        );
        resp.setDataInicio(LocalDate.of(2023, 9, 1));
        resp.setDataFim(LocalDate.of(2023, 9, 25));
        respInc = responsabilidadeService.incluir(resp);
        System.out.println(respInc);

        // teste do Local salvando com chave.
        //l = localRepository.getReferenceById(10l);
        Optional<Local> byId = localRepository.findById(10L);
        l = byId.orElseGet(() -> Local.builder().build());
        System.out.println(l);
        l.getChaves().add(Chave.builder().local(l).numero(20L).qrCode("20L").build());
        l.getChaves().add(Chave.builder().local(l).numero(30L).qrCode("30L").build());
        l = localRepository.saveAndFlush(l);
        System.out.println(l);

        byId = localRepository.findById(10L);
        System.out.println(byId.orElseGet(() -> Local.builder().build()));

        LocalDTO localDTO = localMapper.toDTO(l);
        System.out.println(localDTO);

        Local lTeste = localMapper.toModelo(localDTO);
        System.out.println(lTeste);

        Funcionario f4 = new Funcionario();
        f4.setCpf(125L);
        f4.getPessoa().setNome("Funcionário 125");
        f4.getPessoa().setTelefone("444");
        f4.setAlocacao("Alocação");
        f4.setCargo(Cargo.builder().codigo(1L).build());
        //f3.setPessoa(Pessoa.builder().cpf(125L).build());
        f4 = funcionarioService.incluir(f4);
        System.out.println(f4);

        f4.setAlocacao("OutraTeste");
        //f3.setPessoa(Pessoa.builder().cpf(125L).build());
        f4 = funcionarioService.incluir(f4);
        System.out.println(f4);

        List<Funcionario> teste = funcionarioRepository.findAll(userRoleId("da"));
        System.out.println(teste);


    }

    public static Specification<Funcionario> userRoleId(String name) {
        return (root, query, builder) -> {
            Subquery<Pessoa> subquery = query.subquery(Pessoa.class);
            Root<Pessoa> subqueryRoot = subquery.from(Pessoa.class);
            subquery.select(subqueryRoot);
            Predicate userIdPredicate = builder.equal(subqueryRoot.get(Pessoa.Fields.cpf), root.<String> get(Funcionario.Fields.cpf));

            Predicate roleIdList = builder.like(subqueryRoot.get(Pessoa.Fields.nome), "%" + name + "%");
            subquery.select(subqueryRoot).where(userIdPredicate, roleIdList);
            return builder.exists(subquery);
        };
    }
}