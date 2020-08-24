package com.stock.pojo;

import java.io.Serializable;

public class KDJ implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8202360159963082509L;
	public float k;
	public float d;
	public float j;
	public String code;
	public String date;
	
	public KDJ() {
		
	}
	
	public KDJ(float k,float d,float j) {
		this.k = k;
		this.d = d;
		this.j = j;
	}
	
	public KDJ(float k,float d,float j,String code,String date) {
		this.k = k;
		this.d = d;
		this.j = j;
		this.code = code;
		this.date = date;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[k: " + String.format("%.2f",k) + ",d: " + String.format("%.2f",d) + ",j: " + String.format("%.2f",j) + "]";
	}
}
