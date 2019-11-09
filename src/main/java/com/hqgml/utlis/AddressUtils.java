package com.hqgml.utlis;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@SuppressWarnings("all")
public class AddressUtils {

    private static String ip_json;
    private static Properties properties;
    private static String ak;

    //静态代码块加载配置文件
    static {
        try {
            properties = new Properties();
            properties.load(new InputStreamReader(MsgUtils.class.getClassLoader().getResourceAsStream("baidu.properties"), "GBK"));
            ak = properties.getProperty("ak");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String GetAddress(HttpServletRequest request) {

        //透过代理获取ip地址
        String ipAddress = IpUtil.getIpAddress(request);
        //System.out.println(ipAddress);
        //如果是本机地址，那么就为空实现,空的话 那么百度会默认使用访问地址
        if (ipAddress.equals("0:0:0:0:0:0:0:1") || ipAddress.equals("127.0.0.1")) {
            ipAddress = "";
        }
        //将ip地址发送至百度地图接口获取json
        try {
            ip_json = HttpClientHelper.sendPost("http://api.map.baidu.com/location/ip?ip=" + ipAddress + "&ak="+ ak);
        } catch (IOException e) {
            System.out.println("地址参数获取异常");
        }
        //解析json获取物理地址
        if (ip_json != null) {
            String address = JsonUtlis.getAddress(ip_json);
            return address;
        } else {
            return null;
        }


    }
}
