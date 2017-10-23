package com.ssm.filter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
@SuppressWarnings("serial")
public class SpringFilter extends HttpServlet implements Filter {
    //�����ַ����뼶,value��web.xml��ֵ��
	private String encoding=null;
	//�Ƿ������������ַ����Ĳ���
	private boolean enable=true;
	//Filter�ӿ�ʵ����    ������Httpservlet����
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
        if(enable&&encoding!=null){
        	arg0.setCharacterEncoding(encoding);
        	arg1.setCharacterEncoding(encoding);
        	arg2.doFilter(arg0, arg1);        	
        }
	}
//	<filter>
//    <filter-name>filter</filter-name>
//    <filter-class>com.ssm.filter.SpringFilter</filter-class>
//    <init-param>
//       <param-name>Encoding</param-name>
//       <param-value>UTF-8</param-value>
//    </init-param>
//    <init-param>
//       <param-name>enable</param-name>
//       <param-value>true</param-value>
//    </init-param>
//  </filter>
//  <filter-mapping>
//    <filter-name>filter</filter-name>
//    <url-pattern>/*</url-pattern>
//  </filter-mapping> 
	//HttpServlet��д����ķ���
	@Override
	public void init(FilterConfig config) throws ServletException {
		//����web.xml�����ļ�����ֵ����������ʼ����ֵ
        this.encoding=config.getInitParameter("Encoding");
        //true "true"
        enable="true".equalsIgnoreCase(config
        		.getInitParameter("enable").trim());
	}
	//HttpServlet��д����ķ���
    @Override
    public void destroy() {
    	this.encoding=null;
    	this.enable=false;
    }
}