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
import com.ssm.entity.Comment;
import com.ssm.entity.User;
import com.ssm.entity.Place;
import com.ssm.entity.Liuyan;

@Controller
@RequestMapping("/commentt")
public class CommentController {
	IMapper commentMapper=null;
	public IMapper getcommentMapper() {
		return commentMapper;
	}
	@Autowired
	public void setcommentMapper(@Qualifier("Model")IMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	IMapper userMapper=null;
	public IMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(@Qualifier("Model")IMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	IMapper placeMapper=null;
	public IMapper getplaceMapper() {
		return placeMapper;
	}
	@Autowired
	public void setplaceMapper(@Qualifier("Model")IMapper placeMapper) {
		this.placeMapper = placeMapper;
	}

	@RequestMapping("/delete")
	@ResponseBody  
	public String commentsDelete(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("id")int cid) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Comment commenttest=commentMapper.SearchCommentById(cid);
		System.out.println(cid);
		
		if(commenttest != null){
			commentMapper.CommentDelete(commenttest);
			return "ok";
		}
		else{
			return "wrong";
		} 
	}        
	
	@RequestMapping("/commentUpdate")
	@ResponseBody  
	public String Updatecomment(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("cid")String cid1,
			@RequestParam("uname")String uname,
			@RequestParam("pname")String pname,
			@RequestParam("ctext")String ctext,
			@RequestParam("ctime")String ctime,
			@RequestParam("isLike")String isLike)
					throws UnsupportedEncodingException{
		System.out.println("11111111111111");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		int cid = Integer.parseInt(cid1);
		Comment commenttest=commentMapper.SearchCommentById(cid);
		if(commenttest != null){
			Comment comment = new Comment();
			User user=userMapper.SearchUserByName(uname);
			Place place=placeMapper.SearchPlaceByName(pname);
			comment.setCid(cid);
			comment.setUid(user.getUid());
			comment.setPid(place.getPid());
			comment.setCtext(ctext);
			comment.setCtime(ctime);
			comment.setIsLike(isLike);

			commentMapper.UpdateComment(comment);
			return "ok";
		}
		else{
			return "wrong";
		}
	}     
	 
	
	@RequestMapping("/searchComment")
	@ResponseBody
	public List<Map<String, Object>> FindcommentsAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("comments")Comment comments) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		//System.out.println(comments.getUname()+" / "+comments.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();

		List<Comment> listcomment=this.commentMapper.SelectCommentAll();

		Map<String, Object> map=null;

//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		for (Comment comment : listcomment) {
			map=new HashMap<String, Object>();
			map.put("cid", comment.getCid());
			User usertest=userMapper.SearchUserById(comment.getUid());
			map.put("uname",usertest.getUname());
			System.out.println(usertest.getUname());
			Place placetest=placeMapper.SearchPlaceById(comment.getPid());
			map.put("pname",placetest.getPname());
			System.out.println(placetest.getPname());
			map.put("ctext", comment.getCtext());
			map.put("isLike", comment.getIsLike());
			map.put("ctime", comment.getCtime());
			map.put("edit", "<button id='edit' class='btn btn-info  btn-sm' title='"
			        +comment.getCid()+"' id='add-without-image1'>Edit</button>");
			map.put("del", "<button class='btn btn-danger btn-sm'  title='"+comment.getCid()+"' id='remove'>Delete</button>");
			res.add(map);
		}
		return res;
	}   
	
	@RequestMapping("/searchLiuyan")
	@ResponseBody
	public List<Map<String, Object>> FindliuyanAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("comments")Liuyan comments) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		//System.out.println(comments.getUname()+" / "+comments.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();

		List<Liuyan> listcomment=this.commentMapper.SelectLiuyanAll();

		Map<String, Object> map=null;

