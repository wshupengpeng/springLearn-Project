package poi.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import org.junit.Test;
import poi.handler.param.CellMergeRecord;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/24 9:20
 */
public class DynamicTableTest {

    @Test
    public void writeDynamicTable() throws Exception {
        String resource = "src/main/resources/水质模板.docx";
        DetailTable datas = new DetailTable();

        datas.initData(5,5);
        datas.initTitle(5);

        List<CellMergeRecord> mergeRecordList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            if (i % 2 == 0){
                CellMergeRecord record = new CellMergeRecord();
                record.setRowStartIndex(i);
                record.setRowEndIndex(i+1);
                record.setColEndIndex(i+1);
                record.setColStartIndex(i);
                mergeRecordList.add(record);
            }
        }

        datas.setMergeRecords(mergeRecordList);

        Configure config = Configure.newBuilder()
                .bind("dynamicTable", new DetailTablePolicy())
                .build();
//        Map<String, RenderData> render = new HashMap<>();
//        MiniTableRenderData renderData = new MiniTableRenderData(RowRenderData
//                .build(new String[]{"标题头一", "标题头二"}), Arrays.asList(RowRenderData.build("1", "2")));
//        render.put("dynamicTable", renderData);
        Map<String,Object> render = new HashMap<>();
        render.put("dynamicTable",datas);
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(render);
        FileOutputStream out = new FileOutputStream("out_水质报告测试.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
