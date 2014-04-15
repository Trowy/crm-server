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

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
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
			List<Employee> l = s.getEmployees();
			String res = "";
			for (Employee employee : l) {
				res += employee.toJson()+",";
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
		try {
			Service s = new Service();
			
		switch(request.getParameter("action")){
		
			case "new":
				Employee e = new Employee(0, 
						request.getParameter("login"),
						request.getParameter("password"),
						request.getParameter("first_name"),
						request.getParameter("middle_name"),
						request.getParameter("last_name"),
						request.getParameter("role").charAt(0),
						request.getParameter("email"));
						
				
				s.addEmployee(e);
						
						break;
			case "edit":
				
				Employee e_e = new Employee(Integer.parseInt(request.getParameter("id")), 
						request.getParameter("login"),
						request.getParameter("password"),
						request.getParameter("first_name"),
						request.getParameter("middle_name"),
						request.getParameter("last_name"),
						request.getParameter("role").charAt(0),
						request.getParameter("email"));
						
						
						s.editEmployee(e_e);
						
						break;
			case "delete":
				s.removeEmployee(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
	}

}
