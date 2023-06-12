package webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import webservice.dto.PmsStandardDto;

import javax.jws.WebService;

/**
 * @Description:
 * todo 自己搭建webservice服务进行注册
 * 目前使用其他客户的服务
 *
 * @Author 01415355
 * @Date 2023/6/12 15:31
 */
@Service
//@WebService(name = "MybBlog",  // 与接口中指定的name一致
//        targetNamespace = "http://pms.changan.com.cn", // 与接口中的命名空间一致,一般是接口的包名倒
//        endpointInterface = "com.webserver.myb.service.BlogService"// 接口地址
//)
public class PmsService {

    @Autowired
    private WebServiceTemplate template;


    public String pmsStandardLogisticsSyn(PmsStandardDto pmsStandardDto){
        return template.marshalSendAndReceive("http://183.66.200.161:12090/TESTPMS/service/pmsStandardLogisticsSyn?wsdl",
                pmsStandardDto ,new SoapActionCallback("")).toString();
    }


}
