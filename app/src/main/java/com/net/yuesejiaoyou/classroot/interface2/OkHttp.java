package com.net.yuesejiaoyou.classroot.interface2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.net.yuesejiaoyou.classroot.core.YhApplicationA;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverB.interface4.im.IMManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OkHttp {
	public OkHttpClient mOkHttpClient;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	public String  BASE_URL= Util.url+"/uiface";

	
	public OkHttp() {
		if (mOkHttpClient == null) {
			mOkHttpClient = new OkHttpClient();
		}
	}
public void postAsynHttp(String url, String json) {
		
		RequestBody formBody = new FormBody.Builder().add("json", json).build();
		//RequestBody formBody = RequestBody.create(JSON, json);
		Request request = addHeaders().url("http://120.27.98.128:9000/uiface").post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				/*String str = arg1.body().string();
				if(!str.equals("")){
					String[] ids=str.split("@");
					MainActivity.sqlserverDao.updateSqlserver(ids);
				}
				*/
			}
			
			
		});

}

public String requestPostBySyn(String actionUrl, String[] paramsMap) {
	//---------------------------------------------------------------------
	Log.v("TTT","requestPostBySyn");
	if("0".equals(Util.userid) && !actionUrl.contains("login") && !actionUrl.contains("wxlogin")) {
		synchronized (OkHttp.class) {
			if("0".equals(Util.userid)) {
				LogDetect.setDataType(LogDetect.DataType.exceptionType);
				LogDetect.send("01107", ">>>>>>>>>> 检测到Util.userid==0 "+actionUrl+" <<<<<<<<<<");

				SharedPreferences share = YhApplicationA.getApplication().getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);

				// 尝试从sharepreferences获取userid
				String userid = share.getString("userid", "");
				LogDetect.send("01107", ">>>>>>>>>> (1) userid=" + userid + " <<<<<<<<<<");
				if (!userid.isEmpty()) {
					Util.userid = userid;
					Util.nickname = share.getString("nickname", "");
					Util.headpic = share.getString("headpic", "");
					Util.iszhubo = share.getString("zhubo_bk", "").equals("0") ? "0" : "1";
					LogDetect.send("01107", ">>>>>>>>>> (2) nickname=" + Util.nickname + ",headpic=" + Util.headpic + ",iszhubo=" + Util.iszhubo + ",imManager="+Util.imManager+",IMManager.getInstance()="+IMManager.getInstance()+" <<<<<<<<<<");
					if(Util.imManager == null) {
						LogDetect.send("01107", ">>>>>>>>>> (7) Util.imManager已经丢失 <<<<<<<<<<");

						if(IMManager.getInstance() == null) {
							LogDetect.send("01107", ">>>>>>>>>> (10) IMManager.getInstance()已经丢失,重新连接一下 <<<<<<<<<<");
							try {
								JSONObject jsonObj = new JSONObject("{\"id\":\""+Util.userid+"\",\"nickname\":\""+Util.nickname+"\",\"photo\":\""+Util.headpic+"\"}");
								Util.imManager = new IMManager();
								Util.imManager.initIMManager(jsonObj, YhApplicationA.getApplication());
							} catch (JSONException e) {
								LogDetect.send("01107", ">>>>>>>>>> (9) IMManager.initIMManager JSONException:"+e+" <<<<<<<<<<");
								e.printStackTrace();
							}
						} else {
							LogDetect.send("01107", ">>>>>>>>>> (11) IMManager.getInstance()给Util.imManager重新赋值一下 <<<<<<<<<<");
							Util.imManager = IMManager.getInstance();
							Util.imManager.checkConnection();
						}
					} else {
						LogDetect.send("01107", ">>>>>>>>>> (8) Util.imManager尚未丢失，checkConnection <<<<<<<<<<");
						Util.imManager.checkConnection();
					}
					JPushInterface.setAlias(YhApplicationA.getApplication(), 1, Util.userid);    // 设置极光别名
				} else {
					String username = share.getString("username", "");
					String password = share.getString("password", "");

					LogDetect.send("01107", ">>>>>>>>>> (3) username=" + username + ",password=" + password + " <<<<<<<<<<");
					OkHttpClient client = new OkHttpClient();
					Request request = addHeaders().url(Util.url + "/uiface/ar?p0=A-user-search&p1=login&p2=1&p3=" + username + "&p4=" + password).get().build();
					Call call = client.newCall(request);
					try {
						Response response = call.execute();
						int code = response.code();
						if (code == 200) {
							String json = response.body().string();
							LogDetect.send("01107", ">>>>>>>>>> (4) json=" + json + " <<<<<<<<<<");
							JSONArray jsonArray = new JSONArray(json);
							//JSONObject jsonObject = new JSONObject(json);
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							String a = jsonObject.getString("id");
							if (!a.equals("")) {
								String name = jsonObject.getString("nickname");
								String headpic = jsonObject.getString("photo");
								String gender = jsonObject.getString("gender");
								String zhubo = jsonObject.getString("is_v");
								String zh = jsonObject.getString("username");
								String pwd = jsonObject.getString("password");
								String invite_num = jsonObject.getString("invite_num");
								//share = getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
								share.edit().putString("logintype", "phonenum").commit();
								share.edit().putString("username", zh).commit();
								share.edit().putString("password", pwd).commit();
								share.edit().putString("userid", a).commit();
								share.edit().putString("nickname", name).commit();
								share.edit().putString("headpic", headpic).commit();
								share.edit().putString("sex", gender).commit();
								share.edit().putString("zhubo_bk", zhubo).commit();
								Util.userid = a;
								Util.headpic = headpic;
								Util.nickname = name;
								Util.is_agent = jsonObject.getString("is_agent");
								Util.iszhubo = zhubo.equals("0") ? "0" : "1";
								Util.invite_num = invite_num;

								if(Util.imManager == null) {
									LogDetect.send("01107", ">>>>>>>>>> (00) Util.imManager已经丢失 <<<<<<<<<<");

									if(IMManager.getInstance() == null) {
										LogDetect.send("01107", ">>>>>>>>>> (11) IMManager.getInstance()已经丢失,重新连接一下 <<<<<<<<<<");
										try {
											JSONObject jsonObj = new JSONObject("{\"id\":\""+Util.userid+"\",\"nickname\":\""+Util.nickname+"\",\"photo\":\""+Util.headpic+"\"}");
											Util.imManager = new IMManager();
											Util.imManager.initIMManager(jsonObj, YhApplicationA.getApplication());
										} catch (JSONException e) {
											LogDetect.send("01107", ">>>>>>>>>> (22) IMManager.initIMManager JSONException:"+e+" <<<<<<<<<<");
											e.printStackTrace();
										}
									} else {
										LogDetect.send("01107", ">>>>>>>>>> (33) IMManager.getInstance()给Util.imManager重新赋值一下 <<<<<<<<<<");
										Util.imManager = IMManager.getInstance();
										Util.imManager.checkConnection();
									}
								} else {
									LogDetect.send("01107", ">>>>>>>>>> (44) Util.imManager尚未丢失，checkConnection <<<<<<<<<<");
									Util.imManager.checkConnection();
								}

								JPushInterface.setAlias(YhApplicationA.getApplication(), 1, Util.userid);    // 设置极光别名
							}
						}
					} catch (IOException e) {
						LogDetect.send("01107", ">>>>>>>>>> (5) IOException=" + e + " <<<<<<<<<<");
						e.printStackTrace();
					} catch (JSONException e) {
						LogDetect.send("01107", ">>>>>>>>>> (6) JSONException=" + e + " <<<<<<<<<<");
						e.printStackTrace();
					}
				}
				LogDetect.setDataType(LogDetect.DataType.noType);
			}
		}
	}
	//---------------------------------------------------------------------
	String json="";
    try {
        //创建一个FormBody.Builder
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userid",Util.userid);
		///////////////////////////-------------->>>>>
		LogDetect.send(LogDetect.DataType.specialType, "okhttp第一个参数userid:",Util.userid);
		///////////////////////////-------------->>>>>
		////////后补代码
		Log.v("TTT","paramsMap.length: "+paramsMap.length);
		for(int i = 0; i < paramsMap.length; i++) {
			Log.v("TTT","paramsMap["+i+"]: "+paramsMap[i]);
		}
		if (paramsMap.length!=1) {
			for (int i = 1; i < paramsMap.length; i++) {
				builder.add("param" + i, paramsMap[i]);
				///////////////////////////-------------->>>>>
				LogDetect.send(LogDetect.DataType.specialType, "okhttp参数多个:", paramsMap[i] + "----序号是" + i);
				///////////////////////////-------------->>>>>
			}
		}else {
			///////////////////////////-------------->>>>>
			LogDetect.send(LogDetect.DataType.specialType, "okhttp参数只有一个:", paramsMap[0]);
			///////////////////////////-----
		}
        //生成表单实体对象
        RequestBody formBody = builder.build();
        //补全请求地址
        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
        //创建一个请求
		///////////////////////////-------------->>>>>
		LogDetect.send(LogDetect.DataType.specialType, "okhttp全的连接:",requestUrl+"-------"+formBody.toString());
		Log.v("TTT","requestPostBySyn()-requestUrl: "+requestUrl);
		///////////////////////////-------------->>>>>
        final Request request = addHeaders().url(requestUrl).post(formBody).build();
        //final Request request = new Request.Builder().url(requestUrl).build();
        //创建一个Call
        final Call call = mOkHttpClient.newCall(request);
        //执行请求
        Response response = call.execute();
        if (response.isSuccessful()) {
        	
        	json = response.body().string();
			Log.v("TTT","requestPostBySyn()-json: "+json);
        }else {
			Log.v("TTT","requestPostBySyn()-notSuccess: ");
        }
    } catch (Exception e) {
		Log.v("TTT","requestPostBySyn()-exception: "+getStackInfo(e));
    }
    return json;
}

	private String getStackInfo(Throwable e) {
		String info;
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		info = writer.toString();
		pw.close();
		try {
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return info;
	}

private Request.Builder addHeaders(){
	Request.Builder builder = new Request.Builder()
					.addHeader("Connection", "close");
					//.addHeader("userid",Util.userid);
	//.addHeader("userid","22");
	
	return builder;
	
}






}
