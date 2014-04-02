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
import entity.City;
import entity.Employee;


@WebServlet("/cities")
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CityServlet() {
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
			List<City> l = s.getCities();
			String res = "";
			for (City city : l) {
				res += city.toJson()+",";
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
				City c = new City(0, request.getParameter("name"),Integer.parseInt(request.getParameter("code")),Integer.parseInt(request.getParameter("time_zone")));
						
				
				s.addCity(c);
						
						break;
			case "edit":
				
				City c1 = new City(Integer.parseInt(request.getParameter("id")), 
						request.getParameter("name"),
						Integer.parseInt(request.getParameter("code")),
						Integer.parseInt(request.getParameter("time_zone")));
						
						
						s.editCity(c1);
						
						break;
			case "delete":
				//s.removeCity(Integer.parseInt(request.getParameter("id")));
				
		}
		
		} catch (CRMException e1) {			
			e1.printStackTrace();
		}
		
		response.getWriter().print("{success: true}");
		response.getWriter().flush();
        response.getWriter().close();
		
		
	}

}
