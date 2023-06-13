package webservice.config;

import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;

import java.time.Duration;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/12 15:32
 */
@Configuration
public class WebServiceConfig {

    @Bean
    public WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder) {
        WebServiceMessageSender sender = new HttpWebServiceMessageSenderBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
        WebServiceTemplate template = builder.messageSenders(sender).build();
//        template.setMarshaller(marshaller());
        return template;
    }

//    @Bean
//    public Jaxb2Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // this is the package name specified in the <generatePackage> specified in
//        // pom.xml
//        marshaller.setContextPath("webservice.dto.PmsStandardDto");
//        return marshaller;
//    }

}
