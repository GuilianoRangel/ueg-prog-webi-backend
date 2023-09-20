package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.AmigoDTO;
import br.ueg.prog.webi.faculdade.model.Amigo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AmigoMapper
        extends BaseMapper<Amigo, AmigoDTO> {

    @Override
    @Mapping(source = "tipo.id" , target = "tipo_id")
    @Mapping(source = "tipo.nome", target = "tipo_nome")
    AmigoDTO toDTO(Amigo modelo);

    @Override
    @Mapping(source = "tipo_id", target = "tipo.id")
    Amigo toModelo(AmigoDTO amigoDTO);

}
