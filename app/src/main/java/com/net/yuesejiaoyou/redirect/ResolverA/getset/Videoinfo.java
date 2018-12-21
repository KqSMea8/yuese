package com.net.yuesejiaoyou.redirect.ResolverA.getset;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/28.
 */

public class Videoinfo implements Serializable{
	private int id;
	private String userid;
	private String nickname;
	private String video_id;

	private String video_photo;
	private String		like_num;
	private int status;
	private int ispay;
	private String	 price;
	private int iszan;
	private String	 explain;
	private String	 photo;
	private int online;
	private int liulan_num;
	private int share_num;
	private int isguanzhu;
	private int pay_num;

	public int getPay_num() {
		return pay_num;
	}

	public void setPay_num(int pay_num) {
		this.pay_num = pay_num;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getIsguanzhu() {
		return isguanzhu;
	}

	public void setIsguanzhu(int isguanzhu) {
		this.isguanzhu = isguanzhu;
	}

	public int getLiulan_num() {
		return liulan_num;
	}

	public void setLiulan_num(int liulan_num) {
		this.liulan_num = liulan_num;
	}

	public int getShare_num() {
		return share_num;
	}

	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}

	public String getVideo_photo() {
		return video_photo;
	}

	public void setVideo_photo(String video_photo) {
		this.video_photo = video_photo;
	}

	public String getLike_num() {
		return like_num;
	}

	public void setLike_num(String like_num) {
		this.like_num = like_num;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIspay() {
		return ispay;
	}

	public void setIspay(int ispay) {
		this.ispay = ispay;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getIszan() {
		return iszan;
	}

	public void setIszan(int iszan) {
		this.iszan = iszan;
	}

	@Override
	public String toString() {
		return "Videoinfo{" +
				"id=" + id +
				", userid='" + userid + '\'' +
				", nickname='" + nickname + '\'' +
				", video_id='" + video_id + '\'' +
				", video_photo='" + video_photo + '\'' +
				", like_num='" + like_num + '\'' +
				", status=" + status +
				", ispay=" + ispay +
				", price='" + price + '\'' +
				", iszan=" + iszan +
				", explain='" + explain + '\'' +
				", photo='" + photo + '\'' +
				", online=" + online +
				", liulan_num=" + liulan_num +
				", share_num=" + share_num +
				", isguanzhu=" + isguanzhu +
				'}';
	}
}
