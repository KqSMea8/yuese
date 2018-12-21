package com.net.yuesejiaoyou.redirect.ResolverA.getset;

public class Vliao2_01168 {
	
	/*****************************明细****************************/
	//收入
	/*time: 		现金收入时间
	type: 		现金收入途径
	money: 		收入金额*/
	private String  time;		//现金收入时间
	private String type;		//现金收入途径
	//private double money;		//收入金额
	private String money;		//收入金额
	private String jinqian;		//主播	收入、支出、提现
	//提现
	/*time:		提现时间
	cash:		提现金额
	status: 	提现状态*/
	//private double cash;		//
	private String cash;		//提现金额   
	private String status;		//提现状态
	//支出
	/*type:		支出类型
	num: 		支出数量
	time:		支出时间*/
	private String num;			//支出数量
	//黑名单
	private int sum;			//总金额		支出、收入、提现
	private String photo;		//图片
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getJinqian() {
		return jinqian;
	}
	public void setJinqian(String jinqian) {
		this.jinqian = jinqian;
	}
	
	
	
	
	
	
	
	
	
	
}
