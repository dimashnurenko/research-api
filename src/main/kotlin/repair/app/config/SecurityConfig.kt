package repair.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import repair.app.web.security.SecurityFilter
import repair.app.web.security.UserHolder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    private val notAuthorizedEntryPoint = { _: HttpServletRequest,
                                            response: HttpServletResponse,
                                            _: AuthenticationException ->
        response.sendError(SC_UNAUTHORIZED, "Access Denied")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/api/swagger-resources/**").permitAll()
                .antMatchers("/api/swagger-resources").permitAll()
                .antMatchers("/api/swagger-ui.html").permitAll()
                .antMatchers("/api/v2/api-docs").permitAll()
                .antMatchers("/api/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/api/images").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(notAuthorizedEntryPoint)
                .and()
                .csrf().disable()
    }

    @Bean
    open fun authFilter(@Value("\${app.security.key}")
                        secretKey: String,
                        userHolder: UserHolder): FilterRegistrationBean<SecurityFilter> {
        val registrationBean = FilterRegistrationBean(SecurityFilter(secretKey, userHolder))
        registrationBean.order = 0
        return registrationBean
    }
}