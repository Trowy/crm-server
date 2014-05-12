package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		if(request.getHeader("Origin")!=null){
    		response.addHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        
    	}else{
    		response.addHeader("Access-Control-Allow-Origin","*");
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
			String res = "";		
			Employee e = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));
int e_id;
			
			if(e.getRole()=='M'){
				e_id = e.getId();
			}else{
				e_id=0;
			}
			
			List<Company> l = s.getCompanies(e_id, 0, 1000, 1, false, 0, "");
				
			if(l.size()==0){res=",";}
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getHeader("Origin")!=null){
    		response.addHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        
    	}else{
    		response.addHeader("Access-Control-Allow-Origin","*");
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
			int e_id;
			Employee e;
			List<String> sk = new ArrayList<String>();
			List<String> p = new ArrayList<String>();
			List<String> em = new ArrayList<String>();
		switch(request.getParameter("action")){
		
			case "new":
				e = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));
				
				for(int i=0;request.getParameter("phone_"+i)!=null;i++){
					if(!request.getParameter("phone_"+i).equals("")){
						p.add(request.getParameter("phone_"+i));
					}
				}
				
				for(int i=0;request.getParameter("email_"+i)!=null;i++){
					if(!request.getParameter("email_"+i).equals("")){
						em.add(request.getParameter("email_"+i));
					}
				}
				
				for(int i=0;request.getParameter("skype_"+i)!=null;i++){
					if(!request.getParameter("skype_"+i).equals("")){
						sk.add(request.getParameter("skype_"+i));
					}
				}
				List<Tag> t = new ArrayList<Tag>();
				for(int i=0;request.getParameter("tag_"+i)!=null;i++){
					if(!request.getParameter("tag_"+i).equals("")){
						t.add(new Tag(Integer.parseInt(request.getParameter("tag_"+i)), "", ""));
					}
				}
				
				
				if(e.getRole()=='M'){
					e_id = e.getId();
				}else{
					e_id=Integer.parseInt(request.getParameter("Employee"));
				}
				
				
				
				Company c = new Company(0,
						request.getParameter("name"), 
						request.getParameter("info"), 
						request.getParameter("site"),
						p,//phones, 
						em, 
						sk, 
						new City(Integer.parseInt(request.getParameter("City")), "", 0, 0), 
						new Segment(Integer.parseInt(request.getParameter("Segment")), "", ""), 
						new CompanyStatus(Integer.parseInt(request.getParameter("CompanyStatus")), "", ""),
						new BusinessScale(Integer.parseInt(request.getParameter("BusinessScale")), "", ""),
						new Employee(e_id, "", "", "", "", "", 'S', ""), 
						t	);
				
									
				
				s.addCompany(c);
				
				
				
						
						break;
			case "edit":
				 e = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));
				
				 for(int i=0;request.getParameter("phone_"+i)!=null;i++){
						if(!request.getParameter("phone_"+i).equals("")){
							p.add(request.getParameter("phone_"+i));
						}
					}
					
					for(int i=0;request.getParameter("email_"+i)!=null;i++){
						if(!request.getParameter("email_"+i).equals("")){
							em.add(request.getParameter("email_"+i));
						}
					}
					
					for(int i=0;request.getParameter("skype_"+i)!=null;i++){
						if(!request.getParameter("skype_"+i).equals("")){
							sk.add(request.getParameter("skype_"+i));
						}
					}
					
				
				if(e.getRole()=='M'){
					e_id = e.getId();
				}else{
					e_id=Integer.parseInt(request.getParameter("Employee"));
				}
				Company c_c = new Company(Integer.parseInt(request.getParameter("id")),
						request.getParameter("name"), 
						request.getParameter("info"), 
						request.getParameter("site"),
						p, 
						em, 
						sk, 
						new City(Integer.parseInt(request.getParameter("City")), "", 0, 0), 
						new Segment(Integer.parseInt(request.getParameter("Segment")), "", ""), 
						new CompanyStatus(Integer.parseInt(request.getParameter("CompanyStatus")), "", ""),
						new BusinessScale(Integer.parseInt(request.getParameter("BusinessScale")), "", ""),
						new Employee(e_id, "", "", "", "", "", 'S', ""), 
						new ArrayList<Tag>());
				s.editCompany(c_c);
				
				
				
				
						break;
			case "delete":
				s.removeCompany(Integer.parseInt(request.getParameter("id")));
				
		}
		
		response.getWriter().print("{success: true}");
		
		} catch (CRMException e1) {
			String field = "name";
			if(e1.field_num==1){
				field = "name";
			}
			response.getWriter().print("{success:false, errors: {"+field+":'"+e1.getMessage().replace("'", "\\'")+"'}}");
			
			
		} catch (Exception e) {
			response.getWriter().print("{success:false, errors: {name:'"+e.getMessage().replace("'", "\\'")+"'}}");
		}
		response.getWriter().flush();
        response.getWriter().close();
        }
		
	}

}
