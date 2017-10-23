package calculateRoad;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.Stack;
import calculateRoad.Node;

public class getPath {
	static int number_of_road=0;
	
	public static Stack<Node> stack = new Stack<Node>();  
    /* 存储路径的集合 */  
    public static ArrayList<Object[]> sers = new ArrayList<Object[]>();  
  
    /* 判断节点是否在栈中 */  
    public static boolean isNodeInStack(Node node)  
    {  
        Iterator<Node> it = stack.iterator();  
        while (it.hasNext()) {  
            Node node1 = (Node) it.next();  
            if (node == node1)  
                return true;  
        }  
        return false;  
    }  
  
    /* 此时栈中的节点组成一条所求路径，转储并打印输出 */  
    public static void showAndSavePath(int m[], int t[], int distance[][], boolean bool[])  
    {  
        Object[] o = stack.toArray();  
        for (int i = 0; i < o.length; i++) {  
            Node nNode = (Node) o[i];  
            m[number_of_road]+=nNode.spend_Money;
            t[number_of_road]+=nNode.spend_Time;
          /*  if(i < (o.length - 1))  
                System.out.print(nNode.getPid() + "->");  
            else  
                System.out.print(nNode.getPid());   */
            if (i>0)
            {
            	int idnow=nNode.getId();
            	Node preNode=(Node) o[i-1];
            	int idpre=preNode.getId();
            	//m[number_of_road]+=distance[idnow][idpre];
            	t[number_of_road]+=distance[idnow][idpre];
            }
        }  
        
        sers.add(o); /* 转储 */  
       
    }  
  
