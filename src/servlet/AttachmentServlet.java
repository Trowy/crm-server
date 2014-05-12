package servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CRMException;
import service.Service;
import entity.Attachment;

/**
 * Servlet implementation class AttachmentServlet
 */
@WebServlet("/attachments")
public class AttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    
    public AttachmentServlet() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Service s = Service.getService();
			Attachment a =  s.getAttachmentById(Integer.parseInt(request.getParameter("id")));			
			s.getAttachmentBytes(a);
			response.addHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-Disposition", "attachment; filename="+a.getName()+"."+a.getExtension());
			char[] ca = new char[a.getFile().length];
			for(int i=0;i<ca.length;i++){
				ca[i] = (char)(a.getFile()[i] & 0xFF);
			}
			
			response.getWriter().write(ca);
			response.getWriter().flush();
	        response.getWriter().close();
	        
		} catch (CRMException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
