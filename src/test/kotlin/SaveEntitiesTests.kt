import com.fubao.hubao.components.MyApplication
import com.fubao.hubao.components.model.TestRecord
import jakarta.annotation.Resource
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [MyApplication::class])
class SaveEntitiesTests {

    @Resource
    private lateinit var sqlClient: KSqlClient

    /**
     * 测试混合save
     *
     * 根据ID 修改
     */
    @Test
    fun testUpdateIdEntities(){
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
