package com.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.text.DecimalFormat;
import java.math.BigDecimal;  

import java.text.NumberFormat;

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

import Jisuanjinwei.CalculateJinwei;
import Jisuanjinwei.JinweiZhuanhuan;
import Jisuanjinwei.MaxMin;
import Jisuanjinwei.random;
import calculateRoad.getPath;

import com.ssm.entity.Place;

@Controller
@RequestMapping("/road")
public class CalculateRoad {
	IMapper roadMapper=null;
	public IMapper getroadMapper() {
		return roadMapper;
	}
	@Autowired
	public void setroadMapper(@Qualifier("Model")IMapper roadMapper) {
		this.roadMapper = roadMapper;
	}
	
	@RequestMapping("/SelectAllPlace")
	@ResponseBody  
	//public Map<String, Object> UserEntity(HttpServletRequest request,
	public List<String>  SelectPlace(HttpServletRequest request,
			HttpServletResponse response)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
//		peter  //  112
		List<String> l=roadMapper.SelectAllPlace();

		return l;
	}        
	
	@RequestMapping("/SelectPlaceByPositon")
	@ResponseBody
	public List<Map<String, Object>> SelectPlaceByPositon(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("position")String position) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		System.out.println("11111111");
		System.out.println(position);
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Place> listPlace=roadMapper.SelectPlaceByPosition(position);
		Map<String, Object> map=null;    
		for (Place place : listPlace) {
			map = new HashMap<String,Object>();
			map.put("pid",place.getPid());
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
			res.add(map);
		}
		return res;
	}  
	
	@RequestMapping("/SelectAllStartPlace")
	@ResponseBody  
	//public Map<String, Object> UserEntity(HttpServletRequest request,
	public List<String>  SelectStartPlace(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("position")String position)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
//		peter  //  112
		List<String> l=roadMapper.SelectAllStartPlace(position);

		return l;
	}
	
	@RequestMapping("/getPlace")
	@ResponseBody  
	//public Map<String, Object> UserEntity(HttpServletRequest request,
	public List<Map<String, Object>> getPlaces(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("startPlace")String startPlace,
			@RequestParam("result")String result,
			@RequestParam("pids")String pids,
			@RequestParam("time")int expecttime ,
			@RequestParam("money")int expectmoney)
					throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");	
