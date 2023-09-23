package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.DiscenteDTO;
import br.ueg.prog.webi.faculdade.dto.FuncionarioDTO;
import br.ueg.prog.webi.faculdade.model.Discente;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscenteMapper
        extends BaseMapper<Discente, DiscenteDTO> {

    @Override
    @Mapping(source = "pessoa.nome",        target = "nome")
    @Mapping(source = "pessoa.telefone",    target = "telefone")
    @Mapping(source = "pessoa.email",       target = "email")
    DiscenteDTO toDTO(Discente modelo);

    @Override
    @Mapping(source = "nome",           target = "pessoa.nome")
    @Mapping(source = "telefone",       target = "pessoa.telefone")
    @Mapping(source = "email",          target = "pessoa.email")
    @Mapping(source = "cpf",            target = "pessoa.cpf")
    @Mapping(source = "cpf",            target = "cpf")
    Discente toModelo(DiscenteDTO discenteDTO);

}
