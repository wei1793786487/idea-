package com.hqgml.utlis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.junit.Test;


/**
 * 返回值为0那么为发送成功
 */
@SuppressWarnings("all")
public class MsgUtils {
    //修改配置文件请修改 Msg.properties
    private static Properties properties;
    private static int appid;  // 短信应用SDK AppID
    private static String appkey;// 短信应用SDK AppKey
    private static int templateId_register;//注册的短信模板
    private static int templateId_notice;//通知的短信模板
    private static String smsSign;// NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
    private static String time;  //验证码失效时间

    //静态代码块加载配置文件
    static {
        try {
            properties = new Properties();
            properties.load(new InputStreamReader(MsgUtils.class.getClassLoader().getResourceAsStream("Msg.properties"), "GBK"));
            appid = Integer.parseInt(properties.getProperty("appid")); // 1400开头
            appkey = properties.getProperty("appkey");
            templateId_register = Integer.parseInt(properties.getProperty("templateId_register"));
            templateId_notice = Integer.parseInt(properties.getProperty("templateId_notice"));
            smsSign = properties.getProperty("smsSign");
            time = properties.getProperty("Failuretime");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int SetRegister(String phone,String vcode) {
        // 需要发送短信的手机号码
        String[] phoneNumbers = {phone};
        try {
            String[] params = {vcode, time};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0], templateId_register, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            return result.result;

        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return 1;
    }

    public static int SetNotice(String[] phones, String UserName, String MeetingName,String time, String Place) {
//        尊敬的{1},{2}会议将在{3}在{4}开始，请按时到场。如非本人操作，请忽略本短信。
        // 需要发送短信的手机号码
        String[] phoneNumbers = phones;
        try {
            String[] params = {UserName, MeetingName,time,Place};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0], templateId_notice, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
            return result.result;
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 生成6位随机数验证码
     *
     * @return
     */
    public static String vcode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }

    @Test
    public void deommsg() {

    }
}




