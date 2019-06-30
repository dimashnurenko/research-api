package repair.app.web.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import repair.app.common.ErrorCode
import repair.app.common.ServiceException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler
    fun handleServiceExceptions(exception: ServiceException): ResponseEntity<Response> {
        return ResponseEntity.status(exception.code.httpCode).body(Response(exception.code, exception.details))
    }

    data class Response(val code: ErrorCode, val details: Map<String, String>)
}