//		peter  //  112
		System.out.println(startPlace+" "+pids);
		String[] pidArray=pids.split(",");
		String dis[] = result.split(",");
		
		int fromArray[]=new int[dis.length];
		int toArray[]=new int[dis.length];
		int minuteArray[]=new int[dis.length];
		String gongliArray[]=new String[dis.length];
		String position="";
		
		//处理 得到数据
		for (int i=0; i<dis.length; i++) 	
		{
			System.out.println(dis[i]);
			int indexto=dis[i].indexOf("to");
			int indexa=dis[i].indexOf("a");
			int inxie=dis[i].indexOf("/");
			String from=dis[i].substring(0,indexto);
			String to=dis[i].substring(indexto+2,indexa);
			
			int startPid=roadMapper.GetPlaceIdByName(from);
			int endPid=roadMapper.GetPlaceIdByName(to);
			String gongli=dis[i].substring(indexa+1,inxie);
			String shijian1=dis[i].substring(inxie+1,dis[i].length());
			//System.out.println("from:"+from+"to:"+to+"shijian:"+shijian1);
			int minutes=0;
			//System.out.println(shijian1);
			if ((shijian1.indexOf("小时")!=-1)&&(shijian1.indexOf("分钟")!=-1))
			{
				String str=shijian1.substring(0,shijian1.indexOf("小时"));
				int hours = Integer.parseInt(str);
				//System.out.println(shijian1);
				//System.out.println(shijian1.indexOf("小时")+2);
				//System.out.println(shijian1.indexOf("分钟"));
				String strMinute=shijian1.substring(shijian1.indexOf("小时")+2,shijian1.indexOf("分钟"));
				minutes= hours*60+Integer.parseInt(strMinute);
			}
			else if ((shijian1.indexOf("小时")==-1)&&(shijian1.indexOf("分钟")!=-1))
			{
				String strMinute=shijian1.substring(0,shijian1.indexOf("分钟"));
				minutes = Integer.parseInt(strMinute);
			}
			else if ((shijian1.indexOf("小时")!=-1)&&(shijian1.indexOf("分钟")==-1))
			{
				String strhours=shijian1.substring(0,shijian1.indexOf("小时"));
				minutes = Integer.parseInt(strhours)*60;
			}
		//	System.out.println("minut:"+minutes);
			
			fromArray[i]=startPid;
			toArray[i]=endPid;
			minuteArray[i]=minutes;
			gongliArray[i]=gongli;
			//System.out.println(from+" "+to+" "+gongli+" "+shijian1+"fenzhong:"+minutes);
			//System.out.println(startPid+" "+endPid+" "+gongli+" "+shijian1+"fenzhong:"+minutes);	
		}
		
		
		//System.out.println(pidArray.length);
		
		double pointx[]=new double[pidArray.length];
		double pointy[]=new double[pidArray.length];
		double pointxpx[]=new double[pidArray.length];
		double pointypx[]=new double[pidArray.length];
		double scale[]=new double[pidArray.length];
		
		int pointid[]=new int[pidArray.length];
		for (int i=0; i<pidArray.length; i++)
		{
			int pid = Integer.parseInt(pidArray[i]);
			Place place=roadMapper.SearchPlaceById(pid);
			//Map<String,Double> map=CalculateJinwei.getLngAndLat(place.getPname());
			pointx[i]=place.getPx();
	        pointy[i]=place.getPy();
	        
	        pointid[i]=pid;
	        scale[i]=1;
		}	
		
		double maxx=MaxMin.Max(pointx,pidArray.length);
		double maxy=MaxMin.Max(pointy,pidArray.length);
		double minx=MaxMin.Min(pointx,pidArray.length);
		double miny=MaxMin.Min(pointy,pidArray.length);
		
		int leftMost=0;
		int rightMost=0;
		for (int i=0; i<pidArray.length; i++)
		{
			if (pointx[i]==minx)
				leftMost=pointid[i];
			if (pointx[i]==maxx)
			rightMost=pointid[i];
			
		}
		
		double cost[]=new double[pidArray.length];
		int time[]=new int[pidArray.length];
		
		//System.out.println("leftMost"+leftMost+"rightMost"+rightMost);
		Place places=null;
		for (int i=0; i<pidArray.length; i++)
		{
			places=roadMapper.SearchPlaceById(pointid[i]);
			cost[i]=places.getCost();
			time[i]=places.getTime();
		}
		
		boolean boolarray[]=new boolean[pidArray.length];
		getPath g=new getPath();
		String s=g.calculated(expecttime, expectmoney, cost, time, pointid, leftMost, rightMost, boolarray,pidArray.length,fromArray,toArray,minuteArray,gongliArray);
		System.out.println("road:"+s);
		
		
		
		
		//for(int q=0; q<pidArray.length; q++) System.out.println(pointid[q]);
		//画图部分
		for (int i=0; i<pidArray.length; i++)
		{
			pointxpx[i]=((pointx[i]-minx)/(maxx-minx))*800+100;
			pointypx[i]=450-((pointy[i]-miny)/(maxy-miny))*400;
		}
		
		double sizeofPicture=120;
		
		for (int i=0; i<pidArray.length; i++)
		{
			for (int j=0; j<pidArray.length; j++)
			{
	
				double distance=Math.sqrt(Math.pow((pointxpx[i]-pointxpx[j]),2)+Math.pow((pointypx[i]-pointypx[j]),2));
				if ((i!=j)&&(distance<sizeofPicture)&&(distance>sizeofPicture/2)) 
					{
					   //scale[i]=sizeofPicture/distance;
					   //scale[j]=sizeofPicture/distance;
					   scale[i]=2;
					   scale[j]=2;
					}
				else if ((i!=j)&&(distance<sizeofPicture/2)&&(distance>sizeofPicture/4))
				    {
					   //scale[i]=sizeofPicture/distance;
					   //scale[j]=sizeofPicture/distance;
					   scale[i]=3;
					   scale[j]=3;
					}
				else if ((i!=j)&&(distance<sizeofPicture/4))
			    {
				   //scale[i]=sizeofPicture/distance;
				   //scale[j]=sizeofPicture/distance;
				   scale[i]=4;
				   scale[j]=4;
				}
			} 
		}
		
		//for (int i=0; i<pidArray.length; i++) System.out.println(scale[i]);
		
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		Map<String, Object> maps=null; 
		
	
			/*for (int i=0; i<dis.length; i++)
			{
				String s1=dis[i].replaceAll("to","到");
				s1=s1.replaceAll("a","约");
				s1=s1.replaceAll("/","行程大约需要");
				s1=s1+",";
				s2=s2+s1;
			}
			System.out.println(s2); */
			
		//导出路线
			   String s2="";
			   double total_time=0;
			   double minut=0;
			   String hotel="";
			   String lunch="";
			   String travel="";
			   String start="";
			   
				String roads="";
				String last="";
				roads=roads+pointid.length+",";
				  String [] paths = null;
				  paths = s.split(" ");
				  for (int i=0; i<pointid.length-1; i++)
				  {
					  
					  int a = Integer.parseInt(paths[i]);
					  int b = Integer.parseInt(paths[i+1]);
					  double lefta=0;
					  double leftb=0;
					  double topa=0;
					  double topb=0; 
					  
					  if (i==0) position=(roadMapper.SearchPlaceById(a)).getPosition();
					               
                      String pname1=(roadMapper.SearchPlaceById(a)).getPname();
					  String pname2=(roadMapper.SearchPlaceById(b)).getPname();
					  System.out.println(pname1+","+pname2);
					  for (int k=0; k<dis.length; k++)
					  {
						  if  ((dis[k].indexOf(pname1)!=-1)&&(dis[k].indexOf(pname2)!=-1))
						  {
							
							    String s1=dis[k].replaceAll("to","到");
								s1=s1.replaceAll("a","约");
								s1=s1.replaceAll("/","行程大约需要");
								s1=s1+",";
								s2=s2+s1;
								//System.out.println("minute"+minuteArray[k]);
								minut=(double)minuteArray[k]/1440;
								
						  }
					  }
					  
					  int time1=(roadMapper.SearchPlaceById(a)).getTime();
					  double time11=(double)time1/10;
					  int time2=(roadMapper.SearchPlaceById(b)).getTime();
					  double time22=(double)time2/10;
					  
					  double money1adult=(roadMapper.SearchPlaceById(a)).getCost();
					  double money1child=(roadMapper.SearchPlaceById(a)).getChildcost();
					  
					  double money2adult=(roadMapper.SearchPlaceById(b)).getCost();
					  double money2child=(roadMapper.SearchPlaceById(b)).getChildcost();
					  
					  double xiaoshi11=time11*24;
					  double xiaoshi22=time22*24;
					  
					  BigDecimal bg1 = new BigDecimal(xiaoshi11);  
			          double xiaoshi1 = bg1.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 
			          
			          BigDecimal bg2 = new BigDecimal(xiaoshi22);  
			          double xiaoshi2 = bg2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					  
					  travel=travel+"游览"+(roadMapper.SearchPlaceById(a)).getPname()+"风景区，预计游玩时间"+xiaoshi1+"小时,"+"景点预期消费为</br>"+"    成人:"+money1adult+"元</br>"+"    儿童："+money1child+"元</br>"+"请尽情游玩！从景区出来以后，自行乘车往下一个景点，路线信息为：#";                  
					 // System.out.println("minut"+minut);
					  //System.out.println("total_time"+total_time);
					  double tot1=total_time+time11;
					  //System.out.println("tot1"+tot1);
					  double tot2=tot1+minut;
					 // System.out.println("tot2"+tot2);
                      double tot3=tot2+time22;
                     // System.out.println("tot3"+tot3);
                      
                      if ((int)tot2>(int)tot1)
                      {
                    	  hotel=hotel+"因为车程较长，无法完成下一个景点的游玩，玩蛇网推荐您暂住在"+(roadMapper.SearchPlaceById(a)).getPname()+"附近的"+(roadMapper.SearchPlaceById(a)).getHotel()+"。#";
                    	  total_time=0;  
                      }
                      else if ((int)tot3>(int)tot2)
                      {
                    	  hotel=hotel+"因为下一个景点的预计游玩时间较长，为了保障您的休息时间，玩蛇网推荐您暂住在"+(roadMapper.SearchPlaceById(a)).getPname()+"附近的"+(roadMapper.SearchPlaceById(a)).getHotel()+"。#";
                    	  total_time=0;
                      }
                      else{
                    	  hotel=hotel+"此时时间还很充裕，不需要在此休息，请尽情前往下一个景点!#";
                    	  total_time=tot3;
                      }
                      
                     
                      if (tot1>0.5) 
                      {
                    	  lunch=lunch+"，游玩期间，玩蛇旅游网推荐您在"+(roadMapper.SearchPlaceById(a)).getPname()+"中的"+(roadMapper.SearchPlaceById(a)).getLunch()+"用餐，享受美食。#";
                      }
                      else lunch=lunch+"，由于从"+(roadMapper.SearchPlaceById(a)).getPname()+"到"+(roadMapper.SearchPlaceById(b)).getPname()+"的车程以及游玩时间不长，您可以游览完下一个景点再用餐。#";
                      
                      if (i==pointid.length-2)
                    	  last=last+"本次定制游旅程的终点站是："+(roadMapper.SearchPlaceById(b)).getPname()+"推荐游玩时间："+xiaoshi2+"小时,"+"景点预期消费为</br>"+"    成人:"+money2adult+"元</br>"+"    儿童："+money2child+"元</br>"+"到此您的旅程结束，衷心希望您有一个良好的旅游体验！";
					  
					  for (int j=0; j<pointid.length; j++)
					  {
						  if(a==pointid[j]) 
						  {
							  lefta=pointxpx[j];
							  topa=pointypx[j];
						  }
					  }
					  for (int j=0; j<pointid.length; j++)
					  {
						  if(b==pointid[j]) 
						  {
							  leftb=pointxpx[j];
							  topb=pointypx[j];
						  }
					  }
					if (i==0){
					     double xiebian=Math.sqrt(Math.pow((leftb-lefta), 2)+Math.pow((topa-topb), 2));
				 	     double sin=(topa-topb)/xiebian;
				   	     double cos=(leftb-lefta)/xiebian;
					     double left1=lefta;
					     double top1=topa;
					     double left2=leftb-75*cos;
					     double top2=topb+75*sin;
					     roads=roads+String.valueOf(left1+sizeofPicture/2)+"px"+","+String.valueOf(top1+sizeofPicture/2)+"px"+",";
					     roads=roads+String.valueOf(left2+sizeofPicture/2)+"px"+","+String.valueOf(top2+sizeofPicture/2)+"px"+",";
					}
					else{
						double xiebian=Math.sqrt(Math.pow((leftb-lefta), 2)+Math.pow((topa-topb), 2));
					 	 double sin=(topa-topb)/xiebian;
					   	 double cos=(leftb-lefta)/xiebian;
					   	 
						 double left1=lefta+30*cos;
						 double top1=topa-30*sin;
					   //	double left1=lefta;
					    // double top1=topa;
						 double left2=leftb-75*cos;
						 double top2=topb+75*sin;
						 roads=roads+String.valueOf(left1+sizeofPicture/2)+"px"+","+String.valueOf(top1+sizeofPicture/2)+"px"+",";
						 roads=roads+String.valueOf(left2+sizeofPicture/2)+"px"+","+String.valueOf(top2+sizeofPicture/2)+"px"+",";
					}
					  
				  }    
				  
				/*  for (int i=0; i<pointid.length; i++)
				  {
					  int a = Integer.parseInt(paths[i]);
					  for (int j=0; j<pointid.length; j++)
					  {
						  if(a==pointid[j]) 
						  {
							  roads=roads+String.valueOf(pointxpx[j]+sizeofPicture/2)+"px"+","+String.valueOf(pointypx[j]+sizeofPicture/2)+"px"+",";
						  }
					  }
					  
				  } */
				  start="您的旅程从"+startPlace+"出发，目的地为"+position+"。";
				  //System.out.println(hotel);
				  //System.out.println(roads);
				  //System.out.println(lunch);
				  
     //导出路线

		
		for (int i=0; i<pidArray.length; i++)
		{
			double width=sizeofPicture/scale[i];
			double radius=(sizeofPicture/2)/scale[i];
			String widthpx=String.valueOf(width)+"px";
			String radiuspx=String.valueOf(radius)+"px";
			maps = new HashMap<String,Object>();
			//System.out.println(pointid[i]);
			Place place=roadMapper.SearchPlaceById(pointid[i]);	
			
			String left=String.valueOf(pointxpx[i])+"px";
			//System.out.println("left:"+left);
			String top=String.valueOf(pointypx[i])+"px";
			//System.out.println("top"+top);
			if (place.getPhoto() != null) {
				String photopath = place.getPhoto();
				String [] path = null;
				path = photopath.split(",");	 
				maps.put("pid",pointid[i]);
				//System.out.println("left:"+left);
				//System.out.println("top"+top);
				maps.put("photo","<img id='"+pointid[i]+"' title='"+place.getPname()+"' width='"+widthpx+"' height='"+widthpx+"' style='border-radius:"+radiuspx+"; position:absolute; z-index:1; left:"+left+"; top:"+top+";' src = 'upload/"+path[0]+"' />");
				maps.put("roads",roads);
				maps.put("information",s2);
				maps.put("hotel", hotel);
				maps.put("lunch", lunch);
				maps.put("travel", travel);
				maps.put("last", last);
				maps.put("start", start);
				
			}		
			res.add(maps);
		}
		//画图部分
		
		
		
	
   return res;
}
	

}
