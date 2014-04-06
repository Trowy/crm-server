package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CRMException;
import service.Service;
import entity.BusinessScale;
import entity.City;
import entity.Company;
import entity.CompanyStatus;
import entity.Employee;
import entity.Segment;
import entity.Tag;

/**
 * Servlet implementation class CompanyServlet
 */
@WebServlet("/companies")
public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyServlet() {
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
			List<Company> l = s.getCompanies(2, 0, 2, 1,
					false, 0, "");
			String res = "";
			for (Company company : l) {
				res += company.toJson()+",";
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

				Company c = new Company(0,
						request.getParameter("name"), 
						request.getParameter("info"), 
						request.getParameter("site"),
						new ArrayList<String>(Arrays.asList(request.getParameter("phones").split(","))),//phones, 
						new ArrayList<String>(Arrays.asList(request.getParameter("emails").split(","))), 
						new ArrayList<String>(Arrays.asList(request.getParameter("skypes").split(","))), 
						new City(Integer.parseInt(request.getParameter("City")), "", 0, 0), 
						new Segment(Integer.parseInt(request.getParameter("City")), "", ""), 
						new CompanyStatus(Integer.parseInt(request.getParameter("CompanyStatus")), "", ""),
						new BusinessScale(Integer.parseInt(request.getParameter("BusinessScale")), "", ""),
						new Employee(Integer.parseInt(request.getParameter("Employee")), "", "", "", "", "", 'S', ""), 
						new ArrayList<Tag>());
				
									
				
				s.addCompany(c);
						
						break;
			case "edit":
				Company c_c = new Company(Integer.parseInt(request.getParameter("id")),
						request.getParameter("name"), 
						request.getParameter("info"), 
						request.getParameter("site"),
						new ArrayList<String>(Arrays.asList(request.getParameter("phones").split(","))), 
						new ArrayList<String>(Arrays.asList(request.getParameter("emails").split(","))), 
						new ArrayList<String>(Arrays.asList(request.getParameter("skypes").split(","))), 
						new City(Integer.parseInt(request.getParameter("City")), "", 0, 0), 
						new Segment(Integer.parseInt(request.getParameter("City")), "", ""), 
						new CompanyStatus(Integer.parseInt(request.getParameter("CompanyStatus")), "", ""),
						new BusinessScale(Integer.parseInt(request.getParameter("BusinessScale")), "", ""),
						new Employee(Integer.parseInt(request.getParameter("Employee")), "", "", "", "", "", 'S', ""), 
						new ArrayList<Tag>());
				s.editCompany(c_c);
						break;
			case "delete":
				s.removeEmployee(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
	}

}
