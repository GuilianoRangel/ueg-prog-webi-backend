package br.ueg.prog.webi.faculdade.model.pks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
public @Data class PkDiscente implements Serializable {
    protected Long cpf;
}
