package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;

/*import com.example.vliao.getset.Bills_01165;
import com.example.vliao.getset.Page;*/

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Bills_01165;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;

import org.json.JSONArray;
import org.json.JSONException;
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
public class HelpManager_01165C {
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
//用户预约列表查询	
	public ArrayList<Bills_01165> yuyue_search(String json){


		ArrayList<Bills_01165> list = new ArrayList<Bills_01165>();
		try {
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Bills_01165 bean = new Bills_01165();
				bean.setTouxiang(item.getString("photo"));
				bean.setNickname(item.getString("nickname"));
				bean.setOnline(item.getString("user_state"));
				bean.setTime(item.getString("appoint_time"));
				bean.setBooking_status(item.getString("isreturn"));
				bean.setIntimacy(item.getString("close_num"));
				bean.setUser_id(item.getString("dv_id"));
				
				
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
			LogDetect.send(LogDetect.DataType.specialType, "捕获: ", e);
		}

		return list;
	}
	
	
	
	//我的V币
	public ArrayList<Bills_01165> my_v_search(String json) {

		ArrayList<Bills_01165> list = new ArrayList<Bills_01165>();
		try {
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Bills_01165 bean = new Bills_01165();
				
				
				bean.setTime(item.getString("time"));
				bean.setPay(item.getString("num"));
				bean.setNickname(item.getString("nickname"));
				bean.setTouxiang(item.getString("photo"));
				bean.setIs_anchor(item.getString("is_anchor"));
				//主播收入---money
				bean.setMoney(item.getString("money"));
				bean.setType(item.getString("type"));	//支付	



								
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;
	}
	//我的评价
	public ArrayList<Bills_01165> my_pingjia_search(String json) {
		// TODO Auto-generated method stub

		ArrayList<Bills_01165> list = new ArrayList<Bills_01165>();
		try {
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Bills_01165 bean = new Bills_01165();
				//bean.setTime(item.getString("Time"));	//时间
				bean.setNickname(item.getString("nickname"));//昵称
				bean.setTouxiang(item.getString("photo"));//头像
				bean.setBooking_status(item.getString("Status"));//通话时长
				bean.setTime(item.getString("pingjia_time"));//评价时间
				//is_like
				bean.setType(item.getString("is_like"));//是否喜欢
				//标签-------
				bean.setLabel(item.getString("pl_value"));//标签
				
				//
				list.add(bean);
			}
		}catch(Exception e){
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
	
	
	public Page level_search(String json) {
		LogDetect.send(LogDetect.DataType.specialType, "gerenzhongxin_01165:json:", json);
		ArrayList<Bills_01165> list = new ArrayList<>();
		Page page = new Page();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				//最后一条数据是返回的抓中次数
				if(i==jsonArray.length()-1){
					page.setTotlePage(item.getInt("totlePage"));
				}
				else{
					Bills_01165 bean = new Bills_01165();
					//bean.setPhoto(item.getString("baby_photo"));	//娃娃图片
					bean.setTouxiang(item.getString("photo"));
					bean.setLevel(item.getString("dengji"));
					list.add(bean);
				}
			}
			page.setList(list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogDetect.send(LogDetect.DataType.specialType, "e==========:",e);
			e.printStackTrace();
		}
		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_1165_list:",list);
		return page;
	}
	
}

