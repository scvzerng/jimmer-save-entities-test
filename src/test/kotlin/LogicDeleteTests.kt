import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fubao.hubao.components.MyApplication
import com.fubao.hubao.components.model.Department
import com.fubao.hubao.components.model.Employee
import com.fubao.hubao.components.model.Organization
import com.fubao.hubao.components.model.copy
import com.fubao.hubao.components.model.departmentId
import com.fubao.hubao.components.model.fetchBy
import com.fubao.hubao.components.model.name
import jakarta.annotation.Resource
import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.babyfish.jimmer.sql.runtime.LogicalDeletedBehavior
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest(classes = [MyApplication::class])
@Transactional
open class LogicDeleteTests {

    @Resource
    private lateinit var sqlClient: KSqlClient

    @Test
    @Rollback
    fun testDeleteOrganization(){
        val organization = sqlClient.createQuery(Organization::class) {
            where(table.name eq "金风细雨楼")
            select(table.fetchBy {
                allScalarFields()
                departments {
                    allScalarFields()
                    employees {
                        allScalarFields()
                    }
                }
            })
        }.fetchOne()
        sqlClient.entities.delete(Organization::class, organization.id)
        Assertions.assertNull(sqlClient.entities.findById(Organization::class, organization.id))
    }

    @Test
    @Rollback
    fun testDeleteDepartment(){
        val department = sqlClient.createQuery(Department::class) {
            where(table.name eq "财务部")
            select(table.fetchBy {
                allScalarFields()
                employees {
                    allScalarFields()
                }
            })
        }.fetchOne()

        sqlClient.entities.delete(Department::class, department.id) {
            setDissociateAction(Employee::department, DissociateAction.SET_NULL)
        }
        val dissociateEmployees = sqlClient.filters {
            setBehavior(LogicalDeletedBehavior.IGNORED)
        }.createQuery(Employee::class) {
            where(table.departmentId.isNull())
            select(table.fetchBy {
                allScalarFields()
            })
        }.execute()
        Assertions.assertEquals(3, dissociateEmployees.size)
    }

    @Test
    @Rollback
    fun testDeleteEmployee(){
        val employee = sqlClient.createQuery(Employee::class) {
            where(table.name eq "张三")
            select(table.fetchBy {
                allScalarFields()
            })
        }.fetchOne()

        sqlClient.entities.delete(Employee::class, employee.id)

        val logicDeletedEmployee = sqlClient.filters {
            setBehavior(LogicalDeletedBehavior.IGNORED)
        }.createQuery(Employee::class) {
            where(table.name eq "张三")
            select(table.fetchBy {
                allScalarFields()
            })
        }.fetchOne()
        Assertions.assertNotNull(logicDeletedEmployee)
    }

    @Test
    fun testLogicDelete() {
        val department = sqlClient.createQuery(Organization::class) {
            where(table.name eq "金风细雨楼")
            select(table.fetchBy {
                allScalarFields()
                departments {
                    allScalarFields()
                    employees {
                        allScalarFields()
                    }
                }
            })

        }.fetchOne()

        sqlClient.save(department.copy {
            departments = departments().filter { it.name == "财务部" }
        })

        val organization2 = sqlClient.filters {
            setBehavior(LogicalDeletedBehavior.IGNORED)
        }.createQuery(Organization ::class) {
            where(table.name eq "财务部")
            select(table.fetchBy {
                allScalarFields()
                departments {
                    allScalarFields()
                    employees {
                        allScalarFields()
                    }
                }
            })

        }.fetchOne()

        Assertions.assertEquals(3, organization2.departments.size)

    }

}
