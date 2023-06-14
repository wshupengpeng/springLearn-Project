package webservice.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.transport.http.server.EndpointImpl;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import webservice.dto.PmsStandardDto;
import webservice.interceptor.InInterceptor;

import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;


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

        // 如何在cxf上添加拦截器进行xml报文的请求和响应日志打印
        System.out.println("webService start");
        PmsStandardLogisticsSyn pmsStandardLogisticsSyn = new PmsStandardLogisticsSyn();
        PmsStandardLogisticsSynPortType helloWorld = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint();
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(helloWorld);
//        client.getOutInterceptors().add(new InInterceptor()); // 添加自定义拦截器
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());
        helloWorld = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint();
        String key = helloWorld.getKey("1,2,3");
        System.out.println("webService end");

    }

//    public static void main(String[] args) throws PmsStandardLogisticsSynException_Exception {
//        PmsStandardLogisticsSyn pmsStandardLogisticsSyn = new PmsStandardLogisticsSyn();
//        TrackPO trackPO = new TrackPO();
////        trackPO.setArrivalEstimatedTime(new XMLGregorianCalendarImpl());
//        trackPO.setCartonNumber("123");
//        trackPO.setComeFrom("123");
//        trackPO.setDescr("123");
//        trackPO.setGoodsWeight("123");
//        trackPO.setGoodsVolume("123");
//        trackPO.setLatitude("123");
//        trackPO.setLongitude("123");
//        trackPO.setOperationInstructions("123");
//        trackPO.setStatus("123");
//        trackPO.setReceiptCity("123");
//        trackPO.setSendCity("123");
//        trackPO.setSender("123");
//        trackPO.setToStore("123");
//        trackPO.setOperatorName("123");
//        trackPO.setOperatorPhone("123");
////        trackPO.setOperationDate(new XMLGregorianCalendarImpl());
//        trackPO.setReceiver("123");
//        trackPO.setWaybill("123");
//        trackPO.setTransportWay("123");
//        trackPO.setPutForwardWay("123");
//        trackPO.setProvincesCounties("123");
//        trackPO.setGoodsNumber("123");
//        trackPO.setTrackingNumber("123");
//        trackPO.setPmsNumber("123");
////        trackPO.setWaybill("");
////        trackPO.setWaybill("");
////        trackPO.setWaybill("");
//        PmsStandardLogisticsSynPortType pmsStandardLogisticsSynHttpSoap11Endpoint = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint();
//        String key = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint().getKey(String.join(",", "顺丰物流", "WL001", System.currentTimeMillis() + ""));
//        System.out.println("key:"+key);
//        String request = JSONObject.toJSONString(new TrackPO());
//        System.out.println(request);
//
//        PmsAddTrackDataVo pmsAddTrackDataVo =pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint()
//                .addTrackData("顺丰物流","WL001" , key, trackPO);
//        System.out.println(pmsAddTrackDataVo);
////        PmsAddTrackDataListVo pmsAddTrackDataListVo = pmsStandardLogisticsSyn.getPmsStandardLogisticsSynHttpSoap11Endpoint()
////                .addTrackDataList("123", "123", "123", Arrays.asList(trackPO));
////        System.out.println(pmsAddTrackDataListVo);
//    }


}
