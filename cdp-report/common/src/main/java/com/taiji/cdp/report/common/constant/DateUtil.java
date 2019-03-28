package com.taiji.cdp.report.common.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 日期格式转换的工具类
 * */
public class DateUtil {
    public static String dateD="yyyy-MM-dd";//精度到日
    public static String dateM="yyyy-MM-dd HH:mm:ss";//精度到秒
    /*
     *精确到秒的日期格式转换为字符串
     */
    public static String dateM2String(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateM);
        String str=sdf.format(date);
        return str;
    }
    /*
	 *精确到日的日期格式转换为字符串
	 */
    public static String dateD2String(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateD);
        String str=sdf.format(date);
        return str;
    }
    /*
	 *字符串转换为精确到秒的日期格式
	 */
    public static Date string2DateM(String dstr){
        if(dstr==null||dstr.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateM);
        Date date=null;
        try {
            date = sdf.parse(dstr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    /*
	 *字符串转换为精确到日的日期格式
	 */
    public static Date string2DateD(String dstr){
        if(dstr==null||dstr.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateD);
        Date date=null;
        try {
            date = sdf.parse(dstr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
