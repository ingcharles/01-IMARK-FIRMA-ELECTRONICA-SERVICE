/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XmlCommandControllerImpl 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.firma.controller.xml.command.impl;

import ec.imark.domain.application.ports.inputs.xml.command.XmlCommandService;
import ec.imark.firma.controller.xml.command.XmlCommandController;
import ec.imark.record.request.XmlRequestRecord;
import ec.imark.record.response.XmlResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA CLASE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Carlos.Anchundia - 19/7/2024
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author Carlos.Anchundia
 * @version 1.0.0 $
 * @since 19/7/2024
 */
@RestController
@RequiredArgsConstructor
public class XmlCommandControllerImpl implements XmlCommandController {

  private final XmlCommandService xmlCommandService;

  @Override
  public XmlResponseRecord firmar(XmlRequestRecord xmlRequestRecord) {
    return xmlCommandService.firmar(xmlRequestRecord);
  }
}
