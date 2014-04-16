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
		response.addHeader("Access-Control-Allow-Origin","http://crm.local");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251");
        
        try {
			Service s = new Service();
			int type=Integer.parseInt(request.getParameter("type"));
			List<Event> l = s.getEvents(0, null, 0,1,121,false,0,"");
			if(type==1){l = s.getEvents(0, null, 0,1,121,false,0,"");}
			if(type==2){l = s.getEvents(0, new java.sql.Date(114, 0, 1), 0,1000,1,false,0,"");}
			if(type==3){l = s.getEvents(0, new java.sql.Date(114, 1, 1), 0,1000,1,false,0,"");}
			if(type==4){l = s.getEvents(0, null, 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==5){l = s.getEvents(0, new java.sql.Date(114, 0, 1), 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==6){l = s.getEvents(0, new java.sql.Date(114, 1, 1), 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==7){l = s.getEvents(0, null, 0,1000,1,false,9,request.getParameter("cnt"));}
			if(type==8){l = s.getEvents(0, new java.sql.Date(114, 0, 1), 0,1000,1,false,9,request.getParameter("cnt"));}
			if(type==9){l = s.getEvents(0, new java.sql.Date(114, 1, 1), 0,1000,1,false,9,request.getParameter("cnt"));}			
			
			//List<Event> l = s.getEvents(0, null, 0,1,121,false,0,"");
			String res = "";
			if(l.size()==0){res=",";}
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
		response.addHeader("Access-Control-Allow-Origin","http://crm.local");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251");
        
		try {
			Service s = new Service();
			System.out.println(request.getParameter("action"));
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
				s.removeEvent(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		
	}

}
