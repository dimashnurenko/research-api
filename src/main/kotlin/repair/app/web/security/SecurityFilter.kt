package repair.app.web.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import repair.app.common.ErrorCode
import repair.app.common.ServiceException
import repair.app.user.User
import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityFilter(private val secretKey: String, private val userHolder: UserHolder) : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header.isNullOrBlank()) {
            chain.doFilter(request, response)
            return
        }
        val token: String = if (header.startsWith("Bearer"))
            header.substring(header.indexOf(" ")).trim()
        else
            throw ServiceException(ErrorCode.NOT_AUTHORIZED, mapOf("authentication.header.invalid" to ""))

        doAuth(token)

        chain.doFilter(request, response)
    }

    private fun doAuth(token: String) {
        val body = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secretKey.toByteArray())).parseClaimsJws(token).body

        val user = User(body["id"] as String,
                        body["email"] as String,
                        body["password"] as String,
                        body["name"] as String,
                        body["phone"] as String,
                        "")

        SecurityContextHolder.getContext().authentication = toAuthentication(user)
        userHolder.user = user
    }

    private fun toAuthentication(user: User): Authentication {
        val securityUser = org.springframework.security.core.userdetails.User(user.email,
                                                                              user.password,
                                                                              emptyList())
        return UsernamePasswordAuthenticationToken(securityUser, securityUser.password, securityUser.authorities)
    }
}