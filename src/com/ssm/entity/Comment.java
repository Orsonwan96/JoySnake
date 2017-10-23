package com.ssm.entity;

public class Comment {
    private int cid,uid,pid;
    private String ctime, ctext, isLike;
    private User users;
	public User getUsers() {
		return users;
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

	private Place places;
	
		
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}
	
	public Comment(int cid, int uid, int pid, String ctime, String ctext, String isLike) {
		this.cid = cid;
		this.uid = uid;
		this.pid = pid;
		this.ctime = ctime;
		this.ctext = ctext;
		this.isLike = isLike;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCtext() {
		return ctext;
	}

	public void setCtext(String ctext) {
		this.ctext = ctext;
	}

	public String getIsLike() {
		return isLike;
	}

	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}

}