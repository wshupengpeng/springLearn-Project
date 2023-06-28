package webservice.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import webservice.dto.PmsStandardDto;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * todo 自己搭建webservice服务进行注册
 * 目前使用其他客户的服务
 *
 * @Author 01415355
 * @Date 2023/6/12 15:31
 */
//@Service
//@WebService(name = "MybBlog",  // 与接口中指定的name一致
//        targetNamespace = "http://pms.changan.com.cn", // 与接口中的命名空间一致,一般是接口的包名倒
//        endpointInterface = "com.webserver.myb.service.BlogService"// 接口地址
//)
@Component
public class PmsService {

//    @Autowired
    private WebServiceTemplate template;


    public String pmsStandardLogisticsSyn(PmsStandardDto pmsStandardDto){
        return template.marshalSendAndReceive("http://183.66.200.161:12090/TESTPMS/service/pmsStandardLogisticsSyn?wsdl",
                pmsStandardDto ,new SoapActionCallback("")).toString();
    }

    public static void main(String[] args) throws PmsStandardLogisticsSynException_Exception {
        PmsStandardLogisticsSyn pmsStandardLogisticsSyn = new PmsStandardLogisticsSyn();
        TrackPO trackPO = new TrackPO();
        trackPO.setArrivalEstimatedTime(new Date());
        trackPO.setCartonNumber("12314");
        trackPO.setComeFrom("12314");
        trackPO.setDescr("12314");
        trackPO.setGoodsWeight("12314");
        trackPO.setGoodsVolume("12314");
        trackPO.setLatitude("12314");
        trackPO.setLongitude("12314");
        trackPO.setOperationInstructions("12314");
        trackPO.setStatus("12314");
        trackPO.setReceiptCity("12314");
        trackPO.setSendCity("12314");
        trackPO.setSender("12314");
        trackPO.setToStore("12314");
        trackPO.setOperatorName("12314");
        trackPO.setOperatorPhone("12314");
        trackPO.setOperationDate(new Date());
        trackPO.setReceiver("12314");
        trackPO.setWaybill("12314");
        trackPO.setTransportWay("12314");
        trackPO.setPutForwardWay("12314");
        trackPO.setProvincesCounties("12314");
//        trackPO.setWaybill("");
//        trackPO.setWaybill("");
//        trackPO.setWaybill("");
        PmsStandardLogisticsSynPortType pmsStandardLogisticsSynHttpSoap11Endpoint = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint();
        String key = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint().getKey(String.join(",", "WL001", "顺丰物流", System.currentTimeMillis() + ""));
        System.out.println("key:"+key);
        String request = JSONObject.toJSONString(new TrackPO());
        System.out.println(request);
        PmsAddTrackDataVo pmsAddTrackDataVo = pmsStandardLogisticsSyn
                .getPmsStandardLogisticsSynHttpSoap11Endpoint()
                .addTrackData("WL001", "顺丰物流", key, trackPO);


//        PmsAddTrackDataListVo pmsAddTrackDataListVo = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint()
//                .addTrackDataList("123", "123", "123", Arrays.asList(trackPO));
//        System.out.println(pmsAddTrackDataListVo);
    }


}
