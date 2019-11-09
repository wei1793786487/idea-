package com.hqgml.utlis;

import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("all")
public class Timeutils {


    //获取格式化字符串
    public static String Gettime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatdate = sdf.format(date);
        return formatdate;
    }

    //通过yyyy-MM-dd HH:mm:ss字符串获取标准时间
    public static Date GetStandardtime(String date) throws ParseException {
//        2019/11/11 - 08:40
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
        Date formatdate = sdf.parse(date);
        return formatdate;
    }

    //获取标准时间
    public static String Getdailytime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分");
        String formatdate = sdf.format(date);
        return formatdate;

    }

    //获取会议通知时间
    public static String GetNoticetime(String date) {
        try {
            return Getdailytime(GetStandardtime(date));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 判断时间是不是在该区间之内
     *
     * @param nowDate
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static boolean hourMinuteBetween(String startDate, String endDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd - HH:mm");

        Date now = new Date();
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);

        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime >= startTime && nowTime <= endTime;
    }


    @Test
    public void demo() throws Exception {
//        String gettime = Gettime(new Date());
////        System.out.println(gettime);
////        Date date = GetStandardtime(gettime);
////        System.out.println(date);
////        String s = GetNoticetime(gettime);
////        System.out.println(s);
        String start = "2019/11/09 - 00:00";
        String end = "2019/11/09 - 09:";
        boolean b = hourMinuteBetween(start, end);

        System.out.println(b);
    }


}

