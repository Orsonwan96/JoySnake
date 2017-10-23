package com.ssm.entity;

public class Order {
	private int oid, uid, pid, num;
	private double money;
	private String time, state, message;
	private User users;
	private Place places;
	public User getUsers() {
		return users;
	}

	public double getMoney() {
		return money;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Place getPlaces() {
		return places;
	}

	public void setPlaces(Place places) {
		this.places = places;
	}

	
	


	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	public Order(int oid, int uid, int pid, String time, String state, double money, String message) {
		this.oid = oid;
		this.pid = pid;
		this.uid = uid;
		this.time = time;
		this.state = state;
		this.money = money;
		this.message = message;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
