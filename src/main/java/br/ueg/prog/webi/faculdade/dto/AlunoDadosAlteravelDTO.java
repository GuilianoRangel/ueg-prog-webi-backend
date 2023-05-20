package br.ueg.prog.webi.faculdade.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public @Data class AlunoDadosAlteravelDTO {
    private String primeiroNome;
    private String segundoNome;
    @SuppressWarnings("RegExpRedundantEscape")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "E-mail inválido!!!")
    private String eMail;
    @Max(value = 100, message = "Idade máxima é 100")
    @Min(value = 0,message = "Idade mínima é 0")
    private Integer idade;
}
