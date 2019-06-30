package repair.app.tag

import repair.app.common.PersistableEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tags")
class Tag(
        @Column
        val tag: String = "",
        @Column
        val description: String = "") : PersistableEntity<Long>()