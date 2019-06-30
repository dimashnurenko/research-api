package repair.app.claim

import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import repair.app.claim.ClaimState.PENDING
import repair.app.common.ErrorCode
import repair.app.common.ServiceException
import repair.app.tag.ClaimPoolEntity

interface ClaimRepo : JpaRepository<Claim, Long>

interface ClaimPoolRepo : JpaRepository<ClaimPoolEntity, Long>

@Service
open class ClaimsService(private val repo: ClaimRepo,
                         private val claimPoolRepo: ClaimPoolRepo) {

    fun createClaim(): Long {
        return repo.save(Claim()).getId()
    }

    fun updateClaim(dto: UpdateClaimDto): ClaimResponse {
        val claim = getClaim(dto.id)

        val patch = ClaimPatcher(claim, dto).doPatch()
        val updatedClaim = repo.save(patch)

        return ClaimResponse(updatedClaim)
    }

    private fun getClaim(claimId: Long): Claim {
        return repo.findById(claimId).orElseThrow {
            ServiceException(ErrorCode.RESOURCE_NOT_FOUND,
                             mapOf("message" to "Claim with id $claimId not found"))
        }
    }

    @Transactional(readOnly = true)
    open fun getClaimById(id: Long): ClaimResponse {
        val claim = getClaim(id)
        return ClaimResponse(claim)
    }

    @Transactional
    open fun publish(claimId: Long) {
        val claim = getClaim(claimId)
        claim.state = PENDING

        claimPoolRepo.save(ClaimPoolEntity(claimId))
    }

    open fun findByFilter(filter: ClaimFilter): Page<ClaimResponse> {
        //select from claim_pool inner join claim where state is pending inner join user order by user.rating desc
        return null!!
    }
}
