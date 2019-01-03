package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;



import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Phone_01162;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Tag;

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
 * json 转换其他对象
 */
public class HelpManager_01162C {
	public static String user_id = null;

	public static String getRandom() {
		String string = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		int len = string.length();
		for (int i = 0; i < 8; i++) {
			sb.append(string.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}

	private static int getRandom(int count) {
		return (int) Math.round(Math.random() * (count));
	}

	public Page my_phone_record(String rev) {
		ArrayList<Phone_01162> list = new ArrayList<Phone_01162>();
		Page page = new Page();
		try {
			JSONArray jsonArray = new JSONArray(rev);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				//最后一条数据是用户余额
				if (i == jsonArray.length() - 1) {
					page.setTotlePage(item.getInt("totlePage"));
					page.setPageNo(item.getInt("pagenum"));
				} else {
					Phone_01162 bean = new Phone_01162();
					bean.setVideo_id(item.getInt("Video_Id"));
					bean.setStatus(item.getString("Status"));
					bean.setTime(item.getString("Time"));
					bean.setIsdel(item.getString("Is_del"));
					bean.setUser_id(item.getInt("User_id"));
					bean.setPhoto(item.getString("photo"));
					bean.setNickname(item.getString("nickname"));
					bean.setPrice(item.getString("price"));
					bean.setNum(item.getString("num"));
					bean.setTtime(item.getString("ttime"));
					bean.setZhuboid(item.getInt("Zhubo_Id"));
					list.add(bean);
				}
				page.setList(list);
			}
			page.setList(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return page;
	}






















	public ArrayList<Tag> my_impression(String rev) {
		ArrayList<Tag> list = new ArrayList<Tag>();
		try {
			JSONArray jsonArray = new JSONArray(rev);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Tag bean = new Tag();
				bean.setColor(item.getString("labcolor"));
				bean.setCount(item.getString("count"));
				bean.setRecord(item.getString("lab_name"));
				list.add(bean);
			}
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
				while ((line = reader.readLine()) != null) {
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
