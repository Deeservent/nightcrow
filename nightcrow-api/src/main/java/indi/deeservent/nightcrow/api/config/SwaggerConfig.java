package indi.deeservent.nightcrow.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @author Deeservent onion.dzw@icloud.com  babamu@126.com
 */
@Configuration
public class SwaggerConfig{

    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "indi.deeservent.nightcrow.api" };
        return GroupedOpenApi.builder().group("nightcrow-api")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact= new Contact();
        contact.setName("Deeservent onion.dzw@icloud.com");

        return new OpenAPI().info(new Info()
            .title("nightcrow-api")
            .description( "nightcrow-api")
            .contact(contact)
            .version("1.0")
            .termsOfService("https://maku.net")
            .license(new License().name("MIT")
            .url("https://maku.net")));
    }

}