    /* 
     * 寻找路径的方法  
     * cNode: 当前的起始节点currentNode 
     * pNode: 当前起始节点的上一节点previousNode 
     * sNode: 最初的起始节点startNode 
     * eNode: 终点endNode 
     */  
    public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode,int time, int money, int M[], int T[], int distance[][], boolean bool[]) {  
        
	    Node nNode = null;  
        /* 如果符合条件判断说明出现环路，不能再顺着该路径继续寻路，返回false */  
        if (cNode != null && pNode != null && cNode == pNode)  
            return false;  
  
        if (cNode != null) {  
            int i = 0;  
            /* 起始节点入栈 */  
            stack.push(cNode);  
            /* 如果该起始节点就是终点，说明找到一条路径 */  
            if (cNode == eNode)  
            {  
                /* 转储并打印输出该路径，返回true */  
                showAndSavePath(M, T, distance, bool);  
                number_of_road++;
                return true;  
            }  
            /* 如果不是,继续寻路 */  
            else  
            {  
                /*  
                 * 从与当前起始节点cNode有连接关系的节点集中按顺序遍历得到一个节点 
                 * 作为下一次递归寻路时的起始节点  
                 */  
                nNode = cNode.getRelationNodes().get(i);  
                while (nNode != null) {  
                    /* 
                     * 如果nNode是最初的起始节点或者nNode就是cNode的上一节点或者nNode已经在栈中 ，  
                     * 说明产生环路 ，应重新在与当前起始节点有连接关系的节点集中寻找nNode 
                     */  
                    if (pNode != null  
                            && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {  
                        i++;  
                        if (i >= cNode.getRelationNodes().size())  
                            nNode = null;  
                        else  
                            nNode = cNode.getRelationNodes().get(i);  
                        continue;  
                    }  
                    /* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */  
                    if (getPaths(nNode, cNode, sNode, eNode, time, money, M, T, distance, bool))/* 递归调用 */  
                    {  
                        /* 如果找到一条路径，则弹出栈顶节点 */  
                        stack.pop();  
                    }  
                    /* 继续在与cNode有连接关系的节点集中测试nNode */  
                    i++;  
                    if (i >= cNode.getRelationNodes().size())  
                        nNode = null;  
                    else  
                        nNode = cNode.getRelationNodes().get(i);  
                }  
                /*  
                 * 当遍历完所有与cNode有连接关系的节点后， 
                 * 说明在以cNode为起始节点到终点的路径已经全部找到  
                 */  
                stack.pop();  
                return false;  
            }  
        } else  
            return false;  
    }  
	public String calculated(int expecttime, int expectmoney, double cost[], int time[], int pointid[], int leftMost, int rightMost, boolean bool[], int len, int from[], int to[], int minute[], String gongli[]) {
		sers.clear();
		number_of_road=0;
	/*	int nodeRalation[][] =  
	        {  
	            {1,2},      //0  
	            {0,2,3,5},//1  
	            {0,1,3,4},    //2  
	            {1,2,4,5,6},    //3  
	            {2,3,6},  //4  
	            {1,3,6},     //5  
	            {3,4,5}      // 6
	        };     */
		for (int i=0; i<len; i++) bool[i]=true;
		int nodeRalation[][]=new int[len][len-1];   
		for (int i=0; i<len; i++)
        {   
        	int k=0;
        	for (int j=0; j<len-1; j++)
        	{
        		if (i!=j) {nodeRalation[i][j]=k; k++;}
        		else {nodeRalation[i][j]=k+1; 
        		k+=2; }
        	}
        }
		
	        /* 定义节点数组 */  
	        Node[] node = new Node[len];  
	          
	        for(int i=0;i<len;i++)  
	        {  
	            node[i] = new Node();  
	            
	        }  
	        //initialize
	        for (int i=0; i<len; i++)
	        {
	        	node[i].setPid(pointid[i]);
	        	node[i].setId(i);
	        	int costint=(int)cost[i];
	        	node[i].setSpend_Money(costint);
	        	node[i].setSpend_Time(time[i]);
	        }
        
	        int distance[][]=new int[len][len];
			for (int i=0; i<len; i++)
			{
				for (int j=0; j<len; j++)
				{
					distance[i][j]=0;
				}
			}

			for (int i=0; i<from.length; i++)
			{
				int fromId=-1; int toId=-1; 
				for (int j=0; j<len; j++)
				{
					if (node[j].pid==from[i]) fromId=j;
				}
				for (int j=0; j<len; j++)
				{
					if (node[j].pid==to[i]) toId=j;
				}

				distance[fromId][toId]=minute[i];
				distance[toId][fromId]=minute[i];
			}
			
			/*for (int i=0; i<len; i++)
	        {    
	        	for (int j=0; j<len; j++)
	        	{
	        		System.out.print(distance[i][j]+" ");
	        	}
	        	System.out.println();
	        }      */
	        
	        
	        /* 定义与节点相关联的节点集合 */  
	        for(int i=0;i<len;i++)  
	        {  
	            ArrayList<Node> List = new ArrayList<Node>();  
	              
	            for(int j=0;j<len-1;j++)  
	            {  
	                List.add(node[nodeRalation[i][j]]);  
	            }  
	            node[i].setRelationNodes(List);  
	            List = null;  //释放内存  
	        }  
	        //确定所花费的时间和金钱
	        int total_time=expecttime; int total_money=expectmoney;  
	        int M[]=new int[1000];
	        int T[]=new int[1000];
	        int score[]=new int[1000];
	        for (int i=0; i<1000; i++) {M[i]=0; T[i]=0; score[i]=1000;}
	        
	  	    /* 开始搜索所有路径 */  
	        int startNodeId=-1;
	        int endNodeId=-1;
	        
	        for (int j=0; j<len; j++)
			{
				if (node[j].pid==leftMost) startNodeId=j;
			}
	        for (int j=0; j<len; j++)
			{
				if (node[j].pid==rightMost) endNodeId=j;
			}
	        getPaths(node[startNodeId], null, node[startNodeId], node[endNodeId], total_time, total_money, M, T, distance, bool);  
	      //  System.out.println(number_of_road);
	        
	        System.out.println(sers.size());
	        
	        for (int k=0; k<sers.size(); k++)
	        {
	        	String m=new String("");
	        	Object[] l = sers.get(k);
	        	for (int h=0; h<l.length; h++)
	        	{
	        		Node pNode = (Node) l[h];  
		            m=m+String.valueOf(pNode.getPid());  // 11 9 7 4
	        	}
	        	boolean panduan=true;
	        	for (int i=1; i<len-1; i++)
	        	{
	        		System.out.println(i+": "+bool[i]);
	        		if (bool[i]==true)
	        		{
	        			
	        			String ss = String.valueOf(node[i].getPid());
	        		//	System.out.println("m:"+m);
	        		//	System.out.println(ss);
	        			if (m.indexOf(ss)==-1) panduan=false;
	        			
	        		}
	        	}
	        //	System.out.println("panduan:"+panduan);
	        	if   (panduan==false) //         ((panduan==false)||(M[k]>total_money)||(T[k]>total_time))
	        	{	
	        		score[k]=-1;	
	        	}
	        	
	        	else score[k]=M[k]+T[k]*30;
	        	//System.out.println("SCORE:"+score[k]);
	        }  
	        //find the best road
	        int temp=0;
	        int road=0;
	        for (int k=0; k<sers.size(); k++)
	        {
	        	if (score[k]>temp) 
	        	{
	        		temp=score[k]; 
	        		 road=k;  
	        	}
	        }
	        String a=new String("");
	        Object[] o = sers.get(road);
	        for (int i = 0; i < o.length; i++) {  
	            Node nNode = (Node) o[i];  
	            a=a+String.valueOf(nNode.getPid());
	            a=a+" ";
	        }
	        return a;
	    }  
}
