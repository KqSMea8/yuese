package com.net.yuesejiaoyou.redirect.ResolverB.getset;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 *
 */
public class Page implements Serializable{

	private int current;
	private int pageNo;
	private int totlePage;
	private int totle;
	private int currentEnd;
	private List list;


	
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotlePage() {
		return totlePage;
	}
	public void setTotlePage(int totlePage) {
		this.totlePage = totlePage;
	}
	public int getTotle() {
		return totle;
	}
	public void setTotle(int totle) {
		this.totle = totle;
	}

	public int getCurrentEnd() {
		return currentEnd;
	}
	public void setCurrentEnd(int currentEnd) {
		this.currentEnd = currentEnd;
	}

	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	

}
