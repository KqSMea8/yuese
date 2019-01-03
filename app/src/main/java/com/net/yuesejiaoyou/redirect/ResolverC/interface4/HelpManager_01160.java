package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;



import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.ZBYuyueJB_01160;

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
public class HelpManager_01160 {
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
	public ArrayList<ZBYuyueJB_01160> zhubo_yuyue(String json) {
		  ArrayList<ZBYuyueJB_01160> list = new ArrayList<ZBYuyueJB_01160>();
          try {
              JSONArray jsonArray = new JSONArray(json);
              for (int i = 0; i < jsonArray.length(); i++) {
                  JSONObject item = jsonArray.getJSONObject(i);
                  ZBYuyueJB_01160 bean=new ZBYuyueJB_01160();
                  bean.setUser_id(item.getString("id"));
                  bean.setIntimacy(item.getString("qinmi"));
                  LogDetect.send(LogDetect.DataType.specialType, "亲密度",bean.getIntimacy().toString());
                 // bean.setIs_chaoshi(item.getString("is_chaoshi"));
                  bean.setIs_online(item.getString("online"));
                  bean.setName(item.getString("nickname"));
                  bean.setPhoto(item.getString("photo"));
                  bean.setTime(item.getString("appoint_time"));
                  list.add(bean);
              }
              // session.page=page;
          } catch (Exception e) {
              // TODO: handle exception
              e.printStackTrace();
              LogDetect.send(LogDetect.DataType.specialType, "this0",e);
          }

		return list;
	}

    public ArrayList<ZBYuyueJB_01160> search_dv(String json) {
        ArrayList<ZBYuyueJB_01160> list = new ArrayList<ZBYuyueJB_01160>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                ZBYuyueJB_01160 bean=new ZBYuyueJB_01160();
                bean.setUser_id(item.getString("id"));
                bean.setName(item.getString("nickname"));
                bean.setPhoto(item.getString("photo"));
                bean.setIntimacy(item.getString("signature"));
                list.add(bean);
            }
            // session.page=page;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this1",e);
        }

        return list;
    }

    public Page attention_videolist(String json) {

            ArrayList<Videoinfo> list = new ArrayList<Videoinfo>();
            Page page = new Page();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    if(i==jsonArray.length()-1){
                        page.setTotlePage(item.getInt("totlePage"));
                        page.setCurrent(item.getInt("pagenum"));
                    }else{
                        Videoinfo bean=new Videoinfo();
                        bean.setNickname(item.getString("nickname"));
                        bean.setId(item.getInt("id"));
                        bean.setIspay(item.getInt("ispay"));
                        bean.setIszan(item.getInt("iszan"));
                        bean.setUserid(item.getString("user_id"));
                        bean.setStatus(item.getInt("status"));
                        bean.setLike_num(item.getString("like_num"));
                        bean.setPrice(item.getString("price"));
                        bean.setVideo_id(item.getString("video_id"));
                        bean.setVideo_photo(item.getString("video_photo"));
                        bean.setExplain(item.getString("explain"));
                        bean.setPhoto(item.getString("photo"));
                        list.add(bean);
                    }
                }
                page.setList(list);
                // session.page=page;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                LogDetect.send(LogDetect.DataType.specialType, "this0",e);
            }

            return page;
        }

}
