package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.CargoDTO;
import br.ueg.prog.webi.faculdade.dto.LocalDTO;
import br.ueg.prog.webi.faculdade.model.Cargo;
import br.ueg.prog.webi.faculdade.model.Local;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CargoMapper extends BaseMapper<Cargo, CargoDTO>{
}
