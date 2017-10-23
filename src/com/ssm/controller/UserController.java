package com.ssm.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.ssm.dao.IMapper;
import com.ssm.entity.Place;
import com.ssm.entity.User;

@Controller
@RequestMapping("/usert")
public class UserController {
	//调用UserMapp接口操作的功能
	IMapper userMapper=null;
	public IMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(@Qualifier("Model")IMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@RequestMapping("/login")
	@ResponseBody  
	public Map<String, Object> UsersLogin(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uname")String usn,
			@RequestParam("pwd")String pwd) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
		Map<String, Object> map=new HashMap<String,Object>();
		User user=userMapper.SelectUserByLogin(usn, pwd);		
		if(user!=null){
			System.out.println(usn + " " + pwd);	
			System.out.println(user.getUid()+"  +");			
			map.put("id", user.getUid());
			map.put("name",user.getUname());
		}else{
			map.put("id",0);
		}
		return map;
	}
	
	@RequestMapping("/register")
	@ResponseBody  
	public String UsersRegister(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("uname")String usn,
			@RequestParam("pwd")String pwd,
			@RequestParam("age")String uage1,
			@RequestParam("sex")String usex,
			@RequestParam("account")String account1) throws UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		User usertest=userMapper.SearchUserByName(usn);
		System.out.println(usn + "  " + pwd + "  " + uage1 +" " +usex);
		int uage = Integer.parseInt(uage1);
		double account = Double.valueOf(account1);
		if(usertest == null){
			User user = new User();

			user.setUage(uage);
			user.setUpassword(pwd);
			user.setUname(usn);
			user.setUsex(usex);
			user.setAccount(account);
			System.out.println(user.getUid());
			userMapper.UserRegister(user);
			return "ok";
		}
		else{
			return "wrong";
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody  
	public String UsersDelete(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("id")int uid) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		User usertest=userMapper.SearchUserById(uid);
		System.out.println(uid);

		if(usertest != null){
			userMapper.UserDelete(usertest);
			return "ok";
		}
		else{
			return "wrong";
		}
	}      
	
	@RequestMapping("/userUpdate")
	@ResponseBody  
	public String UpdateUser(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uid")String uuid,
			@RequestParam("upassword")String upwd,
			@RequestParam("uname")String uname,
			@RequestParam("account")String account1,
			@RequestParam("usex")String usex,
			@RequestParam("uage")String uuage)
					throws UnsupportedEncodingException{
		System.out.println("11111111111111");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		int uid = Integer.parseInt(uuid);
		int uage = Integer.parseInt(uuage);
		double account = Double.valueOf(account1);
		User usertest=userMapper.SearchUserById(uid);
		
		System.out.println(uid + "  " + upwd + "  " + uage+ "  ");

		if(usertest != null){
			User user = new User();
//			user.setId(5);
			user.setUid(uid);
			user.setUage(uage);
			user.setUpassword(upwd);
			user.setUname(uname); 
			user.setAccount(account); 
			user.setUsex(usex); 
			
			userMapper.UpdateUser(user);
			return "ok";
		}
		else{
			return "wrong";
		}
	}   
	
	@RequestMapping("/searchUser")
	@ResponseBody
	public List<Map<String, Object>> FindUsersAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("users")User users) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(users.getUname()+" / "+users.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<User> listUser=this.userMapper.SelectUserAll();
		Map<String, Object> map=null;
//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		for (User user : listUser) {
			map=new HashMap<String, Object>();
			map.put("id", user.getUid());
			map.put("name",user.getUname());
			map.put("pwd", user.getUpassword());
			map.put("rtime", user.getRegister_time());
			map.put("sex", user.getUsex());
			map.put("age", user.getUage());
			map.put("account", user.getAccount());
			map.put("edit", "<button id='edit' class='btn btn-info  btn-sm' title='"
			        +user.getUid()+"' >Edit</button>");
			map.put("del", "<button class='btn btn-danger btn-sm'  title='"+user.getUid()+"' id='remove'>Delete</button>");


			res.add(map);
		}
		//json  [{},{},{}]
		return res;
	}  
	
	@RequestMapping("/userEntity")
	@ResponseBody  
	public Map<String, Object> UserEntity(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("uid")String uid)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(uid+"  //  ");		
