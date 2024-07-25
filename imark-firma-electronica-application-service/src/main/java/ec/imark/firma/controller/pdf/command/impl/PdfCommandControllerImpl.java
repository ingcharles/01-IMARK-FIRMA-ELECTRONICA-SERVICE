/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase pdfCommandControllerImpl 22/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.firma.controller.pdf.command.impl;

import ec.imark.domain.application.ports.inputs.pdf.command.PdfCommandService;
import ec.imark.firma.controller.pdf.command.PdfCommandController;
import ec.imark.record.request.PdfRequestRecord;
import ec.imark.record.response.PdfResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA CLASE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Carlos.Anchundia - 22/7/2024
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author Carlos.Anchundia
 * @version 1.0.0 $
 * @since 22/7/2024
 */
@RestController
@RequiredArgsConstructor
public class PdfCommandControllerImpl implements PdfCommandController {
  private final PdfCommandService pdfCommandService;

  @Override
  public PdfResponseRecord firmar(PdfRequestRecord pdfRequestRecord) {
    return pdfCommandService.firmar(pdfRequestRecord);
  }

}
