package poi.pdf.param;

import lombok.Data;

/**
 * @Description: 坐标记录
 * @Author 01415355
 * @Date 2023/5/26 9:25
 */
@Data
public class CoordinateRecord {
    /**
     *  横坐标
     */
    private float x;

    /**
     *  纵坐标
     */
    private float y;

    /**
     *  页码
     */
    private int pageNum;
}
