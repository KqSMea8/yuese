package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

import android.util.Log;


import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao1_01168;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Vliao2_01168;

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
public class HelpManager_01168C {
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
    
    
	    //解析用户资料
		public ArrayList<Vliao1_01168> vliao_zongjine(String json) {
			ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
			try {
				JSONArray jsonArray = new JSONArray(json);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					Vliao1_01168 bean = new Vliao1_01168();

					bean.setMoney(item.getInt("money"));
					bean.setIs_anchor(item.getInt("is_v"));
					list.add(bean);
					LogDetect.send(LogDetect.DataType.specialType,"HelpManager_01168：", list);
	
				}
		}catch(Exception e){
			e.printStackTrace();
		}
			return list;
	}
    
    
    
		//收入明细
		public ArrayList<Vliao2_01168> shourumingxi(String rev) {
			ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
			try {
				JSONArray jsonArray = new JSONArray(rev);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
	                Vliao2_01168 pay=new Vliao2_01168();
	                //pay.setMoney(item.getDouble("money"));
	                pay.setMoney(item.getString("money"));
	                LogDetect.send(LogDetect.DataType.basicType,"01162---钱",item.getString("money"));
	                pay.setTime(item.getString("time"));
	                pay.setType(item.getString("type"));
			        list.add(pay);}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
    //支出明细
		public ArrayList<Vliao2_01168> zhichumingxi(String rev) {
			ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
			try {
				JSONArray jsonArray = new JSONArray(rev);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
	                Vliao2_01168 pay=new Vliao2_01168();
	                pay.setNum(item.getString("num"));
	                pay.setTime(item.getString("time"));
	                pay.setType(item.getString("type"));
	                pay.setPhoto(item.getString("photo"));
			        list.add(pay);}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
    
		//提现明细
				public ArrayList<Vliao2_01168> tixianmingxi(String rev) {
					ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
					try {
						JSONArray jsonArray = new JSONArray(rev);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
			                Vliao2_01168 pay=new Vliao2_01168();
			                /*time:		提现时间
			                cash:		提现金额
			                status: 	提现状态*/
			                pay.setTime(item.getString("time"));
			               // pay.setCash(item.getDouble("cash"));
			                pay.setCash(item.getString("cash"));
			                pay.setStatus(item.getString("status"));
					        list.add(pay);}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return list;
				}
				//黑名单
				public ArrayList<Vliao1_01168> heimingdan(String rev) {
					ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
					try {
						JSONArray jsonArray = new JSONArray(rev);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							Vliao1_01168 pay=new Vliao1_01168();
							/*pay.setState(item.getString("state"));
							pay.setTime(item.getString("time"));
							pay.setPhoto(item.getString("photo"));
							pay.setUsername(item.getString("username"));*/
							//pay.setUsername(item.getString("username"));
							pay.setTarget_id(item.getInt("target_id"));
							pay.setNickname(item.getString("nickname"));
							pay.setPhoto(item.getString("photo"));
					        list.add(pay);}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return list;
				}
				
				public Page zhichuzhubo(String rev) {
					ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
					Page page = new Page();
					try {
						JSONArray jsonArray = new JSONArray(rev);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							//最后一条数据是用户余额
							if(i==jsonArray.length()-1){
								page.setTotlePage(item.getInt("totlePage"));		
							}else{
								Vliao2_01168 bean = new Vliao2_01168();
								bean.setPhoto(item.getString("photo"));
								//bean.setJinqian(item.getString("num"));
								bean.setType(item.getString("type"));
								bean.setNum(item.getString("num"));
								bean.setTime(item.getString("time"));
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
    
    
				public Page tixianzhubo(String rev) {
					ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
					Page page = new Page();
					try {
						JSONArray jsonArray = new JSONArray(rev);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							//最后一条数据是用户余额
							if(i==jsonArray.length()-1){
								page.setTotlePage(item.getInt("totlePage"));		
							}else{
								Vliao2_01168 bean = new Vliao2_01168();
								bean.setPhoto(item.getString("photo"));
								bean.setTime(item.getString("time"));
								//bean.setCash(item.getDouble("cash"));
								bean.setCash(item.getString("cash"));
								bean.setStatus(item.getString("status"));
								/*bean.setJinqian(item.getString("money"));
								bean.setType(item.getString("type"));
								*/
								
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
    
    
				public Page shouruzhubo(String rev) {
					ArrayList<Vliao2_01168> list = new ArrayList<Vliao2_01168>();
					Page page = new Page();
					try {
						JSONArray jsonArray = new JSONArray(rev);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							//最后一条数据是用户余额
							if(i==jsonArray.length()-1){
								page.setTotlePage(item.getInt("totlePage"));		
							}else{
								Vliao2_01168 bean = new Vliao2_01168();
								bean.setPhoto(item.getString("photo"));
								bean.setTime(item.getString("time"));
								bean.setType(item.getString("type"));
								bean.setMoney(item.getString("money"));
								list.add(bean);
							}
							page.setList(list);
						}
						page.setList(list);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						LogDetect.send(LogDetect.DataType.basicType,"01162---解析异常",e);
					}
					return page;
				}
    
    
				public ArrayList<Vliao1_01168> daili_num(String json) {
					ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
			  		try {
			  			JSONArray jsonArray = new JSONArray(json);
			  			for(int i=0;i<jsonArray.length();i++){
			  				JSONObject item = jsonArray.getJSONObject(i);
			  				Vliao1_01168 bean = new Vliao1_01168();
			  				bean.setId(item.getString("id"));
			  				bean.setNickname(item.getString("nickname"));				
			  				bean.setMoney(item.getInt("jine_num"));
			  				bean.setPhoto(item.getString("photo"));
			  				bean.setTixian_account(item.getString("tixian_account"));
			  				bean.setAccount_name(item.getString("account_name"));
			  				bean.setPeople_num(item.getString("people_num"));
			  				list.add(bean);
			  			}
			  		} catch (Exception e) {
			  			LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152个人资料修改:", "=============");
			  			// TODO: handle exception
			  		}
			  		
			  		LogDetect.send(LogDetect.DataType.specialType, "HelpManager_01152个人资料修改:", list);
			  		return list;
				}
    
				
				 public Page daili_mes(String rev) {
						ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
						Page page=new Page();
						try {
							JSONArray jsonArray = new JSONArray(rev);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								if(i==jsonArray.length()-1){
				                      page.setTotlePage(item.getInt("totlePage"));
				                      page.setPageNo(item.getInt("pagenum"));
				                      page.setCurrent(item.getInt("current"));
								}else {
									Vliao1_01168 pay=new Vliao1_01168();
									pay.setNickname(item.getString("nickname"));
									pay.setPhoto(item.getString("photo"));
									list.add(pay);
								}
						       // LogDetect.send(LogDetect.DataType.specialType, "Meijian_HelpManager_01168我的关注",list);
							}
							page.setList(list);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return page;
					}
    
    
				//黑名单
					public ArrayList<Vliao1_01168> tjrwithdraw(String rev) {
						ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
						try {
							JSONArray jsonArray = new JSONArray(rev);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								Vliao1_01168 pay=new Vliao1_01168();
								//ableinvite_price	推荐人提现页面的可提现金额
								//invite_price		剩余贡献值
								pay.setAbleinvite_price(item.getString("ableinvite_price"));
								pay.setInvite_price(item.getString("invite_price"));
						        list.add(pay);}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return list;
					}
					//shouruzhubo_tjr
					public Page shouruzhubo_tjr(String rev) {
						ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
						Page page = new Page();
						try {
							JSONArray jsonArray = new JSONArray(rev);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								//最后一条数据是用户余额
								if(i==jsonArray.length()-1){
									//page.setTotlePage(item.getInt("totlePage"));
									page.setAblenum(item.getDouble("totlePage"));
								}else{
									Vliao1_01168 bean = new Vliao1_01168();
									bean.setPhoto(item.getString("photo"));
									bean.setNickname(item.getString("nickname"));
									bean.setMoney_num(item.getString("money_num"));
									bean.setAble_money(item.getString("able_money"));
									bean.setUptime(item.getString("uptime"));
									bean.setId(item.getString("id"));
									list.add(bean);
								}
								page.setList(list);
							}
							page.setList(list);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							LogDetect.send(LogDetect.DataType.basicType,"01162---解析异常",e);
						}
						return page;
					}
    
    
		//fenxiao2_request			
					public Page fenxiao1_request(String rev) {
						ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
						Page page=new Page();
						try {
							JSONArray jsonArray = new JSONArray(rev);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								if(i==jsonArray.length()-1){
				                      page.setTotlePage(item.getInt("totlePage"));
				                      page.setPageNo(item.getInt("pagenum"));
				                      page.setCurrent(item.getInt("current"));
								}else {
									Vliao1_01168 pay=new Vliao1_01168();
									/*pay.setMoney(item.getString("money"));
					                pay.setTime(item.getString("time"));
					                pay.setType(item.getString("type"));*/
									pay.setNickname(item.getString("nickname"));
									pay.setPhoto(item.getString("photo"));
									list.add(pay);
								}
						       // LogDetect.send(LogDetect.DataType.specialType, "Meijian_HelpManager_01168我的关注",list);
							}
							page.setList(list);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return page;
					}		
					
					public ArrayList<Vliao1_01168> pepele_num(String json) {
						ArrayList<Vliao1_01168> list = new ArrayList<Vliao1_01168>();
						try {
							JSONArray jsonArray = new JSONArray(json);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								Vliao1_01168 bean = new Vliao1_01168();
								//bean.setQiandao_time(item.getString("qiandao_time"));
								//bean.setQinadao_num(item.getInt("qiandao_num"));
								bean.setPeople_num(item.getString("people1"));
								bean.setAccount_name(item.getString("people2"));
								list.add(bean);
								LogDetect.send(LogDetect.DataType.specialType,"HelpManager_01168：", list);
				
							}
					}catch(Exception e){
						e.printStackTrace();
					}
						return list;
				}			
					
					
					
					
}
