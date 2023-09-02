package br.ueg.prog.webi.adminmodule.exception;

import br.ueg.prog.webi.api.exception.MessageCode;

public enum ModuleAdminMessageCode  implements MessageCode {
    ERRO_FILTRO_NAO_INFORMADO("MODULEADMIN-MSG-001",400),
    ERRO_NENHUM_REGISTRO_ENCONTRADO("MODULEADMIN-MSG-002", 404),
    ERRO_GRUPO_DUPLICADO("MODULEADMIN-MSG-003", 400 ),
    ERRO_LOGIN_DUPLICADO("MODULEADMIN-MSG-004", 400 ),
    ERRO_SENHAS_DIFERENTES("MODULEADMIN-MSG-005", 400 ),
    ERRO_SENHA_ANTERIOR_INCORRETA("MODULEADMIN-MSG-006", 400),
    ERRO_USUARIO_NAO_ENCONTRADO("MODULEADMIN-MSG-007", 400 ),
    ERRO_LOGIN_INVALIDO("MODULEADMIN-MSG-008", 400 ),
    ERRO_LOGIN_EM_USO("MODULEADMIN-MSG-009", 400);

    private final String code;

    private final Integer status;

    /**
     * Construtor da classe.
     *
     * @param code   -
     * @param status -
     */
    ModuleAdminMessageCode(final String code, final Integer status) {
        this.code = code;
        this.status = status;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @see Enum#toString()
     */
    @Override
    public String toString() {
        return code;
    }
}
