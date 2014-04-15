package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.CRMException;
import service.Service;
import entity.Employee;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthServlet() {    	
        super();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","GET, PUT, POST, DELETE, OPTIONS");
        response.addHeader("Access-Control-Max-Age","000");
        response.addHeader("Access-Control-Allow-Headers","Content-Type, Authorization, X-Requested-With");
        response.addHeader("Content-Type","application/json");
                        
		if(request.getParameter("employee_id") != null && !request.getParameter("employee_id").equals("undefined")){
			response.getWriter().print("{success: true, employee_id: '"+Integer.parseInt(request.getParameter("employee_id"))+"', employee_role: 'S'}");
		}else{
			response.getWriter().print("{success: false}");       
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
		
        response.setContentType("application/json; charset=windows-1251");        
        
		try {
			
			Service s = new Service();			
			Employee e = s.auth(request.getParameter("auth_login"), request.getParameter("auth_pass"));			
			response.getWriter().print("{success:true, employee_id: '"+e.getId()+"', employee_role: '"+e.getRole()+"'}");			
						
		} catch (CRMException e) {
			response.getWriter().print("{success:false, errors: {auth_login:'"+e.getMessage()+"', auth_pass:'"+e.getMessage()+"'}}");			
		}
		
		response.getWriter().flush();
        response.getWriter().close();
		
	}

}
