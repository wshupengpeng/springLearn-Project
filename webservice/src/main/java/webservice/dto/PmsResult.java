package webservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/12 16:25
 */
@Data
public class PmsResult {

    private boolean success;

    private String code;

    private List<PmsAddTrackDataVo> reasonList;

    @Data
    public static class PmsAddTrackDataVo{

        private boolean success;
        /**
         * 调用结果描述
         */
        private String reason;
        /**
         * 返回物流单号
         */
        private String trackingNumber;
        /**
         * 返回操作日期
         */
        private Date operationDate;
    }

}
