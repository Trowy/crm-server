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
import entity.Employee;
import entity.EventType;


@WebServlet("/event_types")
public class EventTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EventTypeServlet() {
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
        if(request.getSession().getAttribute("employee_id") != null){
        try {
			Service s = new Service();
			List<EventType> l = s.getEventTypes();
			String res = "";
			if(l.size()==0){res=",";}
			for (EventType eventType : l) {
				res += eventType.toJson()+",";
			}			
			res = res.substring(0, res.length()-1);
			response.getWriter().print("{'data':["+res+"]}");
		} catch (CRMException e) {
			response.getWriter().print("{'data':[]}");
		}       
        
        response.getWriter().flush();
        response.getWriter().close();
        }
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
        if(request.getSession().getAttribute("employee_id") != null){
		try {
			Service s = new Service();
			
		switch(request.getParameter("action")){
		
			case "new":
				EventType e = new EventType(0, request.getParameter("name"), request.getParameter("info"));
						
				
				s.addEventType(e);
						
						break;
			case "edit":
				
				EventType e_e = new EventType(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("info"));
						
						
						s.editEventType(e_e);
						
						break;
			case "delete":
				s.removeEventType(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
        }
		
	}

}
