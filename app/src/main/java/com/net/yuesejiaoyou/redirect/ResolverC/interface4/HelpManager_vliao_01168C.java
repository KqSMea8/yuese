package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.vliaofans_01168;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * json 转换其他对象
 *
 */
public class HelpManager_vliao_01168C {
	public static String user_id = null;
	
	public static String getRandom() {
		String string = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < 8; i++) {
	        sb.append(string.charAt(getRandom(len-1)));
	    }
	    return sb.toString();
	}
	private static int getRandom(int count) {
	    return (int) Math.round(Math.random() * (count));
	}
	
	
	public ArrayList<vliaofans_01168> wodefensi(String rev) {
		ArrayList<vliaofans_01168> list = new ArrayList<vliaofans_01168>();
		try {
			JSONArray jsonArray = new JSONArray(rev);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				vliaofans_01168 pay=new vliaofans_01168();
				//username, nickname, introduce, photo, fans_id, user_id;
				pay.setNickname(item.getString("nickname"));
				pay.setPhoto(item.getString("photo"));
				pay.setStatus(item.getString("user_state"));
				pay.setQinmvalue(item.getString("qinmvalue"));
				pay.setFans_id(item.getString("user_id"));
		        list.add(pay);
		        LogDetect.send(LogDetect.DataType.specialType, "Meijian_HelpManager_01168我的粉丝",list);}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @param @return
     * @return String
     * @throws
     * @Title: GetNetIp
     * @Description:
     */
    public static String getNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
//          infoUrl = new URL("http://ip168.com/");
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    strber.append(line + "\n");
                }
                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                httpConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Log.e("getNetIp", ipLine);
        return ipLine;
    }
}
