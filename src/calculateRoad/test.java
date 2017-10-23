package calculateRoad;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        int arr[][]=new int[9][9];
        for (int i=0; i<9; i++)
        {      //i=5
        	int k=0;
        	for (int j=0; j<8; j++)
        	{
        		if (i!=j) {arr[i][j]=k; k++;}
        		else {arr[i][j]=k+1; 
        		k+=2; }
        	}
        }
        
        for (int i=0; i<9; i++)
        {      //i=5
        	for (int j=0; j<8; j++)
        	{
        		System.out.print(arr[i][j]+" ");
        	}
        	System.out.println(" ");
        }
        
	}

}
