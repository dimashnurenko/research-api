package repair.app.web.security

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import repair.app.common.ErrorCode
import repair.app.common.ServiceException
import repair.app.user.User

@RequestScope
@Component
open class UserHolder {
    var user: User? = null

    fun user(): User {
        return if (user == null) throw ServiceException(ErrorCode.NOT_AUTHORIZED, mapOf("access.forbidden" to "")) else user as User
    }
}
