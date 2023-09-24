package com.sf.vsolution.hx.hanzt.template.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.exception.TemplateGenerateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: get/post请求工具类
 * @author: 朱传捷
 * @date: 2019-04-26 10:10
 */
@Slf4j
public class HttpClientUtil {

    /**
     * HttpClient 连接池
     */
    private static PoolingHttpClientConnectionManager cm = null;
    /**
     * 连接超时设置
     */
    private static  RequestConfig config = null;
    /**a
     * 连接超时时间
     */
    private static Integer CONNECT_TIME_OUT = 3 * 1000;
    /** 数据传输超时 */
    private static Integer SOCKET_OUT = 5 * 1000;
    /** 连接池获取连接的超时时间 */
    private final static int CONNECTION_REQUEST_TIMEOUT= 10000;

    static {
        // 初始化连接池，可用于请求HTTP/HTTPS（信任所有证书）
        cm = new PoolingHttpClientConnectionManager(getRegistry());
        // 整个连接池最大连接数
        cm.setMaxTotal(256);
        // 每路由最大连接数，默认值是2
        cm.setDefaultMaxPerRoute(10);
        //连接配置
        config = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_OUT)
                .build();
    }
    /**
     * 发送get请求
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, String> param) {
        log.info("GET请求地址:{},请求参数内容:{}",url, JSON.toJSON(param));
        // 创建Httpclient对象
        CloseableHttpClient httpclient = doHttp();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            //建议根据异常类型不同进行解
            if( e instanceof ConnectException){
                //捕获到超时异常的时候，做出对应的处理操作,抛出自定义异常
                log.error("远程服务:{}连接超时",url);
                throw new TemplateGenerateException( FileConstant.ERR_SERVER_CONNECT_OVERTIME);
            }else if(e instanceof SocketTimeoutException) {
                //捕获到相应异常的时候，做出对应的操作
                log.error("请求远程服务数据超时:{}", e);
                throw new TemplateGenerateException(FileConstant.ERR_SERVER_CONNECT_REQUESTDATA);
            }else {
                log.error("请求服务异常:{}",e);
                throw new TemplateGenerateException(FileConstant.ERR_SERVER);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
//                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("GET响应参数:{}",resultString);
        return resultString;
    }

    public static String doGet(String url, Map<String, String> param, Map<String,String> headerMap) {
        log.info("GET请求地址:{},请求参数内容:{},请求header:{}", url, JSON.toJSON(param),JSON.toJSON(headerMap));
        // 创建Httpclient对象
        CloseableHttpClient httpclient = doHttp();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if(headerMap != null){
                for(String headerName : headerMap.keySet()){
                    httpGet.setHeader(headerName,headerMap.get(headerName));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("请求服务异常:{}", e);
            throw new TemplateGenerateException(FileConstant.ERR_SERVER);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("GET响应:{}", resultString);
        return resultString;
    }

    private static <T> HttpEntity getFormEntity(T formParamter) throws UnsupportedEncodingException {
        List<BasicNameValuePair> form = new ArrayList<>();
        JSONObject.parseObject(JSONObject.toJSONString(formParamter)).forEach((k, v)->{
            form.add(new BasicNameValuePair(k, String.valueOf(v)));
        });
        HttpEntity entity = new UrlEncodedFormEntity(form,"utf-8");
        return entity;
    }


    public static CloseableHttpClient doHttp(){
        // 通过连接池获取连接对象
        CloseableHttpClient httpClient =
                HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).build();
        return httpClient;
    }
    /**
     * 获取 HTTPClient注册器
     * @return
     * @throws Exception
     */
    private static Registry<ConnectionSocketFactory> getRegistry() {
        Registry<ConnectionSocketFactory> registry = null;

        try {
            registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", new PlainConnectionSocketFactory()).register("https", getSSLFactory()).build();
        } catch (Exception e) {
            log.error("获取 HTTPClient注册器失败", e);
        }

        return registry;
    }

    /**
     * 获取HTTPS SSL连接工厂
     * <p>跳过证书校验，即信任所有证书</p>
     * @return
     * @throws Exception
     */
    private static SSLConnectionSocketFactory getSSLFactory() throws Exception {
        // 设置HTTPS SSL证书信息，跳过证书校验，即信任所有证书请求HTTPS
        SSLContextBuilder sslBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        });

        // 获取HTTPS SSL证书连接上下文
        SSLContext sslContext = sslBuilder.build();

        // 获取HTTPS连接工厂
        SSLConnectionSocketFactory sslCsf = new SSLConnectionSocketFactory(sslContext, new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1","TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

        return sslCsf;
    }

}
