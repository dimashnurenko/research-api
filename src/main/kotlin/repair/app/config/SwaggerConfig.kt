package repair.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
open class SwaggerConfig {
    private val AUTH_KEY = "authKey"

    @Bean
    open fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("repair.app.web"))
            .build()
            .pathMapping("/")
            .securitySchemes(listOf(apiKey()))
            .securityContexts(listOf(securityContext()))

    private fun apiKey(): ApiKey {
        return ApiKey(AUTH_KEY, "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        return listOf(SecurityReference(AUTH_KEY, arrayOf()))
    }
}
