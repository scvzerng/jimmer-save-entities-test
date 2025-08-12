package com.fubao.hubao.components.model

import org.babyfish.jimmer.sql.meta.UserIdGenerator

class TimeUserIdGenerator: UserIdGenerator<Long> {
    override fun generate(entityType: Class<*>?): Long? {
        return System.currentTimeMillis()
    }
}