//		peter  //  112
		Map<String, Object> map=new HashMap<String, Object>();
		int a = Integer.parseInt(uid);
		User user=userMapper.SearchUserById(a);		
		//List<Depts> l=userMapper.SelectDeptsAll();
		//List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		if(user!=null){
			//System.out.println(user.getUid()+"  +");			
			//map.put("id", user.getUid());
			map.put("uid",user.getUid());
			map.put("name",user.getUname());
			map.put("pwd",user.getUpassword());
			map.put("usex", user.getUsex());
			map.put("age",user.getUage());
			map.put("register_time",user.getRegister_time());
			map.put("account",user.getAccount());
			map.put("icon", "upload/" + user.getIcon());

			
			
		//	map.put("list",list);
		} 
		return map;
	}
	
	
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(@RequestParam("file")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
			@RequestParam("uname")String uname,
			@RequestParam("upassword")String upassword,
			@RequestParam("uage")String uage1,
			@RequestParam("usex")String usex,
			@RequestParam("account")String account1) throws IllegalStateException, IOException, UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");

		System.out.println("sss");
		
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String fname = "";
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				file.transferTo(new File(filePath + file.getOriginalFilename()));
				fname += file.getOriginalFilename() + ",";
			}
			fname = fname.substring(0,fname.length() - 1);
			User usertest=userMapper.SearchUserByName(uname);
			int uage = Integer.parseInt(uage1);
			double account = Double.valueOf(account1);
			if(usertest == null){
				User user = new User();
				
				user.setUage(uage);
				user.setUpassword(upassword);
				user.setUname(uname);
				user.setUsex(usex);
				user.setAccount(account);
				user.setIcon(fname);
				
				userMapper.UserRegister(user);
				return "ok";

			}
			
		}
		else{
			return "wrong";
		}

		return "success";
	}
	
	@RequestMapping("searchTopFiveUser")
	@ResponseBody
	public List<Map<String, Object>> FindOrderAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("users")User users) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<User> listUser=this.userMapper.GetTopFiveUser();
		Map<String, Object> map=null;
		
		for (User user : listUser) {
			map = new HashMap<String,Object>();
			map.put("uname", user.getUname());
			System.out.println(user.getUname());
			map.put("icon", "upload/" + user.getIcon());
			System.out.println(user.getIcon());
			res.add(map);
		}
		return res;
	}
	
	@RequestMapping("/UpdateUser")
	@ResponseBody
	public String UpdateUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("uname")String uname,
			@RequestParam("uage")String uage,
			@RequestParam("uid")String uuid,
			@RequestParam("usex")String usex) throws IllegalStateException, IOException, UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");
			System.out.println("s");
		int age = Integer.parseInt(uage);
		int uid = Integer.parseInt(uuid);
				User user = new User();
				user.setUid(uid);
				user.setUname(uname);
				user.setUage(age);
				user.setUsex(usex);
				userMapper.UpdateUser1(user);
				return "success";
	}

	
	@RequestMapping("/UpdateUsericon")
	@ResponseBody
	public String UpdateUsericon(@RequestParam("fileToUpload")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
			@RequestParam("uid1")String uid1) throws IllegalStateException, IOException, UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");
		
			int uid = Integer.parseInt(uid1);
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String fname = "";
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				file.transferTo(new File(filePath + file.getOriginalFilename()));
				fname += file.getOriginalFilename() + ",";
			}
			fname = fname.substring(0,fname.length() - 1);
			User user = new User();
			user.setIcon(fname);
			user.setUid(uid);
			userMapper.UpdateUsericon(user);
				
			return "success";
			
			
		}
		else{
			return "wrong";
		}	
	}
	
	@RequestMapping("/updateuicon")
	@ResponseBody
	public String updateicon(@RequestParam("fileToUpload")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
	@RequestParam("uid1")String uid1) throws IllegalStateException, IOException, UnsupportedEncodingException{
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("application/text");
	int uid = Integer.parseInt(uid1);
	System.out.println("Ss");
	String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
	String fname = "";
	if (files != null && files.length > 0) {
	for (MultipartFile file : files) {
	file.transferTo(new File(filePath + file.getOriginalFilename()));
	fname += file.getOriginalFilename() + ",";
	}
	fname = fname.substring(0,fname.length() - 1);
	User user = userMapper.SearchUserById(uid);
	user.setIcon(fname);
	userMapper.UpdateUsericon(user);
	return "success";


	}
	else{
	return "wrong";
	}


	}


}