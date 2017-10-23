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
import com.ssm.entity.Administer;
import com.ssm.entity.Comment;
import com.ssm.entity.User;
import com.ssm.entity.Place;

@Controller
@RequestMapping("/admt")
public class AdmController {
	IMapper admMapper=null;
	public IMapper getadmMapper(){
		return admMapper;
	}
	@Autowired
	public void setadmMapper(@Qualifier("Model")IMapper admMapper) {
		this.admMapper = admMapper;
	}
	
	@RequestMapping("/login")
	@ResponseBody  
	public Map<String, Object> UsersLogin(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("aname")String aname,
			@RequestParam("apwd")String apwd) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
		Map<String, Object> map=new HashMap<String,Object>();
		Administer administer =admMapper.SelectAdmByLogin(aname, apwd);		
		if(administer!=null){
			//System.out.println(usn + " " + pwd);	
			//System.out.println(administer.getAid()+"  +");			
			map.put("id", administer.getAid());
			map.put("name",administer.getAname());
		}else{
			map.put("id",0);
		}
		//json:{"id":xx,"name":xxxx}
		//返回需要jackson jar的支持，
		return map;
	}

	
	@RequestMapping("/AdmEntity")
	@ResponseBody  
	public Map<Integer, Object> AdmEntity(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
		Map<Integer, Object> map=new HashMap<Integer,Object>();
		List<Administer> administers = admMapper.SearchAllAdm();
		Map<String, Object> map1 = null;
		int i = 0;
		for (Administer administer : administers) {
			map1 = new HashMap<>();
			map1.put("aname", administer.getAname());
			map1.put("aage", administer.getAage());
			map1.put("icon", administer.getAicon());
			map1.put("asex", administer.getAsex());
			map.put(i, map1);
			i++;
		}
		return map;		
		
	}
	
	@RequestMapping("/admregister")
	@ResponseBody
	public String admregister(@RequestParam("file")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
			@RequestParam("uname")String uname,
			@RequestParam("upassword")String upassword,
			@RequestParam("uage")String uage1,
			@RequestParam("usex")String usex
	//		@RequestParam("account")String account1
			)
					throws IllegalStateException, IOException, UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");

			Map<String, Object> map=new HashMap<String, Object>();
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String fname = "";
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				file.transferTo(new File(filePath + file.getOriginalFilename()));
				fname += file.getOriginalFilename() + ",";
			}
			fname = fname.substring(0,fname.length() - 1);
			Administer admtest=admMapper.SearchAdmByName(uname);
			int uage = Integer.parseInt(uage1);
	//		double account = Double.valueOf(account1);
			if(admtest == null){
				Administer administer = new Administer();
				
				administer.setAage(uage);
				administer.setApassword(upassword);
				administer.setAname(uname);
				administer.setAsex(usex);
				//administer.setAccoAnt(accoAnt);
				administer.setAicon(fname);
				admMapper.AdmRegister(administer);
				
				return uname;

			}
		}

		return uname;
	}

}
