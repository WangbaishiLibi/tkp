package com.dxt.gaotie.cloud.tkp.util;

//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by admin on 2018/6/26.
 */
public class StringUtil {

    public static final String salt = "skp";

    public static String decodeUrl(String str){
        return decodeUrl(str, "UTF-8");
    }
    public static String decodeUrl(String str, String format){
        try {
            str = URLDecoder.decode(str, format);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }
    public static String getCurrentDateTime(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String formatDate(Date date, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date formatDate(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatDateTime(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Time formatTime(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            return new Time(formatter.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateCalculate(Date date, Integer days){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DATE, days);
        return rightNow.getTime();
    }

//    public static String encodePassword(String rawPassword){
//        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//        return encoder.encodePassword(rawPassword, salt);
//    }

    public static boolean hasField(String clsName, String fieldName){
        Class cls = null;
        try {
            cls = Class.forName("com.llx.entity." + clsName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(cls == null)		return false;
        for(Field field : cls.getDeclaredFields()){
            if(field.getName().equals(fieldName))	return true;
        }
        return false;
    }

    //对象序列化为字符串
    public static String objectSerialiable(Object obj){
        String serStr = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");

            objectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return serStr;
    }

    //字符串反序列化为对象
    public static Object objectDeserialization(String serStr){
        Object newObj = null;
        try {
            String redStr = URLDecoder.decode(serStr, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            newObj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newObj;
    }

    //返回token的第三组字符作为redis key
    public static String tokenKey(String token){
        String []tmp = token.split("\\.");
        return tmp[tmp.length-1];
    }


    public static String randomChars(Integer len){
        final String source = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer num = new StringBuffer();
        Random rd = new Random();
        for(int i=0; i<len; i++){
            num.append(source.charAt(rd.nextInt(source.length())));
        }
        return num.toString();
    }


}
