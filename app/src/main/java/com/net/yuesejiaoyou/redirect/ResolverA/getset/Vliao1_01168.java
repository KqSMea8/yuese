package com.net.yuesejiaoyou.redirect.ResolverA.getset;

public class Vliao1_01168 {
	//钱包
	private int money;			//资产：V币
	private int is_anchor;		//是否是主播
	//黑名单
	private String username;	//用户名
	private String nickname;	//昵称
	private String photo;		//头像
	private int target_id;		//他的id
	//关注
	private int star;			//星级
	private String status;		//状态
	
	//代理
	private String id;						//用户id
	private String tixian_account;			//提现账户
	private String account_name;			//用户名
	private String people_num;				//人数

	private String phonenum;				//手机号
	
	//推荐人提现
	private String ableinvite_price;		//推荐人提现页面的可提现金额
	private String invite_price;			//剩余贡献值
	
	//推荐人提现明细	推荐人收益明细
	private String money_num;		//对方的话费金额的签署
	private String able_money;		//给我的收益
	private String uptime;			//时间
	
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTarget_id() {
		return target_id;
	}
	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getIs_anchor() {
		return is_anchor;
	}
	public void setIs_anchor(int is_anchor) {
		this.is_anchor = is_anchor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTixian_account() {
		return tixian_account;
	}
	public void setTixian_account(String tixian_account) {
		this.tixian_account = tixian_account;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getPeople_num() {
		return people_num;
	}
	public void setPeople_num(String people_num) {
		this.people_num = people_num;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getAbleinvite_price() {
		return ableinvite_price;
	}
	public void setAbleinvite_price(String ableinvite_price) {
		this.ableinvite_price = ableinvite_price;
	}
	public String getInvite_price() {
		return invite_price;
	}
	public void setInvite_price(String invite_price) {
		this.invite_price = invite_price;
	}
	public String getMoney_num() {
		return money_num;
	}
	public void setMoney_num(String money_num) {
		this.money_num = money_num;
	}
	public String getAble_money() {
		return able_money;
	}
	public void setAble_money(String able_money) {
		this.able_money = able_money;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
	
	
	
}
