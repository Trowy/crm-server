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
import entity.EventStatus;


@WebServlet("/event_statuses")
public class EventStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EventStatusServlet() {
        super();        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        
        response.addHeader("Content-Type","application/json");
        
        try {
			Service s = new Service();
			List<EventStatus> l = s.getEventStatuses();
			String res = "";
			for (EventStatus eventStatus : l) {
				res += eventStatus.toJson()+",";
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
		response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        
        response.addHeader("Content-Type","application/json");
		try {
			Service s = new Service();
			
		switch(request.getParameter("action")){
		
			case "new":
				EventStatus e = new EventStatus(0, request.getParameter("name"), request.getParameter("info"));
						
				
				s.addEventStatus(e);
						
						break;
			case "edit":
				
				EventStatus e_e = new EventStatus(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("info"));
						
						
						s.editEventStatus(e_e);
						
						break;
			case "delete":
				s.removeEventStatus(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
		
		
	}

}
