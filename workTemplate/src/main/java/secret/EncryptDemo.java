package secret;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;


/**
 * 测试java加密算法
 */
public class EncryptDemo {
    private static final String HEX_CHARACTERS = "0123456789ABCDEF";
    @Test
    public void sign() throws GeneralSecurityException, UnsupportedEncodingException {
        String app_secret = "5f3e43419b0b4122819a4ddfd52e3c01";
        String access_token = "27e34363bb4c43f49974325d54ef8d1f";
        String app_key = "08ac21e7e75949f9a1455f034f85e54d";
        String param_json = "[\n" +
                "    {\n" +
                "        \"deliveryType\": 17,\n" +
                "        \"deptNo\": \"EBU4418046650693\",\n" +
                "        \"expressItemName\": \"药品\",\n" +
                "        \"expressItemQty\": 1,\n" +
                "        \"fcFlag\": 0,\n" +
                "        \"orderNo\": \"XSD202112130069981\",\n" +
                "        \"pickupBeginTime\": \"2022-09-13 16:42:50\",\n" +
                "        \"guaranteeValue\": 1000,\n" +
                "        \"pickupEndTime\": \"2022-12-13 23:59:59\",\n" +
                "        \"receiptFlag\": 0,\n" +
                "        \"receiverAddress\": \"浙江省金华市义乌市福田街道工人北路\",\n" +
                "        \"receiverMobile\": \"1311111111\",\n" +
                "        \"receiverName\": \"李思测试数据\",\n" +
                "        \"senderAddress\": \"湖北省武汉市东西湖区走马岭兴工路\",\n" +
                "        \"senderCompany\": \"武汉配送组\",\n" +
                "        \"senderMobile\": \"13711111111\",\n" +
                "        \"senderName\": \"小雨\",\n" +
                "        \"siteCollect\": 1,\n" +
                "        \"siteDelivery\": 1,\n" +
                "        \"temptureNum\": 10\n" +
                "    },\n" +
                "    \"\"\n" +
                "]";
//        param_json = JSONObject.parseArray(param_json).toString();
        JSON.DEFAULT_PARSER_FEATURE = Feature.config(JSON.DEFAULT_PARSER_FEATURE, Feature.OrderedField, true);
        param_json = JSONObject.parseArray(param_json).toJSONString();
        System.out.println("param:" + param_json);
        String timestamp = "2022-09-09 14:17:23";
        String v = "2.0";
        String method = "/eclpopenservice/createwborder";
        String content = app_secret + "access_token" + access_token + "app_key"
                + app_key + "method" + method + "param_json" + param_json + "timestamp" + timestamp + "v" + v + app_secret;
        System.out.println("content:" + content);
        String sign = sign("md5-salt",content.getBytes(StandardCharsets.UTF_8),app_secret.getBytes(StandardCharsets.UTF_8));
        System.out.println("sign:" + sign);
        String baseUri = "https://uat-api.jdl.com/eclpopenservice/createwborder?";
        StringBuilder param = new StringBuilder();
        param.append("app_key").append("=").append(URLEncoder.encode(app_key,"utf-8")).append("&")
                .append("v").append("=").append(v).append("&")
                .append("LOP-DN").append("=").append("JDLFSIX").append("&")
                .append("access_token").append("=").append(access_token).append("&")
                .append("timestamp").append("=").append(URLEncoder.encode(timestamp,"utf-8")).append("&")
                .append("sign").append("=").append(sign);
        String url = baseUri + param.toString();
        System.out.println("url:" + url);
    }

    private  String sign(String algorithm, byte[] data, byte[] secret) throws GeneralSecurityException {
        if (Objects.equals(algorithm, "md5-salt")) {
            return bytesToHex(MessageDigest.getInstance("md5").digest(data));
        } else if (Objects.equals(algorithm, "HMacMD5")) {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret, algorithm));
            return Base64.getEncoder().encodeToString(mac.doFinal(data));
        } else if (Objects.equals(algorithm, "HMacSHA1")) {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret, algorithm));
            return Base64.getEncoder().encodeToString(mac.doFinal(data));
        } else if (Objects.equals(algorithm, "HMacSHA256")) {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret, algorithm));
            return Base64.getEncoder().encodeToString(mac.doFinal(data));
        } else if (Objects.equals(algorithm, "HMacSHA512")) {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret, algorithm));
            return Base64.getEncoder().encodeToString(mac.doFinal(data));
        }
        throw new GeneralSecurityException("Algorithm " + algorithm + " not supported yet");
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            stringBuilder.append(HEX_CHARACTERS.charAt((b >>> 4) & 0x0F));
            stringBuilder.append(HEX_CHARACTERS.charAt(b & 0x0F));
        }
        return stringBuilder.toString();
    }

}
