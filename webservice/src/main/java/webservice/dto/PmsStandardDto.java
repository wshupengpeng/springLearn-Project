package webservice.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/12 15:59
 */
@Data
public class PmsStandardDto {

    /**
     * PMS物流承运商
     */
    private String logisticsCarriers;

    /**
     * PMS登录用户名
     */
    private String loginUserName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 物流数据
     */
    private TrackPO trackPO;

    @Data
    public static class TrackPO{
        /**
         *  物流单号
         */
        private String trackingNumber;
        /**
         * 出发单位/库房
         */
        private String comeFrom;
        /**
         *  承运方式
         */
        private String transportWay;
        /**
         * 寄件人
         */
        private String sender;
        /**
         * 寄件城市
         */
        private String sendCity;
        /**
         * 收件城市
         */
        private String receiptCity;
        /**
         * 收件人
         */
        private String receiver;
        /**
         * 预计到达时间
         */
        private String arrivalEstimatedTime;
        /**
         * 到达网店
         */
        private String toStore;
        /**
         * 货物件数
         */
        private String goodsNumber;
        /**
         * 货物重量
         */
        private String goodsWeight;
        /**
         * 货物体积
         */
        private String goodsVolume;
        /**
         * 关联运单
         */
        private String waybill;
        /**
         * 提出方式
         */
        private String putForwardWay;
        /**
         * pms单号
         */
        private String pmsNumber;
        /**
         * 箱号
         */
        private String cartonNumber;
        /**
         * 一级状态（已接单、运输中、派送中、已签收）
         */
        private String status;
        /**
         * 二级状态（操作说明）
         */
        private String operationInstructions;
        /**
         * 操作日期
         */
        private Date operationDate;
        /**
         * 操作经度
         */
        private String longitude;
        /**
         * 操作维度
         */
        private String latitude;
        /**
         * 所属省市县
         */
        private String provincesCounties;
        /**
         * 操作人姓名
         */
        private String operatorName;
        /**
         * 操作人电话
         */
        private String operatorPhone;
        /**
         * 备注说明
         */
        private String descr;

    }
}
