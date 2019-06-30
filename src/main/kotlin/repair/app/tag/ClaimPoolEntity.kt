package repair.app.tag

import repair.app.common.PersistableEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "claims_pool")
class ClaimPoolEntity(
        val claimId: Long
                     ) : PersistableEntity<Long>()