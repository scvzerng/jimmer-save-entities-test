package com.fubao.hubao.components.model.model

import com.fubao.hubao.components.model.Organization
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass
import org.babyfish.jimmer.sql.OnDissociate

/**
 * 多租户
 *
 * 继承该类即可实现组织级别的数据过滤
 */
@MappedSuperclass
interface TenantAware {

    @ManyToOne
    @Key
    @OnDissociate(DissociateAction.DELETE)
    val organization: Organization?

}