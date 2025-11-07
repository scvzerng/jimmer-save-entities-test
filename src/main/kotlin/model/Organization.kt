package com.fubao.hubao.components.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OneToMany

@Entity
interface Organization {
    @Id
    @GeneratedValue(generatorType = TimeUserIdGenerator::class)
    val id: Long

    val name: String

    @LogicalDeleted("true")
    val deleted: Boolean

    @OneToMany(mappedBy = "organization")
    val departments: List<Department>

    @OneToMany(mappedBy = "organization")
    val employees: List<Employee>
}