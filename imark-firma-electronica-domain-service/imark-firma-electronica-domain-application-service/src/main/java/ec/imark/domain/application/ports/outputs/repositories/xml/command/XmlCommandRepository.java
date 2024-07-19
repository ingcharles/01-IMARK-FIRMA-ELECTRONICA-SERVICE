/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XmlCommandRepository 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.domain.application.ports.outputs.repositories.xml.command;

import ec.imark.record.request.XmlRequestRecord;
import ec.imark.record.response.XmlResponseRecord;

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
public interface XmlCommandRepository {
  XmlResponseRecord firmar(XmlRequestRecord xmlRequestRecord);
}
