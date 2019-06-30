package repair.app.claim

import repair.app.common.PersistableEntity
import javax.persistence.*

@Entity
@Table(name = "claims")
open class Claim(
        @Column(name = "assigned_to")
        var assignedTo: Long = -1,
        @Enumerated(EnumType.STRING)
        @Column
        var state: ClaimState = ClaimState.NEW,
        @Column
        var title: String = "",
        @Column
        var description: String = "new def descr",
        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
                  )
        @JoinColumn(name = "claim_id")
        var images: List<ClaimImage> = ArrayList(),
        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
                  )
        @JoinColumn(name = "claim_id")
        var tags: List<ClaimTag> = ArrayList()
                ) : PersistableEntity<Long>()

enum class ClaimState {
    NEW, PENDING, ACCEPTED
}

@Entity
@Table(name = "claim_images")
class ClaimImage(
        @Column(name = "claim_id")
        val claimId: Long,
        @Column
        var image: String = ""
                ) : PersistableEntity<Long>()

@Entity
@Table(name = "claim_tags")
class ClaimTag(
        @Column(name = "claim_id")
        val claimId: Long,
        @Column
        var tag: String = ""
              ) : PersistableEntity<Long>()

