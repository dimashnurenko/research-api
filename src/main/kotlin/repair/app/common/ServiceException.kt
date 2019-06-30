package repair.app.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

class ServiceException(val code: ErrorCode,
                       val details: Map<String, String>, cause: Throwable? = null) : RuntimeException(cause)

enum class ErrorCode(val code: Int, val httpCode: HttpStatus) {
    RESOURCE_NOT_FOUND(10_000, NOT_FOUND),
    NOT_AUTHORIZED(10_001, UNAUTHORIZED),
    STORE_USER_ERROR(10_002, BAD_REQUEST)
}