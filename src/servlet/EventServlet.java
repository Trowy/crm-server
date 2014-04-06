package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CRMException;
import service.Service;
import entity.Attachment;
import entity.Event;
import entity.EventStatus;
import entity.EventType;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/events")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        
        response.addHeader("Content-Type","application/json");
        
        try {
			Service s = new Service();
			List<Event> l = s.getEvents(0, null, 0,1,121,false,0,"");
			String res = "";
			for (Event event : l) {
				res += event.toJson()+",";
			}			
			res = res.substring(0, res.length()-1);
			response.getWriter().print("{'data':["+res+"]}");
		} catch (CRMException e) {
			response.getWriter().print("{'data':[]}");
		}       
        
        response.getWriter().flush();
        response.getWriter().close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
				Event e = new Event(0, 
						request.getParameter("title"),
						request.getParameter("info"),
						new java.sql.Date(114, 1, 2),
						Integer.parseInt(request.getParameter("duration")),
						new ArrayList<Attachment>(),
						new EventStatus(Integer.parseInt(request.getParameter("eventStatus")), "", ""),
						new EventType(Integer.parseInt(request.getParameter("eventType")), "", ""),
						s.getCompany(Integer.parseInt(request.getParameter("company"))),
						s.getContractor(Integer.parseInt(request.getParameter("contractor")))
						);
						
				
				s.addEvent(e);
						
						break;
			case "edit":
				
				Event e_e = new Event(Integer.parseInt(request.getParameter("id")), 
						request.getParameter("title"),
						request.getParameter("info"),
						new java.sql.Date(114, 1, 2),
						Integer.parseInt(request.getParameter("duration")),
						new ArrayList<Attachment>(),
						new EventStatus(Integer.parseInt(request.getParameter("eventStatus")), "", ""),
						new EventType(Integer.parseInt(request.getParameter("eventType")), "", ""),
						s.getCompany(Integer.parseInt(request.getParameter("company"))),
						s.getContractor(Integer.parseInt(request.getParameter("contractor")))
						);
						
						s.editEvent(e_e);
						
						break;
			case "delete":
				s.removeEmployee(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		
	}

}