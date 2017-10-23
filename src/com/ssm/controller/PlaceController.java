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
import com.ssm.entity.Comment;
import com.ssm.entity.Place;
import com.ssm.entity.User;


@Controller
@RequestMapping("/placet")
public class PlaceController {
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
	public String placesDelete(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("id")int pid) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Place placetest=placeMapper.SearchPlaceById(pid);
		System.out.println(pid);
		
		if(placetest != null){
			placeMapper.PlaceDelete(placetest);
			return "ok";
		}
		else{
			return "wrong";
		} 
	}        
	
	@RequestMapping("/placeUpdate")
	@ResponseBody  
	public String Updateplace(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("pid")String pid1,
			@RequestParam("position")String position,
			@RequestParam("pname")String pname,
			@RequestParam("photo")String photo,
			@RequestParam("satisfaction")String satisfaction,
			@RequestParam("cost")String cost1,
			@RequestParam("childcost")String childcost1,
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate,
			@RequestParam("title")String title,
			@RequestParam("description")String description,
			@RequestParam("startPlace")String startPlace,
			@RequestParam("type")String type,
			@RequestParam("discount")String discount,
			@RequestParam("costSource")String costSource,
			@RequestParam("px")String px1,
			@RequestParam("py")String py1,
			@RequestParam("hotel")String hotel,
			@RequestParam("lunch")String lunch,
			@RequestParam("time")String time1)
					throws UnsupportedEncodingException{
		System.out.println("11111111111111");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		int pid = Integer.parseInt(pid1);
		int time = Integer.parseInt(time1);
		int px = Integer.parseInt(px1);
		int py = Integer.parseInt(py1);
		double cost = Double.valueOf(cost1);
		double childcost = Double.valueOf(childcost1);
		Place placetest=placeMapper.SearchPlaceById(pid);
		
		//System.out.println(uid + "  " + upwd + "  " + uage+ "  ");

		if(placetest != null){
			Place place = new Place();
			place.setPid(pid);
			place.setPname(pname);
			place.setPosition(position);
			place.setPhoto(photo);
			place.setSatisfaction(satisfaction); 
			place.setTime(time);
			place.setCost(cost);
			place.setChildcost(childcost);
			place.setDescription(description);
			place.setStartDate(startDate);
			place.setStartPlace(startPlace);
			place.setEndDate(endDate);
			place.setCostSourse(costSource);
			place.setType(type);
			place.setDiscount(discount);
			place.setTitle(title);
			place.setPx(px);
			place.setPy(py);
			place.setHotel(hotel);
			place.setLunch(lunch);
			
			placeMapper.UpdatePlace(place);
			return "ok";
		}
		else{
			return "wrong";
		}
	}     
	 
	
	@RequestMapping("/searchPlace")
	@ResponseBody
	public List<Map<String, Object>> FindplacesAll(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("places")Place places) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//System.out.println(places.getUname()+" / "+places.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Place> listPlace=this.placeMapper.SelectPlaceAll();
		Map<String, Object> map=null;
