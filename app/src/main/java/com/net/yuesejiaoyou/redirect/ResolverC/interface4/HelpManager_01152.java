package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;

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
public class HelpManager_01152 {
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
    //解析个人中M
	public ArrayList<Member_01152> Json_info(String json) {
		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
  		try {
  			JSONArray jsonArray = new JSONArray(json);
  			for(int i=0;i<jsonArray.length();i++){
  				JSONObject item = jsonArray.getJSONObject(i);
  				Member_01152 bean = new Member_01152();
  				bean.setId(item.getInt("id"));
  				bean.setNickname(item.getString("nickname"));				
  				bean.setMoney(item.getString("money"));
  				bean.setPhoto(item.getString("photo"));
  				bean.setTixian_account(item.getString("tixian_account"));
  				bean.setAccount_name(item.getString("account_name"));
  				bean.setPhonenum(item.getString("phonenum"));
  				bean.setXiaji_num(item.getInt("xiaji_num"));
  				bean.setInvite_price(item.getDouble("invite_price"));
				bean.setAbleinvite_price(item.getDouble("ableinvite_price"));
                bean.setCash_onefee(item.getString("cash_onefee"));
				bean.setCash_twofee(item.getString("cash_twofee"));
				bean.setDvcash_onefee(item.getString("dvcash_onefee"));
				bean.setDvcash_twofee(item.getString("dvcash_twofee"));
  				list.add(bean);
  			}
  		} catch (Exception e) {
  			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152个人资料修改:", "=============");
  			// TODO: handle exception
  		}
  		
  		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152个人资料修改:", list);
  		return list;
	}

	//解析主播个人中M
	/*public ArrayList<Member_01152> Json_zhuboinfo(String json) {
		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
  		try {
  			JSONArray jsonArray = new JSONArray(json);
  			for(int i=0;i<jsonArray.length();i++){
  				JSONObject item = jsonArray.getJSONObject(i);
  				Member_01152 bean = new Member_01152();
  				//bean.setId(item.getInt("id"));
  				bean.setNickname(item.getString("nickname"));
  				*//*bean.setMoney(item.getInt("money"));
  				bean.setFans_num(item.getInt("fans_num"));
  				bean.setPrice(item.getString("price"));
  				bean.setStar_ranking(item.getString("star_ranking"));*//*
  				bean.setPhoto(item.getString("photo"));
  				*//*bean.setNo_disturb(item.getString("no_disturb"));
  				bean.setDengji(item.getInt("dengji"));
  				bean.setInvite_num(item.getString("invite_num"));
					bean.setSum(item.getInt("sum"));
					bean.setMyrenzheng(item.getInt("myrenzheng"));*//*
  				list.add(bean);
  			}
  		} catch (Exception e) {
  			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", "=============");
  			// TODO: handle exception
  		}
  		
  		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
  		return list;
	}*/

/*
	public ArrayList<Member_01152> Json_zhuboinfo(String json) {
		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject item = jsonArray.getJSONObject(i);
				Member_01152 bean = new Member_01152();
				bean.setNickname(item.getString("nickname"));
				bean.setPhoto(item.getString("photo"));
				list.add(bean);
			}
		} catch (Exception e) {
			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", "=============");
			// TODO: handle exception
		}

		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
		return list;
	}*/

	public ArrayList<Member_01152> Json_zhuboinfo(String rev) {
		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
		try {
			JSONArray jsonArray = new JSONArray(rev);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Member_01152 bean=new Member_01152();
				LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", "=============");
				bean.setNickname(item.getString("nickname"));
				LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改昵称:", item.getString("nickname"));
				bean.setPhoto(item.getString("photo"));
				LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改头像:", item.getString("photo"));
				list.add(bean);}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
		return list;
	}










	public Page Json_mbinfo(String json) {
//		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
//  		try {
//  			JSONArray jsonArray = new JSONArray(json);
//  			for(int i=0;i<jsonArray.length();i++){
//  				JSONObject item = jsonArray.getJSONObject(i);
//  				Member_01152 bean = new Member_01152();
//				bean.setPhoto();
//  				//bean.setId(item.getInt("id"));
//  				//bean.setNicheng(item.getString("nicheng"));
//  				//bean.setIncome(item.getDouble("his_pay"));
//  				list.add(bean);
//  			}
//  		} catch (Exception e) {
//  			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", "=============");
//  			// TODO: handle exception
//  		}
//
//  		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
//  		return list;

		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
		Page page = new Page();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				if(i==jsonArray.length()-1){
					page.setTotlePage(item.getInt("totlePage"));
					page.setCurrent(item.getInt("pagenum"));
				}else{
					Member_01152 bean=new Member_01152();
					bean.setNickname(item.getString("uptime"));
					//bean.setWxnumber(item.getString("wxnumber"));
					//bean.setIsvip(item.getInt("isvip"));
					bean.setPrice(item.getString("money_num"));
					bean.setTixian_account(item.getString("able_money"));
					bean.setPhoto(item.getString("photo"));
					//bean.setGender(item.getString("gender"));
					//bean.setHeight(item.getString("height"));
					//bean.setIslike(item.getInt("islike"));
					//bean.setIsmatchmaker(item.getInt("ismatchmaker"));
					//bean.setIslike(0);
					list.add(bean);
				}
			}
			page.setList(list);
		} catch (Exception e) {
			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", "=============");
			// TODO: handle exception
		}

		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
		return page;
	}
	
	public Page Json_fo(String json) {
		ArrayList<Member_01152> list = new ArrayList<Member_01152>();
		Page page = new Page();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				if(i==jsonArray.length()-1){
					page.setTotlePage(item.getInt("totlePage"));
					page.setCurrent(item.getInt("pagenum"));
				}else{
					Member_01152 bean=new Member_01152();
					
					bean.setNickname(item.getString("nickname"));
					bean.setPhoto(item.getString("photo"));
					
					list.add(bean);
				}
			}
			page.setList(list);
		} catch (Exception e) {
			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152人数列表:", "=============");
			// TODO: handle exception
		}

		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152主播资料修改:", list);
		return page;
	}
}





































