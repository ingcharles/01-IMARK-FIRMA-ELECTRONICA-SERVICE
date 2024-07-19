/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XmlCommandRepositoryImpl 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.dataacces.adapter.xml.command;

import ec.imark.domain.application.ports.outputs.repositories.xml.command.XmlCommandRepository;
import ec.imark.record.request.XmlRequestRecord;
import ec.imark.record.response.XmlResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class XmlCommandRepositoryImpl implements XmlCommandRepository {
  @Override
  @Transactional
  public XmlResponseRecord firmar(XmlRequestRecord xmlRequestRecord){
    return null;
  }
}
