/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XmlCommandServiceImpl 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.domain.application.services.xml.command;

import ec.imark.domain.application.ports.inputs.xml.command.XmlCommandService;
import ec.imark.domain.application.ports.outputs.repositories.xml.command.XmlCommandRepository;
import ec.imark.record.request.XmlRequestRecord;
import ec.imark.record.response.XmlResponseRecord;
import eu.europa.esig.dss.model.DSSDocument;
import lombok.RequiredArgsConstructor;
import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignatureInput;
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
public class XmlCommandServiceImpl implements XmlCommandService {
  static {
    // Inicializa la biblioteca de seguridad XML
    Init.init();
  }
  private final XmlCommandRepository xmlCommandRepository;
  private final XAdESSignatureUtils xAdESSignatureUtils;
  private final XMLSchemaValidator xMLSchemaValidator;
  @Override
  @Transactional
  public XmlResponseRecord firmar(XmlRequestRecord xmlRequestRecord) {
    // Ruta del archivo XML a firmar
    String xmlPath = "C:\\respaldo\\firmaElectronica\\archivo.xml";
    // Ruta del archivo .p12
    String p12Path = "C:\\respaldo\\firmaElectronica\\firma.p12";
    // Path to your XSD files
    String xsdFilePath = "C:\\respaldo\\firmaElectronica\\factura_V2.0.0.xsd";
    // Contraseña del archivo .p12
    String p12Password = "Edu3751997";



    DSSDocument dSSDocument =xAdESSignatureUtils.firmarDocumentoXml(xmlPath,p12Path,p12Password);
    String xmlPathSigned = "C:\\respaldo\\firmaElectronica\\signed_document.xml";
    boolean xsdVa =xMLSchemaValidator.validarDocumentoXml(xsdFilePath, xmlPathSigned);
    return xmlCommandRepository.firmar(xmlRequestRecord);
  }


}
