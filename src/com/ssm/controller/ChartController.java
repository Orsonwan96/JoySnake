package com.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

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
import com.ssm.entity.Order;
import com.ssm.entity.User;
import com.ssm.entity.Place;

@Controller
@RequestMapping("/chartt")
public class ChartController {
	IMapper chartMapper=null;
	public IMapper getchartMapper() {
		return chartMapper;
	}
	@Autowired
	public void setchartMapper(@Qualifier("Model")IMapper chartMapper) {
		this.chartMapper = chartMapper;
	}
	
	@RequestMapping("/numoforder")
	@ResponseBody  
	public Map<String, Object> numoforder(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Map<String, Object> map = new HashMap<String,Object>();
		List<Place> places = this.chartMapper.GetAllPositionPlace();
		for(Place place: places){
			List<Float> pname = new ArrayList<>();
			for(int i = 1; i <= 12; i++){
				pname.add(chartMapper.getnumoforder(i,place.getPosition()));
			}
			map.put(place.getPosition(), pname);
		}
		map.put("judge", true);
//		List<Float> tianjin = new ArrayList<>();
//		List<Float> jinan = new ArrayList<>();
//		List<Float> beijing = new ArrayList<>();
//		for(int i = 1; i <= 12; i++){
//			tianjin.add(chartMapper.getnumoforder(i,chartMapper.GetPlaceIdByName("天津")));
//			jinan.add(chartMapper.getnumoforder(i,chartMapper.GetPlaceIdByName("济南")));
//			beijing.add(chartMapper.getnumoforder(i,chartMapper.GetPlaceIdByName("北京")));
//		}
//		map.put("tianjin", tianjin);
//		map.put("beijing", beijing);
//		map.put("jinan", jinan);
//		map.put("judge", "true");
		return map;
	}        
	
	@RequestMapping("/satisfyCount")
	@ResponseBody  
	public Map<String, Object> SatisfyCount(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Map<String, Object> map = new HashMap<String,Object>();
		

		map.put("degree1", chartMapper.satisfycount("特别喜欢"));
		map.put("degree2", chartMapper.satisfycount("喜欢"));
		map.put("degree3", chartMapper.satisfycount("一般"));
		map.put("degree4", chartMapper.satisfycount("不喜欢"));
		map.put("judge", "true");
		return map;
	}       

	@RequestMapping("/typeCount")
	@ResponseBody  
	public Map<String, Object> TypeCount(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Map<String, Object> map = new HashMap<String,Object>();

		map.put("abroad", chartMapper.typecount("出境游"));
		map.put("culture", chartMapper.typecount("人文景观游"));
		map.put("natural", chartMapper.typecount("自然风光游"));
		map.put("downtown", chartMapper.typecount("乡村游"));
		map.put("city", chartMapper.typecount("城市游"));
		map.put("holiday", chartMapper.typecount("度假游"));
		map.put("scenery", chartMapper.typecount("观光游"));
		map.put("judge", "true");
	
		return map;
	}
	
	@RequestMapping("/typeCount2")
	@ResponseBody  
	public Map<String, Object> TypeCount2(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> map1 = null;
		Map<String, Object> map2 = null;
	//	Map<String, Object> map3 = new HashMap<String,Object>();
		List<Map<String, Object>> res2 = null;
		List<Map<String, Object>> res1=null;
		List<Map<String, Object>> res= new ArrayList<Map<String, Object>>();
		List<Order> orders = new ArrayList<Order>();
		List<Order> typeorder = new ArrayList<Order>();
		List<String> pnames = new ArrayList<String>();
		orders = chartMapper.SelectOrderAll();
		String []type = {"出境游","人文景观游","自然风光游","乡村游","城市游","度假游","观光游"};
		for (String string : type) {
			map1 = new HashMap<String,Object>();
			res1 = new ArrayList<Map<String, Object>>();
		//	typeorder = chartMapper.SelectOneTypeOrderAll(string);
			pnames = chartMapper.GetOneTypePlaces(string);
			for(String pname : pnames){
				map2 = new HashMap<String,Object>();
				
				res2 = new ArrayList<Map<String, Object>>();
				map2.put("年龄0-30人数", chartMapper.GetNumOfDifAge(pname,0, 30));
				
				map2.put("年龄30-50人数", chartMapper.GetNumOfDifAge(pname,30,50));

				map2.put("年龄50以上人数", chartMapper.GetNumOfDifAge(pname,50,330));
				
				map1.put(pname, map2);
			}
		//	res1.add(map1);
			map.put(string,map1);
			
//			int []pnum = new int[typeorder.size()];
//			for (int i : pnum) {
//				i = 0;
//			}
//			int []pid = new int[typeorder.size()];
//			int k = 0;
//			for (Order order : typeorder) {
//				pid[k] = order.getPid();
//				k++;
//			}
//			for(Order order : orders){
//				
//				if (chartMapper.GetPlaceTypeById(order.getPid()) == string) {
//					for(int i = 0; i < typeorder.size(); i++){
//						if (pid[i] == order.getPid()) {
//							pnum[i]++;
//						}
//					}
//				}
//			}
//			for(int i = 0; i < typeorder.size();i++){
//				map1.put(chartMapper.GetPlaceNameById(pid[i]),pnum[i]);
//			}
//			res.add(map1);
//			map.put(string, res);
		}

	//	res.add(map);
	//	map3.put("data5", map);
		return map;
	}
	
	@RequestMapping("/searchdata")
	@ResponseBody  
	public Map<String, Object> searchdata(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/text");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("order", chartMapper.todayorder());
		map.put("place", chartMapper.placecount());
		map.put("user", chartMapper.todayuser());
		map.put("comment", chartMapper.todaycomment());
		return map;
	} 

	

}
