import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.mybatis.test.mapper.UserMapper;
import com.mybatis.test.service.IUserService;
import com.mybatis.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mybatis.test.MybatisPlusIntercepterApplication;

/**
 * @creater hpp
 * @Date 2023/8/21-20:53
 * @description:  用于测试mybatisPlus拦截器的单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusIntercepterApplication.class)
@Slf4j
public class MybatisPlusIntercepterUnitTest {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserMapper userMapper;

    /**
     *  测试不使用mapper.xml 情况下拦截器是否触发
     */
    @Test
    public void notUseMapperXml(){
        iUserService.getById(1);
    }


    /**
     *  测试使用mapper.xml 情况下拦截器是否触发
     */
    @Test
    public void useMapperXml(){
        userMapper.selectDiffById(1);
    }

    @Test
    public void testParseSqlCostTime() throws JSQLParserException {
        long start = System.currentTimeMillis();
        String testSql = "select *,count(tt.id) from t_user tr inner join t_map tm on tr.id = tm.user_id inner join t_test tt on tr.id = tt.user_id where tr.id = 1 group by tr.id having count(tt.id) > 1 order by tr.id desc";
        for(int i = 0; i < 100000; i++){
            JsqlParserGlobal.parse(testSql);
        }
        log.info("testParseSqlCostTime:{}ms", System.currentTimeMillis() - start);
    }

}
