/**
 * <p> Proyecto imark-firma-electronica-service.
 * <p> Clase CharEncodingFilter 19/7/2024.
 * <p> Copyright 2024 Consejo de la Judicatura.
 * <p> Todos los derechos reservados.
 */
package ec.imark.base.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

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
@Configuration
public class CharEncodingFilter {
  @Bean
  public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    filter.setForceEncoding(true);

    FilterRegistrationBean<CharacterEncodingFilter> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setFilter(filter);
    registrationBean.addUrlPatterns("/*");

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<CharsetResponseFilter> charsetResponseFilterRegistrationBean() {
    FilterRegistrationBean<CharsetResponseFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new CharsetResponseFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
