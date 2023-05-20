package br.ueg.prog.webi.faculdade.mapper;

import br.ueg.prog.webi.api.mapper.BaseMapper;
import br.ueg.prog.webi.faculdade.dto.LivroDTO;
import br.ueg.prog.webi.faculdade.model.Livro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = TipoMapper.class
)
public interface LivroMapper
        extends BaseMapper<Livro, LivroDTO> {
}
