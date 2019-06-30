package repair.app.image

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File


@Component
class AmazonS3Client(@Value("\${aws.region}")
                     final val region: String,
                     @Value("\${aws.s3.bucket.name}")
                     val bucketName: String) {

    val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
            .build()

    fun storeFiles(files: Array<MultipartFile>): List<String> {
        return files.map { file ->
            val tmpFile = File(file.originalFilename)
            tmpFile.createNewFile()
            file.transferTo(tmpFile)

            val request = PutObjectRequest(bucketName, file.originalFilename, tmpFile)
            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.addUserMetadata("x-amz-meta-title", file.originalFilename)
            request.metadata = metadata

            s3Client.putObject(request)

            "https://s3.$region.amazonaws.com/$bucketName/images/${file.originalFilename}"
        }
    }

    fun getFile(path: String): S3Object {
        return s3Client.getObject(bucketName, path)
    }
}
