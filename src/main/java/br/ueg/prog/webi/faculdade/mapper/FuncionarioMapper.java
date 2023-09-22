package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.AmigoDTO;
import br.ueg.prog.webi.faculdade.dto.FuncionarioDTO;
import br.ueg.prog.webi.faculdade.model.Amigo;
import br.ueg.prog.webi.faculdade.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper
        extends BaseMapper<Funcionario, FuncionarioDTO> {

    @Override
    @Mapping(source = "pessoa.nome",        target = "nome")
    @Mapping(source = "pessoa.telefone",    target = "telefone")
    @Mapping(source = "pessoa.email",       target = "email")
    @Mapping(source = "cargo.codigo",       target = "cargo_codigo")
    @Mapping(source = "cargo.nome",         target = "cargo_nome")
    FuncionarioDTO toDTO(Funcionario modelo);

    @Override
    @Mapping(source = "cargo_codigo",   target = "cargo.codigo")
    @Mapping(source = "cargo_nome",     target = "cargo.nome")
    @Mapping(source = "nome",           target = "pessoa.nome")
    @Mapping(source = "telefone",       target = "pessoa.telefone")
    @Mapping(source = "email",          target = "pessoa.email")
    @Mapping(source = "cpf",            target = "pessoa.cpf")
    @Mapping(source = "cpf",            target = "cpf")
    Funcionario toModelo(FuncionarioDTO funcionarioDTO);

}
