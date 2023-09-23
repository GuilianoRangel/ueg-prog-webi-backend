package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.LocalDTO;
import br.ueg.prog.webi.faculdade.model.Local;
import jakarta.validation.constraints.Max;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ResponsabilidadeMapper.class})
public interface LocalMapper extends BaseMapper<Local, LocalDTO>{
    @Override
    //@Mapping(source = "localDTO", target = "chaves.local")
    Local toModelo(LocalDTO localDTO) ;
}