//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		for (Liuyan comment : listcomment) {
			map=new HashMap<String, Object>();
			map.put("lid", comment.getLid());
			
			map.put("uname",comment.getUname());
			
			map.put("mail", comment.getMail());
			map.put("message", comment.getMessage());
			
			map.put("del", "<button class='btn btn-danger btn-sm'  title='"+comment.getLid()+"' id='remove'>Delete</button>");
			res.add(map);
		}
		return res;
	}
	
	
	@RequestMapping("/addcomment")
	@ResponseBody  
	public String commentsRegister(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("uname")String uname,
			@RequestParam("pname")String pname,
			@RequestParam("isLike")String isLike,
			@RequestParam("ctext")String ctext) throws UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		System.out.println(uname);
		System.out.println(pname);
		System.out.println(isLike);
		System.out.println(ctext);
		User user=userMapper.SearchUserByName(uname);
		Place place=placeMapper.SearchPlaceByName(pname);
		//System.out.println(usn + "  " + pwd + "  " + uage1 +" " +usex);
		
			Comment comment = new Comment();

			comment.setPid(place.getPid());
			comment.setUid(user.getUid());
			comment.setCtext(ctext);
			comment.setIsLike(isLike);
			commentMapper.addComment(comment); 
			
			return "ok";
	}
	  
	@RequestMapping("/commentEntity")
	@ResponseBody  
	public Map<String, Object> commentEntity(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("cid")String cid1)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(cid1+"  //  ");		

		Map<String, Object> map=new HashMap<String, Object>();
		int cid = Integer.parseInt(cid1);
		Comment comment=commentMapper.SearchCommentById(cid);		
		
		if(comment!=null){
			User user=userMapper.SearchUserById(comment.getUid());
			map.put("uname",user.getUname());
			Place place=placeMapper.SearchPlaceById(comment.getPid());
			map.put("pname",place.getPname());
			map.put("ctext",comment.getCtext());
			map.put("ctime",comment.getCtime());
			map.put("isLike",comment.getIsLike());

		} 
		return map;
	} 
	
	@RequestMapping("/SearchCommentByPid")
	@ResponseBody
	public List<Map<String, Object>> SearchCommentByPid(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("pid")String pid1) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		int pid = Integer.parseInt(pid1);
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Comment> listComment=this.commentMapper.SearchCommentByPid(pid);
		Map<String, Object> map=null;
//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		for (Comment comment : listComment) {
			map=new HashMap<String, Object>();
			map.put("ctext",comment.getCtext());
			map.put("ctime",comment.getCtime());
			map.put("isLike",comment.getIsLike());
			User user=userMapper.SearchUserById(comment.getUid());
			map.put("uname", user.getUname());

			res.add(map);
		}
		return res;
	}        
	
	//ÐÂ¼Ó
	@RequestMapping("/SelectThreeComment")
	@ResponseBody
	public List<Map<String, Object>> getThreeComment(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("comments")Comment comments) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//System.out.println(places.getUname()+" / "+places.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Comment> listComment=this.commentMapper.SelectCommentAll();
		
		int[] result = new int[6];  
	    int count = 0;  
	    while(count < 6) {  
	        int num = (int) (Math.random() * listComment.size());  
	        boolean flag = true;  
	        for (int j = 0; j < 6; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }    
	    }
	    Map<String, Object> map=null;
	    for(int i=0; i<6; i++){  
	    	map=new HashMap<String, Object>();
	    	int uid=listComment.get(result[i]).getUid();
	    	User user=commentMapper.SearchUserById(uid);
			map.put("uname", user.getUname());
			map.put("ctime", listComment.get(result[i]).getCtime());
			map.put("ctext", listComment.get(result[i]).getCtext());
			res.add(map);
		}
		return res;
	}
	
	@RequestMapping("/Liuyandelete")
	@ResponseBody  
	public String liuyansDelete(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("id")int lid) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Liuyan commenttest=commentMapper.SearchLiuyanById(lid);
		//System.out.println(cid);
		
		if(commenttest != null){
			commentMapper.LiuyanDelete(commenttest);
			return "ok";
		}
		else{
			return "wrong";
		} 
	}
	
	@RequestMapping("/leaveMessage")
	@ResponseBody  
	public String leaveMessage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("name")String name,
			@RequestParam("email")String email,
			@RequestParam("text")String text) throws UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");

			Liuyan liuyan = new Liuyan();

			liuyan.setUname(name);
			liuyan.setMail(email);
			liuyan.setMessage(text);
			
			commentMapper.addMessage(liuyan); 
			
			return "ok";
	}
	
}  
