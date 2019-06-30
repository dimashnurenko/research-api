package repair.app.image

import repair.app.common.PersistableEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "image_metadata")
data class ImageMetadata(val userId: String,
                         val path: String) : PersistableEntity<Long>()

data class ImageDto(val id: String, val url: String)
