package repair.app.user

import repair.app.common.PersistableEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(val firstName: String,
           val lastName: String,
           val email: String,
           val password: String,
           val phone: String,
           val logo: String,
           val rating: Int = 0) : PersistableEntity<Long>()