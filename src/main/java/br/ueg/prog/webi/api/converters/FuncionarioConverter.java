package br.ueg.prog.webi.api.converters;

import br.ueg.prog.webi.api.interfaces.IConverter;
import br.ueg.prog.webi.faculdade.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FuncionarioConverter implements IConverter {
    @Autowired
    private FuncionarioService funcionarioService;

    private static final Logger LOG =
            LoggerFactory.getLogger(LongConverter.class);
    @Override
    public Object converter(String value) {
        if(Objects.nonNull(value)){
            try {
                Long pk = Long.valueOf(value);
                return funcionarioService.obterPeloId(pk);
            }catch (Exception e){
                LOG.error("Erro ao Converter valor(%s) para StatusAtivoInativo",value);
            }
        }
        return null;
    }
}