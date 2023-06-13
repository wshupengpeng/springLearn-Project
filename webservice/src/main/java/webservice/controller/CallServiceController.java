package webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webservice.dto.PmsStandardDto;
import webservice.service.PmsService;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/12 15:21
 */
@RestController
@RequestMapping("/call/webservice")
public class CallServiceController {

    @Autowired
    private PmsService pmsService;

    @RequestMapping("/callWebService")
    public String callWebService() {
        PmsStandardDto pmsStandardDto = new PmsStandardDto();
        return pmsService.pmsStandardLogisticsSyn(pmsStandardDto);
    }


    @RequestMapping("/testMethod")
    public String testMethod(String startTime,String endTime) {
        return startTime + " :" + endTime;
    }
}
