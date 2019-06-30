package repair.app.claim

import java.text.SimpleDateFormat

class UpdateClaimDto(
        val id: Long,
        val title: String? = null,
        val description: String? = null,
        val images: List<String>? = null,
        val tags: List<String>? = null
                    )

class ClaimResponse(claim: Claim) {
    companion object {
        val SIMPLE_DATE_FORMAT = SimpleDateFormat("MM-dd-YYYY hh:mm:ss")
    }

    private val id: Long = claim.getId()
    private val title: String = claim.title
    private val description: String = claim.description
    private val createdDate: String = SIMPLE_DATE_FORMAT.format(claim.createdDate)
    private val updatedDate: String = SIMPLE_DATE_FORMAT.format(claim.updatedDate)
    private val images: List<String> = claim.images.map { it.image }
    private val tags: List<String> = claim.tags.map { it.tag }
}

class ClaimFilter(val tags: List<String>, val minRating: Int)