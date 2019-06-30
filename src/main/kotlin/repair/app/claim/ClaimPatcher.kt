package repair.app.claim

class ClaimPatcher(private val claim: Claim, private val dto: UpdateClaimDto) {

    fun doPatch(): Claim {
        dto.title?.let { claim.title = it }
        dto.description?.let { claim.description = it }
        dto.images?.let { claim.images = toClaimImages(it) }
        dto.tags?.let { claim.tags = toClaimTags(it) }
        return claim
    }

    private fun toClaimImages(images: List<String>): List<ClaimImage> {
        return images.map { ClaimImage(claim.getId(), it) }
    }

    private fun toClaimTags(tags: List<String>): List<ClaimTag> {
        return tags.map { ClaimTag(claim.getId(), it) }
    }
}
