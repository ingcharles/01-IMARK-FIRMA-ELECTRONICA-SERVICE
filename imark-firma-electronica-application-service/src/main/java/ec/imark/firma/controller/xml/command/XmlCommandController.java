/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XmlCommandController 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.firma.controller.xml.command;

import ec.imark.record.request.XmlRequestRecord;
import ec.imark.record.response.XmlResponseRecord;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.text.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA INTERFACE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Carlos.Anchundia - 19/7/2024
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author Carlos.Anchundia
 * @version 1.0.0
 * @since 19/7/2024
 */
@RequestMapping("/command/xml")
@Validated
public interface XmlCommandController {

  @PostMapping("/firmar")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Permite firmar un documento Xml.")
  XmlResponseRecord firmar(
      @Valid @NotNull @RequestBody XmlRequestRecord xmlRequestRecord)
      throws ParseException;


}
