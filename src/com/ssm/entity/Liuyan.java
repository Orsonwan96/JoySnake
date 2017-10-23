package com.ssm.entity;

public class Liuyan {
	private String uname,mail,message;
	private int lid;
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Liuyan() {
		// TODO Auto-generated constructor stub
	}
	public Liuyan(String uname, String mail, String message,int lid){
		this.uname = uname;
		this.mail = mail;
		this.message = message;
		this.lid = lid;
	}

}
