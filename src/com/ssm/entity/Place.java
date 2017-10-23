package com.ssm.entity;

import java.util.List;

public class Place {
	private int pid, time, px, py;
	private String pname, position, photo, satisfaction, startDate, endDate, title, description, startPlace, hotel,lunch;
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	private double cost,childcost;
	private String discount,type, costSource;
	private List<Order> listorder; 
    public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public int getPy() {
		return py;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCostSourse() {
		return costSource;
	}

	public void setCostSourse(String costSource) {
		this.costSource = costSource;
	}

	private List<Comment> listcomment;
	

	public Place() {
		// TODO Auto-generated constructor stub
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public double getChildcost() {
		return childcost;
	}

	public void setChildcost(double childcost) {
		this.childcost = childcost;
	}

	public Place(int pid, int time, String pname, String position, String photo, String satisfaction,double cost,double childcost, String startDate, String endDate, String title, String description, String startPlace) {
		this.pid = pid;
		this.time = time;
		this.pname = pname;
		this.position = position;
		this.photo = photo;
		this.satisfaction = satisfaction;
		this.cost = cost;
		this.childcost = childcost;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
		this.startPlace = startPlace;
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
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
