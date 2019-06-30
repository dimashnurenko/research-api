package repair.app.image

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import repair.app.common.ErrorCode
import repair.app.common.ServiceException
import repair.app.user.User

@Component
class AmazonS3Storage(private val client: AmazonS3Client,
                      @Value("\${aws.s3.bucket.name}")
                      val bucketName: String,
                      private val imageMetadataRepo: ImageMetadataRepo) : ImageStorage {

    override fun storeImages(files: Array<MultipartFile>, user: User): List<String> {
        val filePaths = client.storeFiles(files)
        //todo
        val storedImages = imageMetadataRepo.saveAll(filePaths.map { ImageMetadata("", "") })
        return storedImages.map { "/api/images/${it.getId()}" }
    }

    override fun findImageMetadata(imageId: String): ImageMetadata = imageMetadataRepo.findById(imageId).orElseThrow {
        ServiceException(ErrorCode.RESOURCE_NOT_FOUND, mapOf("image.not.found" to imageId))
    }

    override fun findRawImageById(imageId: String): ByteArray {
        val metadata = findImageMetadata(imageId)
        val s3Object = client.getFile(metadata.path.split("/$bucketName/")[1])
        val objectContent = s3Object.objectContent
        val bytes = objectContent.readBytes()
        objectContent.close()
        return bytes
    }
}
