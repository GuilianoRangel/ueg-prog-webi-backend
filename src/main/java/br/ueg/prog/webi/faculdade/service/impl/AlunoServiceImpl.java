package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.faculdade.exception.SistemaMessageCode;
import br.ueg.prog.webi.faculdade.model.Aluno;
import br.ueg.prog.webi.faculdade.repository.AlunoRepository;
import br.ueg.prog.webi.faculdade.service.AlunoService;
import br.ueg.prog.webi.faculdade.utils.ValidacoesComum;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Aluno incluir(Aluno aluno) {
        this.validarCamposObrigatorios(aluno);
        this.validarDados(aluno);
        this.prepararParaIncluir(aluno);
        Aluno alunoIncluido = this.gravarDados(aluno);
        return alunoIncluido;
    }

    private void prepararParaIncluir(Aluno aluno) {
        aluno.setStatusMatricula("Aguardando Aprovação");
    }

    private Aluno gravarDados(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    private void validarDados(Aluno aluno) {
        List<String> erros = new ArrayList<>();

        if(!ValidacoesComum.isEmailValido(aluno.getEMail())){
            erros.add("E-mail inválido");
        }

        String validacaoEmail = validarEmailExistente(aluno);
        if(Strings.isNotEmpty(validacaoEmail)){
            erros.add(validacaoEmail);
        }

        if(!erros.isEmpty()){
            throw  new IllegalArgumentException("Erro ao Validar dados do Aluno: "+
                    String.join(",", erros)
                    );
        }
    }

    private String validarEmailExistente(Aluno aluno) {
        Optional<Aluno> alunoDoEmail = alunoRepository.findAlunoByeMail(aluno.getEMail());
        String retorno = "";
        if(alunoDoEmail.isPresent()){
            String primeiroNome = alunoDoEmail.get().getPrimeiroNome();
             retorno =  "E-mail já utilizado no sistema, e-mail:"+ aluno.getEMail()+
                    " do Usuário: "+primeiroNome;
        }
        return retorno;
    }

    private void validarCamposObrigatorios(Aluno aluno) {
        if(Objects.isNull(aluno)){
            throw  new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
        List<String> camposVazios = new ArrayList<>();
        if(Strings.isEmpty(aluno.getPrimeiroNome())){
            camposVazios.add("Primeiro Nome");
        }
        if(Strings.isEmpty(aluno.getSegundoNome())){
            camposVazios.add("Segundo Nome");
        }
        if(!camposVazios.isEmpty()) {
            throw  new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS/*
                    "Campos Obrigatórios não preenchidos ("+
                            String.join(",",camposVazios)+")"*/
            );
        }
    }

    @Override
    public Aluno alterar(Aluno aluno, Long matricula) {
        this.validarCamposObrigatorios(aluno);
        this.validarDados(aluno);

        Aluno alunoBD = recuperarAlunoOuGeraErro(matricula);

        aluno.setStatusMatricula(alunoBD.getStatusMatricula());
        aluno.setId(matricula);

        Aluno save = alunoRepository.save(aluno);

        return save;
    }

    private Aluno recuperarAlunoOuGeraErro(Long matricula) {
        Aluno alunoBD = alunoRepository
                .findById(matricula)
                .orElseThrow(
                        () -> new BusinessException(SistemaMessageCode.ERRO_REGISTRO_NAO_ENCONTRADO)
                );
        return alunoBD;
    }

    @Override
    public Aluno excluir(Long id) {
        Aluno alunoExcluir = this.recuperarAlunoOuGeraErro(id);
        this.alunoRepository.delete(alunoExcluir);
        return alunoExcluir;
    }

    @Override
    public Aluno obterPeloId(Long id) {
        return this.recuperarAlunoOuGeraErro(id);
    }

    @Override
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @Override
    public List<Aluno> localizar(Aluno aluno) {
        return this.alunoRepository.localizarPorAluno(aluno);
    }

    @Override
    public Aluno cancelarMatricula(Long id) {
        Aluno aluno = this.recuperarAlunoOuGeraErro(id);
        aluno.setStatusMatricula("Cancelada");
        Aluno save = this.alunoRepository.save(aluno);
        return save;
    }
}
