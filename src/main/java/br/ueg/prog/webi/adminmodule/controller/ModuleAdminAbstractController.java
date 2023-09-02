package br.ueg.prog.webi.adminmodule.controller;

import br.ueg.prog.webi.api.controller.AbstractController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ModuleAdminAbstractController extends AbstractController {


    /**
     * Exporta o {@link JasperPrint} no formato PDF conforme os parâmetros
     * informados.
     *
     * @param print -
     * @param name -
     * @return -
     * @throws JRException -
     * @throws IOException -
     */
    protected ResponseEntity<InputStreamResource> toPDF(final JasperPrint print, final String name)
            throws JRException, IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(print, out);

            try (ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray())) {
                return ResponseEntity.ok().header("Content-Disposition", "inline; filename=" + name.trim())
                        .contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(input));
            }
        }
    }
}
