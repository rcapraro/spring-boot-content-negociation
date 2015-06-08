package demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false)
                .favorParameter(true) //format = pdf
                .ignoreAcceptHeader(false)
                .useJaf(false)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("pdf", MediaType.parseMediaType("application/pdf"));
    }
}
