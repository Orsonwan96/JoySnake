package calculateRoad;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.Stack;
import calculateRoad.Node;

public class getPath {
	static int number_of_road=0;
	
	public static Stack<Node> stack = new Stack<Node>();  
    /* �洢·���ļ��� */  
    public static ArrayList<Object[]> sers = new ArrayList<Object[]>();  
  
    /* �жϽڵ��Ƿ���ջ�� */  
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
  
    /* ��ʱջ�еĽڵ����һ������·����ת������ӡ��� */  
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
        
        sers.add(o); /* ת�� */  
       
    }  
  
    /* 
     * Ѱ��·���ķ���  
     * cNode: ��ǰ����ʼ�ڵ�currentNode 
     * pNode: ��ǰ��ʼ�ڵ����һ�ڵ�previousNode 
     * sNode: �������ʼ�ڵ�startNode 
     * eNode: �յ�endNode 
     */  
    public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode,int time, int money, int M[], int T[], int distance[][], boolean bool[]) {  
        
	    Node nNode = null;  
        /* ������������ж�˵�����ֻ�·��������˳�Ÿ�·������Ѱ·������false */  
        if (cNode != null && pNode != null && cNode == pNode)  
            return false;  
  
        if (cNode != null) {  
            int i = 0;  
            /* ��ʼ�ڵ���ջ */  
            stack.push(cNode);  
            /* �������ʼ�ڵ�����յ㣬˵���ҵ�һ��·�� */  
            if (cNode == eNode)  
            {  
                /* ת������ӡ�����·��������true */  
                showAndSavePath(M, T, distance, bool);  
                number_of_road++;
                return true;  
            }  
            /* �������,����Ѱ· */  
            else  
            {  
                /*  
                 * ���뵱ǰ��ʼ�ڵ�cNode�����ӹ�ϵ�Ľڵ㼯�а�˳������õ�һ���ڵ� 
                 * ��Ϊ��һ�εݹ�Ѱ·ʱ����ʼ�ڵ�  
                 */  
                nNode = cNode.getRelationNodes().get(i);  
                while (nNode != null) {  
                    /* 
                     * ���nNode���������ʼ�ڵ����nNode����cNode����һ�ڵ����nNode�Ѿ���ջ�� ��  
                     * ˵��������· ��Ӧ�������뵱ǰ��ʼ�ڵ������ӹ�ϵ�Ľڵ㼯��Ѱ��nNode 
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
                    /* ��nNodeΪ�µ���ʼ�ڵ㣬��ǰ��ʼ�ڵ�cNodeΪ��һ�ڵ㣬�ݹ����Ѱ·���� */  
                    if (getPaths(nNode, cNode, sNode, eNode, time, money, M, T, distance, bool))/* �ݹ���� */  
                    {  
                        /* ����ҵ�һ��·�����򵯳�ջ���ڵ� */  
                        stack.pop();  
                    }  
                    /* ��������cNode�����ӹ�ϵ�Ľڵ㼯�в���nNode */  
                    i++;  
                    if (i >= cNode.getRelationNodes().size())  
                        nNode = null;  
                    else  
                        nNode = cNode.getRelationNodes().get(i);  
                }  
                /*  
                 * ��������������cNode�����ӹ�ϵ�Ľڵ�� 
                 * ˵������cNodeΪ��ʼ�ڵ㵽�յ��·���Ѿ�ȫ���ҵ�  
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
		
	        /* ����ڵ����� */  
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
	        
	        
	        /* ������ڵ�������Ľڵ㼯�� */  
	        for(int i=0;i<len;i++)  
	        {  
	            ArrayList<Node> List = new ArrayList<Node>();  
	              
	            for(int j=0;j<len-1;j++)  
	            {  
	                List.add(node[nodeRalation[i][j]]);  
	            }  
	            node[i].setRelationNodes(List);  
	            List = null;  //�ͷ��ڴ�  
	        }  
	        //ȷ�������ѵ�ʱ��ͽ�Ǯ
	        int total_time=expecttime; int total_money=expectmoney;  
	        int M[]=new int[1000];
	        int T[]=new int[1000];
	        int score[]=new int[1000];
	        for (int i=0; i<1000; i++) {M[i]=0; T[i]=0; score[i]=1000;}
	        
	  	    /* ��ʼ��������·�� */  
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
