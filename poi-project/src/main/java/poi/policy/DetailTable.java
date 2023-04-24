package poi.policy;

import com.deepoove.poi.data.RowRenderData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 测试poi自定义策略对象
 * @Author 01415355
 * @Date 2023/4/24 9:17
 */
@Data
public class DetailTable {

    List<List<String>> data = new ArrayList<>();

    boolean isSupportCustomizePolicy = true;


    public void initData(){
        int dataSize = 10;
        int titleSize = 2;
        initTitle(titleSize);
        int preNum = 1;
        for(int i = 1; i < dataSize; i++){
            List<String> row = new ArrayList<>();
            for(int j = 0; j < titleSize; j++){
                row.add((preNum + i)/2 + "");
            }
        }
    }


    public void initTitle(int titleSize){
        List<String> title = new ArrayList<>();
        for (int i = 0; i < titleSize; i++) {
            title.add("标题" + i);
        }
        data.add(title);
    }



}
