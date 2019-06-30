package repair.app.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
open class ClaimController {

    @GetMapping(path = ["/hello"])
    open fun sayHello(): String {
        return "hello maza fakasdsddsdsd  "
    }
}