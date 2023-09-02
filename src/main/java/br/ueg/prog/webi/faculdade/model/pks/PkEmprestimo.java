package br.ueg.prog.webi.faculdade.model.pks;

import br.ueg.prog.webi.api.model.annotation.PkComposite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@PkComposite
public @Data class PkEmprestimo implements Serializable {
    protected PkChave chave;
    protected PkPessoaPermissao pessoaPermissao;
}
