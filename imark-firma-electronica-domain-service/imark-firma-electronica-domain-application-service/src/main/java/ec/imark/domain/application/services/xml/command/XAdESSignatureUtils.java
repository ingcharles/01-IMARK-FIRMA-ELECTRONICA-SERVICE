/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase XAdESSignatureUtils 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.domain.application.services.xml.command;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
@Service
public class XAdESSignatureUtils implements Serializable {

  public DSSDocument firmarDocumentoXml(String xmlPath, String p12Path, String p12Password) {

    try {
      // Verificar que el archivo XML existe
      File xmlFile = new File(xmlPath);
      if (!xmlFile.exists() || !xmlFile.isFile()) {
        throw new IOException("El archivo XML en la ruta especificada no existe: "+xmlPath);
      }
      DSSDocument toSignDocument = new FileDocument(xmlFile);

      // Crear el token PKCS12 con la protección de contraseña
      KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(p12Password.toCharArray());

      // Cargar el token PKCS12
      XAdESSignatureParameters parameters;
      XAdESService service;
      SignatureValue signatureValue;
      try (Pkcs12SignatureToken signingToken = new Pkcs12SignatureToken(new File(p12Path), passwordProtection)) {

        // Obtener la clave privada y la cadena de certificados
        DSSPrivateKeyEntry privateKey = signingToken.getKeys().getFirst();
        CertificateToken signingCertificate = privateKey.getCertificate();
        CertificateToken[] certificateChain = privateKey.getCertificateChain();

        // Crear parámetros de firma
        parameters = new XAdESSignatureParameters();
        parameters.setSignatureLevel(SignatureLevel.XAdES_BASELINE_B);
        parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);
        parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
        parameters.setSigningCertificate(signingCertificate);
        parameters.setCertificateChain(certificateChain);

        // Crear el servicio de firma
        service = new XAdESService(new CommonCertificateVerifier());

        // Obtener los datos a firmar
        ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

        // Calcular el valor de la firma
        signatureValue = signingToken.sign(dataToSign, DigestAlgorithm.SHA256, privateKey);
      }

      // Firmar el documento
      DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
      // Guardar el documento firmado
      signedDocument.save("C:\\respaldo\\firmaElectronica\\signed_document.xml");
      return signedDocument;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

}
