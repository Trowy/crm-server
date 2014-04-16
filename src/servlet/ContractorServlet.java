package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CRMException;
import service.Service;
import entity.Contractor;

/**
 * Servlet implementation class ContractorServlet
 */
@WebServlet("/contractors")
public class ContractorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractorServlet() {
        super();
        // 
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
        
        
        //response.addHeader("Content-Type","application/json");
        response.setContentType("application/json; charset=windows-1251"); 
        
        try {
			Service s = new Service();
			List<Contractor> l = s.getContractors(0,10000,1,true,0,"");
			String res = "";
			if(l.size()==0){res=",";}
			for (Contractor contractor : l) {
				res += contractor.toJson()+",";				
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
			
		switch(request.getParameter("action")){
		
			case "new":
				System.out.println("test");
				Contractor contractor = new Contractor(0,
						request.getParameter("first_name"), 
						request.getParameter("middle_name"), 
						request.getParameter("last_name"), 
						request.getParameter("info"), 
						Arrays.asList(request.getParameter("phones").split(",")) , 
						Arrays.asList(request.getParameter("emails").split(",")), 
						Arrays.asList(request.getParameter("skypes").split(",")));
						
				
				s.addContractor(contractor);
				response.getWriter().print("{success: true}");
				response.getWriter().flush();
		        response.getWriter().close();
						break;
			case "edit":
				
				Contractor contractor_1 = new Contractor(Integer.parseInt(request.getParameter("id")),
						request.getParameter("first_name"), 
						request.getParameter("middle_name"), 
						request.getParameter("last_name"), 
						request.getParameter("info"), 
						Arrays.asList(request.getParameter("phones").split(",")) , 
						Arrays.asList(request.getParameter("emails").split(",")), 
						Arrays.asList(request.getParameter("skypes").split(",")));
				
						
						s.editContractor(contractor_1);
						response.getWriter().print("{success: true}");
						response.getWriter().flush();
				        response.getWriter().close();
						break;
			case "delete":
				s.removeContractor(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			
			response.getWriter().print("{success: "+e1.getMessage()+"}");
			response.getWriter().flush();
	        response.getWriter().close();
		}
	
		
	}

}
