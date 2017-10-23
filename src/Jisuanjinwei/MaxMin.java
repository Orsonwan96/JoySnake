package Jisuanjinwei;

public class MaxMin {
	
	public static double Max(double point[], int len)
	{
		double max=-1;
		for (int i=0; i<len; i++)
		{
			if (point[i]>max) max=point[i];
		}
		return max;
	}
	
	public static double Min(double point[], int len)
	{
		double min=10000;
		for (int i=0; i<len; i++)
		{
			if (point[i]<min) min=point[i];
		}
		return min;
	}

}
