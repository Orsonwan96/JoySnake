package com.ssm.entity;

import java.util.List;

import org.springframework.context.annotation.Primary;

public class User {
	private String upassword, uname, register_time,icon,usex;
	private int uid, uage;
	private double account;
	private List<Order> listorder; 
	private List<Comment> listcomment;

	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String password, String username,String usex, String icon, double account, int id, int age, String register_time){
		   this.uid = id;
		   this.uage = age;
		   this.register_time = register_time;
		   this.upassword = password;
		   this.uname = username;
		   this.icon = icon;
		   this.account = account;
		   this.usex = usex;
	   }
	public List<Comment> getListcomment() {
		return listcomment;
	}

	public void setListcomment(List<Comment> listcomment) {
		this.listcomment = listcomment;
	}
	public List<Order> getListorder() {
		return listorder;
	}
	public void setListorder(List<Order> listorder) {
		this.listorder = listorder;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getRegister_time() {
		return register_time;
	}
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUsex() {
		return usex;
	}
	public void setUsex(String usex) {
		this.usex = usex;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getUage() {
		return uage;
	}
	public void setUage(int uage) {
		this.uage = uage;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
}