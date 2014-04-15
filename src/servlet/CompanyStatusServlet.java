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
import entity.CompanyStatus;


@WebServlet("/company_statuses")
public class CompanyStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompanyStatusServlet() {
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
			List<CompanyStatus> l = s.getCompanyStatuses();
			String res = "";
			for (CompanyStatus companyStatus : l) {
				res += companyStatus.toJson()+",";
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
				CompanyStatus e = new CompanyStatus(0,  request.getParameter("name"), request.getParameter("info"));
						
				
				s.addCompanyStatus(e);
						
						break;
			case "edit":
				
				CompanyStatus e_e = new CompanyStatus(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("info"));
						
						
						s.editCompanyStatus(e_e);
						
						break;
			case "delete":
				s.removeCompanyStatus(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
		
	}

}
