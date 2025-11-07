package com.fubao.hubao.components.model

import com.fubao.hubao.components.model.model.TenantAware
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToMany

@Entity
interface Department : TenantAware {
    @Id
    @GeneratedValue(generatorType = TimeUserIdGenerator::class)
    val id: Long

    @Key
    val name: String

    @OneToMany(mappedBy = "department")
    val employees: List<Employee>

    @LogicalDeleted("true")
    val deleted: Boolean

}