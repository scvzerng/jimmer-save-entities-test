package com.fubao.hubao.components.model

import com.fubao.hubao.components.model.model.TenantAware
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OnDissociate

@Entity
interface Employee: TenantAware {
    @Id
    @GeneratedValue(generatorType = TimeUserIdGenerator::class)
    val id: Long

    @Key
    val name: String

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    val department: Department?

    @LogicalDeleted("true")
    val deleted: Boolean
}