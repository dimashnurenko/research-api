package repair.app.common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneId.of
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class PersistableEntity<T : Serializable> {
    companion object {
        private val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: T? = null

    @Column(name = "created_date", columnDefinition = "timestamp")
    var createdDate: LocalDateTime? = null

    @Column(name = "updated_date", columnDefinition = "timestamp")
    var updatedDate: LocalDateTime? = null

    @CreatedBy
    @Column(name = "created_by")
    var createdBy: Long? = null

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: Long? = null

    fun getId(): T {
        return id!!
    }

    @PrePersist
    fun prePersist() {
        this.createdDate = now(of("UTC"))
    }

    @PreUpdate
    fun preUpdate() {
        this.updatedDate = now(of("UTC"))
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as PersistableEntity<*>

        return this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"
}