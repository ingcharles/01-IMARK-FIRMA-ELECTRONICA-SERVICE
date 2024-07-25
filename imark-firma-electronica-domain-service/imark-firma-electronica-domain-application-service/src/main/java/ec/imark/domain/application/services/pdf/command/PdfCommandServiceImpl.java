/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase PdfCommandServiceImpl 22/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.domain.application.services.pdf.command;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ec.imark.domain.application.ports.inputs.pdf.command.PdfCommandService;
import ec.imark.record.request.PdfRequestRecord;
import ec.imark.record.response.PdfResponseRecord;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.spi.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PdfCommandServiceImpl implements PdfCommandService {

  @Override
  @Transactional
  public PdfResponseRecord firmar(PdfRequestRecord pdfRequestRecord) {
    String pdfPath = "C:\\respaldo\\firmaElectronica\\input.pdf";
    // Ruta del archivo .p12
    String p12Path = "C:\\respaldo\\firmaElectronica\\firma.p12";
    // Contraseña del archivo .p12
    String p12Password = "Edu3751997";
    String signedPdfFilePath = "C:\\respaldo\\firmaElectronica\\output.pdf";

    File pdfFile = new File(pdfPath);
    if (!pdfFile.exists() || !pdfFile.isFile()) {
      //throw new IOException("El archivo PDF en la ruta especificada no existe: "+pdfFile);
    }
    DSSDocument toSignDocument = new FileDocument(pdfFile);
    DSSDocument toSignDocumentQr;
    PAdESService service;
    SignatureValue signatureValue;
    try {

      // Crear el token PKCS12 con la protección de contraseña
      KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(p12Password.toCharArray());

      try (Pkcs12SignatureToken signingToken = new Pkcs12SignatureToken(new File(p12Path), passwordProtection)) {

        //SignatureTokenConnection signingToken = new Pkcs12SignatureToken(new FileInputStream(p12FilePath), new KeyStore.PasswordProtection(password.toCharArray()));
        //CertificateToken certificate = signingToken.getKeys().getFirst();
        // Obtener la clave privada y la cadena de certificados
        DSSPrivateKeyEntry privateKey = signingToken.getKeys().getFirst();
        CertificateToken signingCertificate = privateKey.getCertificate();
        CertificateToken[] certificateChain = privateKey.getCertificateChain();

        PAdESSignatureParameters parameters = new PAdESSignatureParameters();
        parameters.setSigningCertificate(signingCertificate);
        parameters.setCertificateChain(certificateChain);
        parameters.setSignaturePackaging(SignaturePackaging.ENVELOPED);

        // Coordenadas de la firma y la imagen QR
        float x = 100;
        float y = 100;
        int pageNumber = 1; // Cambiar según la página donde quieras firmar

        // Crear el documento PDF
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
          PDPage page = document.getPage(pageNumber - 1);

          // Añadir la imagen QR
          String qrContent = "Información de la firma";
          BufferedImage qrImage = generateQRImage(qrContent);
          PDImageXObject pdImage = PDImageXObject.createFromByteArray(document,
              convertBufferedImageToByteArray(qrImage), "QR");

          try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
              PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.drawImage(pdImage, x, y, 100, 100); // Cambiar el tamaño según sea necesario
          }

          // Guardar el documento con la imagen QR
          document.save(signedPdfFilePath);

          // Firmar el documento con la imagen QR
          toSignDocumentQr = new FileDocument(new File(signedPdfFilePath));

          // Crear el servicio de firma
          service = new PAdESService(new CommonCertificateVerifier());

          // Obtener los datos a firmar
          ToBeSigned dataToSign = service.getDataToSign(toSignDocumentQr, parameters);

          // Calcular el valor de la firma
          signatureValue = signingToken.sign(dataToSign, DigestAlgorithm.SHA256, privateKey);
        }

        // Firmar el documento
        DSSDocument signedDocument = service.signDocument(toSignDocumentQr, parameters, signatureValue);
        // Guardar el documento firmado
        signedDocument.save(signedPdfFilePath);

        // Firmar el documento con la imagen QR
        //DSSDocument signedDocument = new FileDocument(new File(signedPdfFilePath));
        //SignatureService signatureService = new SignatureService(new FileCacheDataLoader());
        //signedDocument = signatureService.signDocument(signedDocument, parameters, signingToken);

        // Guardar el documento firmado
        //signedDocument.save(signedPdfFilePath);
      }
    } catch (IOException | WriterException e) {
      System.out.println("Exception: " + e.getMessage());

    }
    return null;
  }

  private static BufferedImage generateQRImage(String text) throws WriterException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

    BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < 200; x++) {
      for (int y = 0; y < 200; y++) {
        image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
      }
    }
    return image;
  }

  private static byte[] convertBufferedImageToByteArray(BufferedImage image) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    return baos.toByteArray();
  }


}
