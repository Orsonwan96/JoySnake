package Jisuanjinwei;

public class JinweiZhuanhuan {  
    public static double[] MillierConvertion(double lat, double lon)  
    {  
         double L = 6381372 * Math.PI * 2;//�����ܳ�  
         double W=L;// ƽ��չ����x������ܳ�  
         double H=L/2;// y��Լ�����ܳ�һ��  
         double mill=2.3;// ����ͶӰ�е�һ����������Χ��Լ������2.3֮��  
         double x = lon * Math.PI / 180;// �����ȴӶ���ת��Ϊ����  
         double y = lat * Math.PI / 180;// ��γ�ȴӶ���ת��Ϊ����  
         y=1.25 * Math.log( Math.tan( 0.25 * Math.PI + 0.4 * y ) );// ����ͶӰ��ת��  
         // ����תΪʵ�ʾ���  
         x = ( W / 2 ) + ( W / (2 * Math.PI) ) * x;  
         y = ( H / 2 ) - ( H / ( 2 * mill ) ) * y;  
         double[] result=new double[2];  
         result[0]=x;  
         result[1]=y;  
         return result;  
    }  
}  