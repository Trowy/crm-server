package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import org.apache.commons.io.IOUtils;

import service.CRMException;
import service.Service;
import entity.Attachment;
import entity.Employee;
import entity.Event;
import entity.EventStatus;
import entity.EventType;

@WebServlet("/events")
public class EventServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5965012404082027867L;

	public EventServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getHeader("Origin") != null) {
			response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

		} else {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		response.addHeader("Access-Control-Max-Age", "000");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Content-Type", "application/json");
		response.setContentType("application/json; charset=windows-1251");

		if (request.getSession().getAttribute("employee_id") != null) {

			try {

				Calendar cal = Calendar.getInstance();

				Service s = Service.getService();
				int type = Integer.parseInt(request.getParameter("type"));

				if (type == 2 || type == 5 || type == 8) {
					cal.add(Calendar.DATE, -7);
				}

				if (type == 3 || type == 6 || type == 9) {
					cal.add(Calendar.DATE, -1);

				}

				Employee e = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));

				int e_id;

				if (e.getRole() == 'M') {
					e_id = e.getId();
				} else {
					e_id = 0;
				}

				List<Event> l = s.getEvents(e_id, null, 0, 1000, 1, false, 0, "");
				if (type == 1) {
					l = s.getEvents(e_id, null, 0, 1000, 1, false, 0, "");
				}
				if (type == 2) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 0, "");
				}
				if (type == 3) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 0, "");
				}
				if (type == 4) {
					l = s.getEvents(e_id, null, 0, 1000, 1, false, 8, request.getParameter("comp"));
				}
				if (type == 5) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 8,
							request.getParameter("comp"));
				}
				if (type == 6) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 8,
							request.getParameter("comp"));
				}
				if (type == 7) {
					l = s.getEvents(e_id, null, 0, 1000, 1, false, 9, request.getParameter("cnt"));
				}
				if (type == 8) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 9,
							request.getParameter("cnt"));
				}
				if (type == 9) {
					l = s.getEvents(e_id, new java.sql.Date(cal.getTimeInMillis()), 0, 1000, 1, false, 9,
							request.getParameter("cnt"));
				}

				String res = "";
				if (l.size() == 0) {
					res = ",";
				}
				for (Event event : l) {
					res += event.toJson() + ",";
				}
				res = res.substring(0, res.length() - 1);
				response.getWriter().print("{'data':[" + res + "]}");
			} catch (CRMException e) {
				e.printStackTrace();
				response.getWriter().print("{'data':[]}");
			}

			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		response.addHeader("Access-Control-Allow-Origin", "http://crm-tusur.6te.net/");

		response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		response.addHeader("Access-Control-Max-Age", "000");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Content-Type", "application/json");
		response.setContentType("application/json; charset=windows-1251");

		response.getWriter().print("s");
		try {
			Map<String, String> fileds = new Hashtable<String, String>();

			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			InputStream filecontent = null;
			String filename = null;
			for (FileItem item : items) {
				if (item.isFormField()) {

					fileds.put(item.getFieldName(), item.getString());
				} else {
					filename = FilenameUtils.getName(item.getName());
					filecontent = item.getInputStream();
				}
			}

			Service s = Service.getService();

			String[] date;
			Attachment a = null;

			if (filename != null && !filename.equals("")) {
				try (FileOutputStream out = new FileOutputStream("E:\\" + filename)) {
					IOUtils.copy(filecontent, out);
				}

			}
			File f1;
			switch (fileds.get("action")) {

			case "new":

				date = fileds.get("date").split("\\.");

				Event e = new Event(0, fileds.get("title"), fileds.get("info"), new java.sql.Date(
						Integer.parseInt(date[2]) + 100, Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])),

				Integer.parseInt(fileds.get("duration")), null, new EventStatus(Integer.parseInt(fileds
						.get("eventStatus")), "", ""),
						new EventType(Integer.parseInt(fileds.get("eventType")), "", ""), s.getCompany(Integer
								.parseInt(fileds.get("company"))), s.getContractor(Integer.parseInt(fileds
								.get("contractor"))));

				s.addEvent(e);
				if (filename != null && !filename.equals("")) {
					s.addAttachment(new Attachment("E:\\" + filename, e.getId()));
					f1 = new File("E:\\" + filename);
					f1.delete();
				}
				response.getWriter().print("{success: true}");
				break;
			case "edit":
				date = fileds.get("date").split("\\.");
				Event e_e = new Event(Integer.parseInt(fileds.get("id")), fileds.get("title"), fileds.get("info"),
						new java.sql.Date(Integer.parseInt(date[2]) + 100, Integer.parseInt(date[1]) - 1, Integer
								.parseInt(date[0])), Integer.parseInt(fileds.get("duration")), null,
						new EventStatus(Integer.parseInt(fileds.get("eventStatus")), "", ""), new EventType(
								Integer.parseInt(fileds.get("eventType")), "", ""), s.getCompany(Integer
								.parseInt(fileds.get("company"))), s.getContractor(Integer.parseInt(fileds
								.get("contractor"))));

				s.editEvent(e_e);
				response.getWriter().print("{success: true}");
				break;
			case "delete":
				s.removeEvent(Integer.parseInt(fileds.get("id")));
				response.getWriter().print("{success: true}");
			}

		} catch (CRMException e1) {
			e1.printStackTrace();
			response.getWriter()
					.print("{success:false, errors: {title:'" + e1.getMessage().replace("'", "\\'") + "'}}");
		} catch (FileUploadException e1) {

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{success:false, errors: {title:'" + e.getMessage().replace("'", "\\'") + "'}}");
		}

		response.getWriter().flush();
		response.getWriter().close();		
	}

}
