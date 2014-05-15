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

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = -4746180138011498672L;

	public EmployeeServlet() {
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
				Service s = Service.getService();
				List<Employee> l = s.getEmployees();
				String res = "";
				if (l.size() == 0) {
					res = ",";
				}
				for (Employee employee : l) {
					res += employee.toJson() + ",";
				}
				res = res.substring(0, res.length() - 1);
				response.getWriter().print("{'data':[" + res + "]}");
			} catch (CRMException e) {
				response.getWriter().print("{'data':[]}");
			}

			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
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
				Service s = Service.getService();
				Employee e3 = s.getEmployee((Integer) request.getSession().getAttribute("employee_id"));

				if (e3.getRole() == 'S') {
					switch (request.getParameter("action")) {

					case "new":
						Employee e = new Employee(0, request.getParameter("login"), request.getParameter("password"),
								request.getParameter("first_name"), request.getParameter("middle_name"),
								request.getParameter("last_name"), request.getParameter("role").charAt(0),
								request.getParameter("email"));

						e.hashPassword();
						s.addEmployee(e);

						break;
					case "edit":
						Employee e_old = s.getEmployee(Integer.parseInt(request.getParameter("id")));
						Employee e_e = new Employee(Integer.parseInt(request.getParameter("id")),
								request.getParameter("login"),
								request.getParameter("password").equals("") ? e_old.getPassword() : request
										.getParameter("password"), request.getParameter("first_name"),
								request.getParameter("middle_name"), request.getParameter("last_name"), request
										.getParameter("role").charAt(0), request.getParameter("email"));
						response.getWriter().println(e_e.toString());
						if (!request.getParameter("password").equals("")) {
							e_e.hashPassword();
						}
						response.getWriter().println(e_e.toString());
						s.editEmployee(e_e);

						break;
					case "delete":
						s.removeEmployee(Integer.parseInt(request.getParameter("id")));

					}
					response.getWriter().print("{success: true}");
				}
			} catch (CRMException e1) {
				String field = "login";
				if (e1.field_num == 1) {
					field = "login";
				} else if (e1.field_num == 2) {
					field = "password";
				} else if (e1.field_num == 3) {
					field = "first_name";
				} else if (e1.field_num == 4) {
					field = "middle_name";
				} else if (e1.field_num == 5) {
					field = "last_name";
				} else if (e1.field_num == 6) {
					field = "role";
				}
				e1.printStackTrace();
				response.getWriter().print(
						"{success:false, errors: {" + field + ":'" + e1.getMessage().replace("'", "\\'") + "'}}");
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print(
						"{success:false, errors: {login:'" + e.getMessage().replace("'", "\\'") + "'}}");
			}

			response.getWriter().flush();
			response.getWriter().close();

		}
	}

}
