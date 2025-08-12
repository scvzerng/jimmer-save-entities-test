package com.fubao.hubao.components.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.Key

@Entity
interface TestRecord {
    @Id
    @GeneratedValue(generatorType = TimeUserIdGenerator::class)
    val id: Long

    @Key
    val name: String

    val memberCount:Long
}
