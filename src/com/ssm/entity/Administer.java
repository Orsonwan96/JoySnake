package com.ssm.entity;

public class Administer {
	private int aid, Aage;
	private String Aname, Apassword, Asex,Aicon;

	public Administer() {
		// TODO Auto-generated constructor stub
	}
	
	public Administer(int aid, int Aage, String Aname, String Apassword, String Asex, String Aicon) {
		this.aid = aid;
		this.Aage = Aage;
		this.Aname = Aname;
		this.Apassword = Apassword;
		this.Asex = Asex;
		this.Aicon = Aicon;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getAage() {
		return Aage;
	}

	public void setAage(int aage) {
		Aage = aage;
	}

	public String getAname() {
		return Aname;
	}

	public void setAname(String aname) {
		Aname = aname;
	}

	public String getApassword() {
		return Apassword;
	}

	public void setApassword(String apassword) {
		Apassword = apassword;
	}

	public String getAsex() {
		return Asex;
	}

	public void setAsex(String asex) {
		Asex = asex;
	}

	public String getAicon() {
		return Aicon;
	}

	public void setAicon(String aicon) {
		Aicon = aicon;
	}

}
