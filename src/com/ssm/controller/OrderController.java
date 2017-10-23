package com.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.dao.IMapper;
import com.ssm.entity.Order;
import com.ssm.entity.User;
import com.ssm.entity.Place;

@Controller
@RequestMapping("/ordert")
public class OrderController {
	IMapper orderMapper=null;
	public IMapper getOrderMapper() {
		return orderMapper;
	}
	@Autowired
	public void setOrderMapper(@Qualifier("Model")IMapper orderMapper) {
		this.orderMapper = orderMapper;
	}
	
	IMapper placeMapper=null;
	public IMapper getplaceMapper() {
		return placeMapper;
	}
	@Autowired
	public void setPlaceMapper(@Qualifier("Model")IMapper placeMapper) {
		this.placeMapper = placeMapper;
	}
	
	IMapper userMapper=null;
	public IMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(@Qualifier("Model")IMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	
	@RequestMapping("/orderEntity")
	@ResponseBody  
	public Map<String, Object> UserEntity(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("oid")int oid)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(oid+"  //  ");		
		Map<String, Object> map=new HashMap<String, Object>();
		Order order = orderMapper.SearchOrderById(oid);		
		//List<Depts> l=userMapper.SelectDeptsAll();
		//List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		String placename = orderMapper.SearchPlaceById(order.getPid()).getPname();
		String username = orderMapper.SearchUserById(order.getUid()).getUname();
		if(order!=null){
			//System.out.println(user.getUid()+"  +");			
			//map.put("id", user.getUid());
			map.put("oid",order.getOid());
			map.put("pname",placename);
			map.put("uname",username);
			map.put("when",order.getTime());
			map.put("state",order.getState());
			
			
		//	map.put("list",list);
		} 
		return map;
	}
	
	@RequestMapping("/addorder")
	@ResponseBody  
	public String adduser(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uname")String usn,
			@RequestParam("pname")String pname,@RequestParam("state")String state,
			@RequestParam("money")String money1,
			@RequestParam("message")String message) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		System.out.println(usn + "  " + pname + "  "  + " " + " " + state);
		int uid = orderMapper.GetUserIdByName(usn);
		int pid = orderMapper.GetPlaceIdByName(pname);
		double money = Double.valueOf(money1);
		System.out.println(uid + "  " + pid + "  "  + " " + " " + state);
			Order order = new Order();
			order.setPid(pid);
			order.setUid(uid);
			order.setState(state);
			order.setMoney(money);
			order.setMessage(message);
			order.setNum(1);
			orderMapper.MakeOrder(order);
		
			return "ok";
	}   
	
	@RequestMapping("/delete")
	@ResponseBody  
	public String UsersDelete(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("oid")int oid) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Order ordertest=orderMapper.SearchOrderById(oid);
		System.out.println(oid);

		if(ordertest != null){
			orderMapper.OrderDelete(ordertest);
			return "ok";
		}
		else{
			return "wrong";
		}
	}
	
	@RequestMapping("/orderUpdate")
	@ResponseBody  
	public String UpdateOrder(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("oid")String oid1,
			@RequestParam("pname")String pname,
			@RequestParam("uname")String uname,
			@RequestParam("when")String when,
			@RequestParam("state")String state)
					throws UnsupportedEncodingException{
		int oid = Integer.parseInt(oid1);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Order ordertest=orderMapper.SearchOrderById(oid);
		Place place = placeMapper.SearchPlaceByName(pname);
		User user = userMapper.SearchUserByName(uname);
		if(ordertest != null){
			Order order = new Order();
            order.setPid(place.getPid());
            order.setOid(oid);
			order.setUid(user.getUid());
			order.setTime(when);
			order.setNum(1);
			order.setState(state);
			orderMapper.UpdateOrder(order);
			return "ok";
		}
		else{
			return "wrong";
		}
	}   
	
	@RequestMapping("/searchOrder")
	@ResponseBody
	public List<Map<String, Object>> FindOrderAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("orders")Order orders) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(orders.getOid()+" / "+orders.getState()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Order> listOrder=this.orderMapper.SelectOrderAll();
		Map<String, Object> map=null;
		
		for (Order order : listOrder) {
			String placename = orderMapper.SearchPlaceById(order.getPid()).getPname();
			String username = orderMapper.SearchUserById(order.getUid()).getUname();
			map=new HashMap<String, Object>();
			map.put("oid", order.getOid());
			map.put("uname",username);
			map.put("pname",placename);
			map.put("when", order.getTime());
			map.put("num", order.getNum());
			map.put("state", order.getState());
			map.put("edit", "<button id='edit' class='btn btn-info  btn-sm' title='"
			        +order.getOid()+"' >Edit</button>");
			map.put("del", "<button class='btn btn-danger btn-sm'  title='"+order.getOid()+"' id='remove'>Delete</button>");
			res.add(map);
		}
		//json  [{},{},{}]
		return res;
	}
	
	@RequestMapping("/addqinziorder")
	@ResponseBody  
	public String addqinziorder(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uid")String uid1,
			@RequestParam("cost")String cost,
			@RequestParam("pid")String pid1,@RequestParam("message")String message) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		int uid = Integer.parseInt(uid1);
		int pid = Integer.parseInt(pid1);
		double money = Double.parseDouble(cost);
		User user = orderMapper.SearchUserById(uid);
		if(user.getAccount() >= money){
			user.setAccount(user.getAccount() - money);
			orderMapper.UpdateUser(user);
			Order order = new Order();
			order.setPid(pid);
			order.setUid(uid);
			order.setMessage(message);
			order.setMoney(money);
			order.setState("Œ¥¥¶¿Ì");
			orderMapper.MakeOrder(order);
			return "ok";
		}
		else{
			return "wrong";
		}
	}



}

