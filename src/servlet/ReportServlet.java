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
import entity.Segment;

/**
 * Servlet implementation class ReportServlet
 */
@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
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
				Service s = Service.getService();
				Employee e3 = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));
	        	
				
		        if(e3.getRole()=='S'){
				int type=Integer.parseInt(request.getParameter("type"));
				response.getWriter().print(s.generateReport(type));
		        }
			} catch (CRMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
        
        response.getWriter().flush();
        response.getWriter().close();
        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
	}

}
