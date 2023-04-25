package poi.policy;

import lombok.Data;
import poi.handler.param.CellMergeRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 测试poi自定义策略对象
 * @Author 01415355
 * @Date 2023/4/24 9:17
 */
@Data
public class DetailTable {

    List<List<String>> data;

    List<List<String>> head;

    List<CellMergeRecord> mergeRecords;

    boolean isSupportCustomizePolicy = true;


    public void initData(int row,int col){
        data = new ArrayList<>();
        for(int i = 0; i < row; i++){
            List<String> rowList = new ArrayList<>();
            for(int j = 0; j < col; j++){
                rowList.add("表格数据"+i);
            }
            data.add(rowList);
        }
    }


    public void initTitle(int titleSize){
        head =  new ArrayList<>();
        for (int i = 0; i < titleSize; i++) {
            List<String> colList = new ArrayList<>();
            colList.add("标题" + i);
            head.add(colList);
        }
    }



}
