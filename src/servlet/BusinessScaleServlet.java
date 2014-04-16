package servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CRMException;
import service.Service;
import entity.BusinessScale;
import entity.Employee;


@WebServlet("/business_scales")
public class BusinessScaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BusinessScaleServlet() {
        super();        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getHeader("Origin").contains("http://crm.local")){
        	response.addHeader("Access-Control-Allow-Origin","http://crm.local");
        }else{
        	response.addHeader("Access-Control-Allow-Origin","http://crm-tusur.6te.net");
        }
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251");
        
        try {
			Service s = new Service();
			List<BusinessScale> l = s.getBusinessScales();
			String res = "";
			if(l.size()==0){res=",";}
			for (BusinessScale businessScale : l) {
				res += businessScale.toJson()+",";
			}			
			res = res.substring(0, res.length()-1);
			response.getWriter().print("{'data':["+res+"]}");
		} catch (CRMException e) {
			response.getWriter().print("{'data':[]}");
		}       
        
        response.getWriter().flush();
        response.getWriter().close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getHeader("Origin").contains("http://crm.local")){
        	response.addHeader("Access-Control-Allow-Origin","http://crm.local");
        }else{
        	response.addHeader("Access-Control-Allow-Origin","http://crm-tusur.6te.net");
        }
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251");
		try {
			Service s = new Service();
			
		switch(request.getParameter("action")){
		
			case "new":
				BusinessScale e = new BusinessScale(0, request.getParameter("name"), request.getParameter("info"));
						
				
				s.addBusinessScale(e);
						
						break;
			case "edit":
				
				BusinessScale e_e = new BusinessScale(
						Integer.parseInt(request.getParameter("id")),
						request.getParameter("name"), request.getParameter("info"));
										
				s.editBusinessScale(e_e);
						
						break;
			case "delete":
				s.removeBusinessScale(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		
		response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
		
		
	}

}
