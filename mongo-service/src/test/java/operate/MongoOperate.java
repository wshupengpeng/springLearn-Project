package operate;

import com.mongo.MongoSpringApplication;
import com.mongo.entity.Book;
import com.mongo.entity.DynamicData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @creater hpp
 * @Date 2023/7/19-19:31
 * @description: mongo 基础操作练习
 */
@SpringBootTest(classes = MongoSpringApplication.class)
@RunWith(SpringRunner.class)
public class MongoOperate {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void insert() {
        Book book1 = new Book("1", "CSS", "asd");
        Book book2 = new Book("2", "java", "asiudh");
        Book book3 = new Book("3", "C#", "aasdou");
        Book book4 = new Book("4", "javaSE", "jhgas");
        mongoTemplate.insert(book1);
        mongoTemplate.insert(book2);
        mongoTemplate.insert(book3);
        mongoTemplate.insert(book4);
    }


    @Test
    public void insert1() {
        for (int i = 0; i < 5; i++) {
            DynamicData dynamicData = new DynamicData();
            dynamicData.setCompanyName("测试公司" + i);
            dynamicData.setBankName("测试银行" + i);
            dynamicData.setNo("测试编号" + i);

            List<DynamicData.DynamicParam> referenceList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                DynamicData.DynamicParam dynamicParam = new DynamicData.DynamicParam();
                dynamicParam.setTitleName("映射表头" + j + ",映射下标：" + i);
                dynamicParam.setTitleValue("映射表值" + j + ",映射下标：" + i);
                dynamicParam.setTitleIndex(j);
                referenceList.add(dynamicParam);
            }

            dynamicData.setReferenceList(referenceList);
            dynamicData.setDynamicTextList(referenceList);

            mongoTemplate.insert(dynamicData);
        }

    }

    @Test
    public void createIndex() {


    }

    @Test
    public void getData() {
        // 取所有数据
        List<DynamicData> all = mongoTemplate.findAll(DynamicData.class);

        // 条件查询
        Query query = new Query(Criteria.where(""));

    }

}
