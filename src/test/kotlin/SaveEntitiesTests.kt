import com.fubao.hubao.components.MyApplication
import com.fubao.hubao.components.model.TestRecord
import jakarta.annotation.Resource
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [MyApplication::class])
open class SaveEntitiesTests {

    @Resource
    private lateinit var sqlClient: KSqlClient

    @Test
    fun testOnlyIdEntities(){
        val list = mutableListOf<TestRecord>()
        // db not exist insert it
        list.add(TestRecord {
            id = 999
            name = "test999"
            memberCount = 999L
        })
        // db not exist insert it
        list.add(TestRecord {
            id = 888
            name = "test888"
            memberCount = 999L
        })
        // db  exist update it
        list.add(TestRecord {
            id = 1
            memberCount = 999L
        })
        sqlClient.saveEntities(list, SaveMode.NON_IDEMPOTENT_UPSERT)
        val record1 = sqlClient.findById(TestRecord::class, 1L)!!
        Assertions.assertEquals(record1.memberCount, 999L)
    }

    /**
     * 测试混合save
     *
     * 根据ID 修改
     */
    @Test
    @Transactional
    open fun testUpdateIdEntities(){
        sqlClient.saveEntities(listOf(TestRecord {
            id = 1
            memberCount = 999L
        }, TestRecord {
            name = "test2"
        }))
        // 根据id=1修改memberCount的值未生效
        val record1 = sqlClient.findById(TestRecord::class, 1L)!!
        Assertions.assertEquals(record1.memberCount, 999L)
    }
    /**
     * 测试混合save
     *
     * 根据key
     */
    @Test
    fun testUpdateKeyEntities(){
        sqlClient.saveEntities(listOf(TestRecord {
            name = "test"
            memberCount = 999L
        }, TestRecord {
            name = "test2"
        }))
        // 根据key='test'修改memberCount的值生效
        val record1 = sqlClient.findById(TestRecord::class, 1L)!!
        Assertions.assertEquals(record1.memberCount, 999L)
    }
}
