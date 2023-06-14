package webservice.interceptor;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/14 14:12
 */
public class InInterceptor extends AbstractPhaseInterceptor<Message> {


    public InInterceptor() {
        super(Phase.PRE_INVOKE);  // 在调用方法之前调用自定拦截器
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            try {
                String str = IOUtils.toString(is);
                // 原请求报文
                System.out.println("====> request xml=\r\n" + str);

                InputStream ism = new ByteArrayInputStream(str.getBytes());
                message.setContent(InputStream.class, ism);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
