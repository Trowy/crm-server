package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import service.CRMException;
import service.Service;
import entity.Attachment;
import entity.Employee;
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
        	
        	Calendar cal = Calendar.getInstance();
        	
        	
        	
        	
			Service s = Service.getService();
			int type=Integer.parseInt(request.getParameter("type"));
			
			if(type==2 || type==5 || type==8){
        		cal.add(Calendar.DATE, -7);            	
        	}
			
			if(type==3 || type==6 || type==9){
        		cal.add(Calendar.DATE, -1);          	
        	
			}
			
			Employee e = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));
			
			int e_id;
			
			if(e.getRole()=='M'){
				e_id = e.getId();
			}else{
				e_id=0;
			}
			
			List<Event> l = s.getEvents(e_id, null, 0,1000,1,false,0,"");
			if(type==1){l = s.getEvents(e_id, null, 0,1000,1,false,0,"");}
			if(type==2){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,0,"");}
			if(type==3){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,0,"");}
			if(type==4){l = s.getEvents(e_id, null, 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==5){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==6){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,8,request.getParameter("comp"));}
			if(type==7){l = s.getEvents(e_id, null, 0,1000,1,false,9,request.getParameter("cnt"));}
			if(type==8){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,9,request.getParameter("cnt"));}
			if(type==9){l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0,1000,1,false,9,request.getParameter("cnt"));}			
			
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//if(request.getHeader("Origin").contains("http://crm.local")){
        	response.addHeader("Access-Control-Allow-Origin","http://crm.local");
        //}else{
        	//response.addHeader("Access-Control-Allow-Origin","http://crm-tusur.6te.net");
        //}
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251");
        if(request.getSession().getAttribute("employee_id") != null){
		try {
			String fieldvalue = "";
			Service s = Service.getService();
			System.out.println(request.getParameter("action"));
			String[] date;
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
	            
				if(item.getFieldName().equals("action")){
					fieldvalue = item.getString();
				}
	                        
	                
	            
	            
	        }
		switch(fieldvalue){
		
			case "new":
			
				 for (FileItem item : items) {
			            if (item.isFormField()) {
			                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
			                String fieldname = item.getFieldName();
			                String fieldvalue1 = item.getString();
			                // ... (do your job here)
			            } else {
			                // Process form file field (input type="file").
			                String fieldname = item.getFieldName();
			                String filename = FilenameUtils.getName(item.getName());
			                InputStream filecontent = item.getInputStream();
			                response.getWriter().print(fieldname+" "+filename+"<br>");
			            }
			        }
			/*
				date = request.getParameter("date").split("-");
				Event e = new Event(0, 
						request.getParameter("title"),
						request.getParameter("info"),
						new java.sql.Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])),
						//new java.sql.Date(Calendar.getInstance().getTimeInMillis()),
						Integer.parseInt(request.getParameter("duration")),
						new ArrayList<Attachment>(),
						new EventStatus(Integer.parseInt(request.getParameter("eventStatus")), "", ""),
						new EventType(Integer.parseInt(request.getParameter("eventType")), "", ""),
						s.getCompany(Integer.parseInt(request.getParameter("company"))),
						s.getContractor(Integer.parseInt(request.getParameter("contractor")))
						);
					
				
				s.addEvent(e);
				*/
				
						break;
			case "edit":
				date = request.getParameter("date").split("-");
				Event e_e = new Event(Integer.parseInt(request.getParameter("id")), 
						request.getParameter("title"),
						request.getParameter("info"),
						new java.sql.Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])),
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
		} catch (FileUploadException e1) {
			
			}
		response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
        }
	}

}
