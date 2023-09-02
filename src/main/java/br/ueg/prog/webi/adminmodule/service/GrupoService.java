package br.ueg.prog.webi.adminmodule.service;

import br.ueg.prog.webi.adminmodule.dto.GrupoEstatisticasDTO;
import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroGrupoDTO;
import br.ueg.prog.webi.adminmodule.exception.ModuleAdminMessageCode;
import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.model.GrupoFuncionalidade;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.repository.GrupoFuncionalidadeRepository;
import br.ueg.prog.webi.adminmodule.repository.GrupoRepository;
import br.ueg.prog.webi.adminmodule.repository.UsuarioGrupoRepository;
import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.util.CollectionUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private GrupoFuncionalidadeRepository grupoFuncionalidadeRepository;

    @Autowired
    private DataSource dataSource;

    /**
     * Retorna uma lista de {@link Grupo} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Grupo> getGruposByFiltro(final FiltroGrupoDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Grupo> grupos = grupoRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(ApiMessageCode.ERRO_REGISTRO_NAO_ENCONTRADO);
        }

        return grupos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroGrupoDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (Strings.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (Objects.isNull(filtroDTO.getAtivo())) {
            vazio = Boolean.FALSE;
        }
        if(filtroDTO.getIdModulo()!=null && filtroDTO.getIdModulo()>0){
            vazio = Boolean.FALSE;

        }

        if (vazio) {
            throw new BusinessException(ModuleAdminMessageCode.ERRO_FILTRO_NAO_INFORMADO);
        }
    }

    /**
	 * Retorna uma lista de {@link Grupo} ativos .
	 *
	 * @return
	 */
	public List<Grupo> getAtivos() {
		List<Grupo> grupos = grupoRepository.getAtivos();

		if (CollectionUtil.isEmpty(grupos)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}
		return grupos;
	}

    /**
     * Retorna uma lista de {@link Grupo} cadatrados .
     *
     * @return
     */
    public List<Grupo> getCadastrados() {
        List<Grupo> grupos = grupoRepository.findAll();

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(ModuleAdminMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return grupos;
    }

    /**
     * Salva a instânica de {@link Grupo} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param grupo
     * @return
     */
    public Grupo salvar(Grupo grupo) {
        validarCamposObrigatorios(grupo);
        validarDuplicidade(grupo);

        tratarGrupoFuncionalidade(grupo);

        Grupo grupoSaved = grupoRepository.save(grupo);
        grupoSaved = grupoRepository.findByIdFetch(grupoSaved.getId());
        return grupoSaved;
    }

     /**
     * Realiza o tratamento do atributo grupo funcionalidade (preenchimento do atributo grupo e
     * tratar os gruposfuncionalidades não alterados
     * @param grupo
     */
    private void tratarGrupoFuncionalidade(Grupo grupo) {
        Set<GrupoFuncionalidade> grupoFuncionalidadesToDB = new HashSet<>();

        for (GrupoFuncionalidade grupoFuncionalidade : grupo.getGrupoFuncionalidades()) {
            if (grupoFuncionalidade.getId() == null) {
                grupoFuncionalidade.setGrupo(grupo);
                grupoFuncionalidadesToDB.add(grupoFuncionalidade);
            } else {
                GrupoFuncionalidade grupoFuncionalidadeBD =
                        grupoFuncionalidadeRepository.findByIdFetch(grupoFuncionalidade.getId());
                grupoFuncionalidadesToDB.add(grupoFuncionalidadeBD);
            }
        }
        grupo.setGrupoFuncionalidades(grupoFuncionalidadesToDB);
    }

    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param grupo
     */
    private void validarCamposObrigatorios(final Grupo grupo) {
        boolean vazio = Boolean.FALSE;

        if (Strings.isEmpty(grupo.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (Strings.isEmpty(grupo.getDescricao())) {
            vazio = Boolean.TRUE;
        }

        if (Strings.isEmpty(grupo.getStatus().toString())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(ApiMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Grupo a ser salvo já existe na base de dados.
     *
     * @param grupo
     */
    private void validarDuplicidade(final Grupo grupo) {
        Long count = grupoRepository.countByNomeAndGrupoAndNotId(grupo.getNome(),
                grupo.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(ModuleAdminMessageCode.ERRO_GRUPO_DUPLICADO);
        }
    }

    /**
     * Retorna os roles do sistema conforme o 'idUsuario' e o 'mnemonico'
     * informados.
     *
     * @param idUsuario
     * @return
     */
    public List<String> getRolesByUsuario(final Long idUsuario) {
        return grupoRepository.findRolesByUsuario(idUsuario);
    }

    /**
     * Inativa o {@link Grupo} pelo 'id'.
     *
     * @param id
     * @return
     */
    public Grupo inativar(final Long id) {
        Grupo grupo = getGrupoByIdFetch(id);
        grupo.setStatus(StatusAtivoInativo.INATIVO);
        return grupoRepository.save(grupo);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id'.
     *
     * @param id
     * @return
     */
    public Grupo ativar(final Long id) {
        Grupo grupo = getGrupoByIdFetch(id);
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        return grupoRepository.save(grupo);
    }

    /**
     * Retorna a instância de {@link Grupo} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Grupo getGrupoByIdFetch(final Long id) {
        return grupoRepository.findByIdFetch(id);
    }

    /**
     * Relatório de estatisticas de usuários por grupo
     * @return
     */
    public List<GrupoEstatisticasDTO> getGrupoEstatisticas(){
        return usuarioGrupoRepository.getEstatisticaGrupo();
    }

    public JasperPrint gerarRelatorio(){
        try {
         Connection connection = dataSource.getConnection();
            Map<String, Object> params = new HashMap<>();
            JasperDesign jd =
                    JRXmlLoader.load(
                            this.getClass()
                                    .getResourceAsStream("/module-admin/relatorios/total_usuarios_por_grupo.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, params, connection);
            return jasperPrint;
            //TODO Não foi feito o tratamento correto ainda
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
