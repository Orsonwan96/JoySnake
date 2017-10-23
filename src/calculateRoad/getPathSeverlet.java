package calculateRoad;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import calculateRoad.getPath;
import net.sf.json.JSONArray;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class getPathSeverlet
 */
@WebServlet("/getPathSeverlet")
public class getPathSeverlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getPathSeverlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("gbk");
		
		String library=request.getParameter("library");
		String lake=request.getParameter("lake");
		String lunch=request.getParameter("lunch");
		String bridge=request.getParameter("bridge");
		String hotel=request.getParameter("hotel");
		
		boolean boolarray[]=new boolean[6];
		for (int i=0; i<6; i++) boolarray[i]=false;
		if (hotel.equals("true")) boolarray[1]=true;
		if (lunch.equals("true")) boolarray[2]=true;
		if (lake.equals("true")) boolarray[3]=true;
		if (bridge.equals("true")) boolarray[4]=true;
		if (library.equals("true")) boolarray[5]=true;
		
		for (int i=1; i<6; i++) System.out.print(boolarray[i]);
		
		getPath g=new getPath();
		//String s=g.calculated(boolarray,6);
		PrintWriter pw=response.getWriter();
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		//map.put("road",s);
		list.add(map);
		JSONArray j=JSONArray.fromObject(list);
		pw.print(j.toString());
		
	}

}
