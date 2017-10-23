package com.ssm.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssm.entity.User;
import com.ssm.entity.Place;
import com.ssm.entity.Administer;
import com.ssm.entity.Comment;
import com.ssm.entity.Order;
import com.ssm.entity.Liuyan;

@Component("Model")
public interface IMapper {
	
//Admin
	public Administer SelectAdmByLogin(String aname,String apwd);
	public List<Administer> SearchAllAdm();
	public Administer SearchAdmByName(String aname);
	public int AdmRegister(Administer adm);


	
//user
	public User SelectUserByLogin(String uname,String upwd);
	public int UserRegister(User user);
	public int UserDelete(User user);
	public User SearchUserByName(String uname);
	public User SearchUserById(int uid);
	public  void UpdateUser(User user);
	public List<User> SelectUserAll();
	public int CountUserAll();
	public List<User> GetTopFiveUser();
	public  void UpdateUser1(User user);
	public  void UpdateUsericon(User user);

	
//place
	public List<Place> SelectPlaceAll();
	public Place SearchPlaceByName(String pname);
	public int addPlace(Place place);
	public Place SearchPlaceById(int pid);
	public int PlaceDelete(Place place);
	public  void UpdatePlace(Place place);
	
	public List<Comment> SearchCommentByPid(int pid);
	public List<Place> SelectPlaceByType(String type);
	
	public  void UpdatePlaceicon(Place place);

	

	
//comment
	public List<Comment> SelectCommentAll();
	public int addComment(Comment comment);
	public Comment SearchCommentById(int cid);
	//public Comment SearchCommentByName(String pname);
	public int CommentDelete(Comment comment);
	public  void UpdateComment(Comment comment);
	
//Message
	public int addMessage(Liuyan liuyan);
	public List<Liuyan> SelectLiuyanAll();
	public Liuyan SearchLiuyanById(int lid);
	public int LiuyanDelete(Liuyan liuyan);
	
//order
	public int MakeOrder(Order order);
	public int GetUserIdByName(String uname);
	public int GetPlaceIdByName(String pname);
	public  void UpdateOrder(Order order);
	public List<Order> SelectOrderAll();
	public Order SearchOrderById(int oid);
	public int OrderDelete(Order order);
	
	public List<Integer> GetTopThreePid();

	//chart
	public float getnumoforder(int i,String position);
	public int satisfycount(String islike);
	public int  typecount(String s); 
	public int todayorder();
	public int todaycomment();
	public int todayuser();
	public int placecount();
	public int GetNumOfDifAge(String pname,int a1, int a2);
	public List<Place> GetAllPositionPlace();
	public List<String> GetOneTypePlaces(String type);
	
	//road
	public List<String> SelectAllPlace();
	public List<Place> SelectPlaceByPosition(String position);
	public List<String> SelectAllStartPlace(String position);
	
}