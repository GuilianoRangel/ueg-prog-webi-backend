package br.ueg.prog.webi.api.converters;

import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.api.interfaces.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StatusAtivoInativoConverter  implements IConverter {
    private static final Logger LOG =
            LoggerFactory.getLogger(LongConverter.class);
    @Override
    public Object converter(String value) {
        if(Objects.nonNull(value)){
            try {
                return StatusAtivoInativo.getById(value);
            }catch (Exception e){
                LOG.error("Erro ao Converter valor(%s) para StatusAtivoInativo",value);
            }
        }
        return null;
    }
}