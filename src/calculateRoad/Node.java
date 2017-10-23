package calculateRoad;

import java.util.ArrayList;  

public class Node {
	public int id;
	public int spend_Money=0;
	public int spend_Time=0;
	public int pid=0;
	public ArrayList<Node> relationNodes = new ArrayList<Node>();
	
    public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	  
  
    
  
    public int getSpend_Money() {
		return spend_Money;
	}

	public void setSpend_Money(int spend_Money) {
		this.spend_Money = spend_Money;
	}

	public int getSpend_Time() {
		return spend_Time;
	}

	public void setSpend_Time(int spend_Time) {
		this.spend_Time = spend_Time;
	}

	public ArrayList<Node> getRelationNodes() {  
        return relationNodes;  
    }  
  
    public void setRelationNodes(ArrayList<Node> relationNodes) {  
        this.relationNodes = relationNodes;  
    }  
}
