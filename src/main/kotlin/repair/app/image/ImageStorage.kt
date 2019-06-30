package repair.app.image

import org.springframework.data.repository.CrudRepository
import org.springframework.web.multipart.MultipartFile
import repair.app.user.User

interface ImageMetadataRepo : CrudRepository<ImageMetadata, String>

interface ImageStorage {
    fun storeImages(files: Array<MultipartFile>, user: User): List<String>

    fun findImageMetadata(imageId: String): ImageMetadata

    fun findRawImageById(imageId: String): ByteArray
}