//<div class='btn btn-primary' data-toggle='modal' data-target='#addModal'>Edit</div>
		String realpath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		System.out.println(realpath);
		for (Place place : listPlace) {
			map=new HashMap<String, Object>();
			if (place.getPhoto() != null) {
				String photopath = place.getPhoto();
				String [] path = null;
				path = photopath.split(",");	
				map.put("photo","<img width='60px' height='60px' src = 'upload/"+path[0]+"' />");
			}			
			map.put("id", place.getPid());			
			map.put("name",place.getPname());
			map.put("position", place.getPosition());
			map.put("satisfaction", place.getSatisfaction());
			map.put("time", place.getTime());
			map.put("cost", place.getCost());
			map.put("childcost", place.getChildcost());
		//	map.put("photo", place.getPhoto());
			map.put("startDate", place.getStartDate());
			map.put("endDate", place.getEndDate());
			map.put("title", place.getTitle());
			map.put("description", place.getDescription());
			map.put("type", place.getType());
			map.put("discount", place.getDiscount());
			map.put("costSource", place.getCostSourse());
			map.put("startPlace", place.getStartPlace());
			map.put("px", place.getPx());
			map.put("py", place.getPy());
			map.put("hotel", place.getHotel());
			map.put("lunch", place.getLunch());
			map.put("edit", "<button id='edit' class='btn btn-info  btn-sm' title='"
			        +place.getPid()+"' id='add-without-image1'>Edit</button>");
			map.put("del", "<button class='btn btn-danger btn-sm'  title='"+place.getPid()+"' id='remove'>Delete</button>");
			res.add(map);
		}
		return res;
	}  
	
	@RequestMapping("/addplace")
	@ResponseBody  
	public String placesRegister(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("pname")String pname,
			@RequestParam("position")String position,
			@RequestParam("photo")String photo,
			@RequestParam("time")String time1,
			@RequestParam("satisfaction")String satisfaction,
			@RequestParam("childcost")String childcost1,
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate,
			@RequestParam("title")String title,
			@RequestParam("description")String description,
			@RequestParam("startPlace")String startPlace,
			@RequestParam("type")String type,
			@RequestParam("discount")String discount,
			@RequestParam("costSource")String costSource,
			@RequestParam("px")String px1,
			@RequestParam("py")String py1,
			@RequestParam("hotel")String hotel,
			@RequestParam("lunch")String lunch,
			@RequestParam("cost")String cost1) throws UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Place placetest=placeMapper.SearchPlaceByName(pname);
		//System.out.println(usn + "  " + pwd + "  " + uage1 +" " +usex);
		int time = Integer.parseInt(time1);
		int px = Integer.parseInt(px1);
		int py = Integer.parseInt(py1);
		double cost = Double.valueOf(cost1);
		double childcost = Double.valueOf(childcost1);
		if(placetest == null){
			Place place = new Place();

			place.setPname(pname);
			place.setPosition(position);
			place.setPhoto(photo);
			place.setTime(time);
			place.setSatisfaction(satisfaction);
			place.setCost(cost);
			place.setChildcost(childcost);
			place.setStartDate(startDate);
			place.setEndDate(endDate);
			place.setTitle(title);
			place.setDescription(description);
			place.setStartPlace(startPlace);
			place.setType(type);
			place.setDiscount(discount);
			place.setTitle(title);
			place.setPx(px);
			place.setPy(py);
			place.setHotel(hotel);
			place.setLunch(lunch);
			placeMapper.addPlace(place);
			return "ok";
		}
		else{
			return "wrong";
		}
	}
	
	@RequestMapping("/placeEntity")
	@ResponseBody  
	public Map<String, Object> placeEntity(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("pid")String pid1)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(pid1+"  //  ");		

		Map<String, Object> map=new HashMap<String, Object>();
		int pid = Integer.parseInt(pid1);
		Place place=placeMapper.SearchPlaceById(pid);		
		
		if(place!=null){
			
			map.put("pname",place.getPname());
			map.put("position",place.getPosition());
			map.put("photo",place.getPhoto());
			map.put("time",place.getTime());
			map.put("satisfaction",place.getSatisfaction());
			map.put("cost",place.getCost());
			map.put("childcost",place.getChildcost());
			map.put("startDate",place.getStartDate());
			map.put("endDate",place.getEndDate());
			map.put("title",place.getTitle());
			map.put("description",place.getDescription());
			map.put("startPlace",place.getStartPlace());
			map.put("type", place.getType());
			map.put("discount", place.getDiscount());
			map.put("costSource", place.getCostSourse());
			map.put("px", place.getPx());
			map.put("py", place.getPy());
			map.put("hotel", place.getHotel());
			map.put("lunch", place.getLunch());
		} 
		return map;
	}
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(@RequestParam("file")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
			@RequestParam("pname")String pname,
			@RequestParam("position")String position,
			@RequestParam("time")String time1,
			@RequestParam("satisfaction")String satisfaction,
			@RequestParam("childcost")String childcost1,
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate,
			@RequestParam("title")String title,
			@RequestParam("description")String description,
			@RequestParam("startPlace")String startPlace,
			@RequestParam("type")String type,
			@RequestParam("discount")String discount,
			@RequestParam("costSource")String costSource,
			@RequestParam("px")String px1,
			@RequestParam("py")String py1,
			@RequestParam("hotel")String hotel,
			@RequestParam("lunch")String lunch,
			@RequestParam("cost")String cost1) throws IllegalStateException, IOException, UnsupportedEncodingException{
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
			Place placetest=placeMapper.SearchPlaceByName(pname);
			int time = Integer.parseInt(time1);
			int px = Integer.parseInt(px1);
			int py = Integer.parseInt(py1);
			double cost = Double.valueOf(cost1);
			double childcost = Double.valueOf(childcost1);
			if(placetest == null){
				Place place = new Place();

				place.setPname(pname);
				place.setPosition(position);
				place.setPhoto(fname);
				place.setTime(time);
				place.setSatisfaction(satisfaction);
				place.setCost(cost);
				place.setChildcost(childcost);
				place.setStartDate(startDate);
				place.setEndDate(endDate);
				place.setTitle(title);
				place.setDescription(description);
				place.setStartPlace(startPlace);
				place.setType(type);
				place.setDiscount(discount);
				place.setTitle(title);
				place.setPx(px);
				place.setPy(py);
				place.setHotel(hotel);
				place.setLunch(lunch);
				placeMapper.addPlace(place);
				return "success";
			}
			
		}
		else{
			return "wrong";
		}

		return "success";
	}
	
	//新加的
	@RequestMapping("/SearchThreePhoto")
	@ResponseBody  
	public Map<String, Object> ThreePhoros(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("pid")String pid1)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println(pid1+"  //  ");		

		Map<String, Object> map=new HashMap<String, Object>();
		int pid = Integer.parseInt(pid1);
		Place place=placeMapper.SearchPlaceById(pid);		
		
		if(place!=null){
			
			if (place.getPhoto() != null) {
				String photopath = place.getPhoto();
				String [] path = null;
				path = photopath.split(",");	
				map.put("photo1","upload/"+path[0]);
				map.put("photo2","upload/"+path[1]);
				map.put("photo3","upload/"+path[2]);
				map.put("photo4","upload/"+path[3]);
			}
		} 
		return map;
	}
	
	@RequestMapping("/getDiscount")
	@ResponseBody  
	public Map<String, Object> FourDiscount(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("pid")String pid1)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//System.out.println(pid1+"  //  ");		

		Map<String, Object> map=new HashMap<String, Object>();
		int pid = Integer.parseInt(pid1);
		Place place=placeMapper.SearchPlaceById(pid);		
		
		if(place!=null){
			
			if (place.getDiscount() != null) {
				String discount = place.getDiscount();
				//System.out.println(discount);	
				String [] dis = null;
				dis = discount.split("a");	
		
				map.put("dis1",dis[0]);
				map.put("dis2",dis[1]);
				map.put("dis3",dis[2]);
				map.put("dis4",dis[3]);
				
			}
		} 
		return map;
	}
	
	@RequestMapping("/getmessage")
	@ResponseBody
	public Map<String, Object> GetProductMessage(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("places")Place places) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<Map<String, Object>> res= null;
		
		Map<String, Object> map=null;
		Map<String, Object> mapall=new HashMap<String, Object>();
		String []type = {"出境游","人文景观游","自然风光游","乡村游","城市游","度假游","观光游"};
		for(String string : type){
			res=new ArrayList<Map<String,Object>>();
			List<Place> listPlace=this.placeMapper.SelectPlaceByType(string);
			for (Place place : listPlace) {
				map=new HashMap<String, Object>();
				if (place.getPhoto() != null) {
					String photopath = place.getPhoto();
					String [] path = null;
					path = photopath.split(",");	
					map.put("photo","upload/"+ path[0]);
				}			
				map.put("id", place.getPid());
				map.put("title", place.getTitle());
				map.put("description", place.getDescription());
				res.add(map);
			}
			map=new HashMap<String, Object>();
			int[] result = new int[4];  
			for(int o = 0; o < 4; o++){
				result[o] = -1;
			}
		    int count = 0;  
		    while(count < 4) {  
		    	 
		        int num = (int) (Math.random() * (listPlace.size()));  
		        boolean flag = true;  
		        for (int j = 0; j < 4; j++) {  
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
		    map.put("num1", result[0]);
		    map.put("num2", result[1]);
		    map.put("num3", result[2]);
		    map.put("num4", result[3]);
		    res.add(map);
		    mapall.put(string, res);
		}
		
		return mapall;
	}   
	
	@RequestMapping("/GetTopThreePlace")
	@ResponseBody  
	public List<Map<String, Object>> GetTopThreePlace(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		List<Integer> pids = placeMapper.GetTopThreePid();
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		Map<String, Object> map= null;
		for(int i : pids){
			map = new HashMap<String,Object>();
			Place place = placeMapper.SearchPlaceById(i);
			if ( placeMapper.SearchPlaceById(i).getPhoto() != null) {
				String photopath =  placeMapper.SearchPlaceById(i).getPhoto();
				String [] path = null;
				path = photopath.split(",");	
//				for (String p : path) {
//					System.out.println("<"+p);
//				}
				map.put("photo","upload/"+ path[0]);
			}	
			
			map.put("id", place.getPid());
			map.put("title", place.getTitle());
			map.put("description", place.getDescription());
			res.add(map);
		}
		return res;
	}
	
	@RequestMapping("/SelectThreePlace")
	@ResponseBody
	public List<Map<String, Object>> getThreeComment(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("places")Place places) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		//System.out.println(places.getUname()+" / "+places.getUpassword()+" / ");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Place> listPlace=this.placeMapper.SelectPlaceAll();
		
		int[] result = new int[5];  
	    int count = 0;  
	    while(count < 5) {  
	        int num = (int) (Math.random() * listPlace.size());  
	        boolean flag = true;  
	        for (int j = 0; j < 5; j++) {  
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
	    for(int i=0; i<5; i++){  
	    	map=new HashMap<String, Object>();
	    	String pname=listPlace.get(result[i]).getPname();
	    	String photo=listPlace.get(result[i]).getPhoto();
	    	String [] path = null;
			path = photo.split(",");
			String src="upload/"+path[0];  
			System.out.println(src);
	    	String description=listPlace.get(result[i]).getDescription();
			map.put("pname", pname);
			map.put("src", src);
			map.put("description", description);
			res.add(map);
		}
		return res;
	}
	
	@RequestMapping("/UpdatePlaceicon")
	@ResponseBody
	public String UpdateUsericon(@RequestParam("fileToUpload")MultipartFile[] files, HttpServletRequest request,HttpServletResponse response,
			@RequestParam("ph1")String ph1,
			@RequestParam("pid1")String pid1) throws IllegalStateException, IOException, UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");
		    String newphoto = "";
			int pid = Integer.parseInt(pid1);
			int ph = Integer.parseInt(ph1);
			Place place1 = placeMapper.SearchPlaceById(pid);
			Place place2 = new Place();
			String photo1 = place1.getPhoto();
			String []arr = photo1.split(",");
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String fname = "";
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				file.transferTo(new File(filePath + file.getOriginalFilename()));
				fname += file.getOriginalFilename() + ",";
			}
			fname = fname.substring(0,fname.length() - 1);
				arr[ph] = fname;
				for(String i : arr){
					newphoto += i;
					newphoto += ",";
				}
				newphoto = newphoto.substring(0, newphoto.length() - 1);
				place2.setPid(pid);
				place2.setPhoto(newphoto);
				placeMapper.UpdatePlaceicon(place2);
			return "success";
			
			
		}
		else{
			return "wrong";
		}

		
	}

	
} 
