/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase ImarkFirmaElectronicaApplication 18/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.base.contenedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA CLASE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Carlos.Anchundia - 18/7/2024
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author Carlos.Anchundia
 * @version 1.0.0 $
 * @since 18/7/2024
 */

@EnableFeignClients(basePackages = "ec.imark")
@SpringBootApplication(scanBasePackages = "ec.imark")
@EnableJpaRepositories(basePackages = {"ec.imark"})
@EntityScan(basePackages = {"ec.imark"})
public class ImarkFirmaElectronicaApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImarkFirmaElectronicaApplication.class, args);
  }
}
