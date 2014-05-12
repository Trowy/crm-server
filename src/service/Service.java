package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.FileSystemAlreadyExistsException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import entity.Attachment;
import entity.Buffer;
import entity.BusinessScale;
import entity.City;
import entity.Company;
import entity.CompanyStatus;
import entity.Contractor;
import entity.Employee;
import entity.Event;
import entity.EventStatus;
import entity.EventType;
import entity.Log;
import entity.Segment;
import entity.Tag;

public class Service {

	private static Service serivce=null;
	
	private static final Logger log = Logger.getLogger(Service.class);
	private static Connection connection;
	private static String defaultErrorMsg = "Во время выполнения запроса произошла ошибка. "
			+ "Попробуйте повторить попытку или свяжитесь со службой поддержки.";
	private static Buffer buffer;
	
	public static Service getService() throws CRMException{
		if(serivce == null){
			serivce = new Service();
		}		
		return serivce;
	}
	
	private Service() throws CRMException {
		log.trace("Start DBConnection");
		connection = PostgresqlDBConnection.getConnection();
		refreshBuffer();
		log.trace("Finish DBConnection");
	}
	
    public void destroy() {
    	try {
    		connection.close();
		} catch (SQLException e) {}
    	connection = null;
    }
    
    public void refreshBuffer() throws CRMException {
    	buffer = new Buffer();
		buffer.setEventStatuses(getEventStatuses());
		buffer.setEventTypes(getEventTypes());
		buffer.setEmployees(getEmployees());
		buffer.setTags(getTags());
		buffer.setBusinessScales(getBusinessScales());
		buffer.setCompanyStatuses(getCompanyStatuses());
		buffer.setSegments(getSegments());
		buffer.setCities(getCities());
    }
	
	public void addTag(Tag tag) throws CRMException {
		String sqlQuery = "SELECT * FROM add_tag(name := '" + tag.getName() + "'";
		if (tag.getInfo() != null && tag.getInfo() != "")
			sqlQuery += ", info := '" + tag.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "tag with name '" + tag.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			tag.setId(insertResult.getId());
			buffer.addTag(tag);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addBusinessScale(BusinessScale businessScale) throws CRMException {
		String sqlQuery = "SELECT * FROM add_business_scale(name := '" + businessScale.getName() + "'";
		if (businessScale.getInfo() != null && businessScale.getInfo() != "")
			sqlQuery += ", info := '" + businessScale.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "business scale with name '" + businessScale.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			businessScale.setId(insertResult.getId());
			buffer.addBusinessScale(businessScale);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addCompanyStatus(CompanyStatus companyStatus) throws CRMException {
		String sqlQuery = "SELECT * FROM add_company_status(name := '" + companyStatus.getName() + "'";
		if (companyStatus.getInfo() != null && companyStatus.getInfo() != "")
			sqlQuery += ", info := '" + companyStatus.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "company status with name '" + companyStatus.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			companyStatus.setId(insertResult.getId());
			buffer.addCompanyStatus(companyStatus);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addSegment(Segment segment) throws CRMException {
		String sqlQuery = "SELECT * FROM add_segment(name := '" + segment.getName() + "'";
		if (segment.getInfo() != null && segment.getInfo() != "")
			sqlQuery += ", info := '" + segment.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "segment with name '" + segment.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			segment.setId(insertResult.getId());
			buffer.addSegment(segment);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addEventType(EventType eventType) throws CRMException {
		String sqlQuery = "SELECT * FROM add_event_type(name := '" + eventType.getName() + "'";
		if (eventType.getInfo() != null && eventType.getInfo() != "")
			sqlQuery += ", info := '" + eventType.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "event type with name '" + eventType.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			eventType.setId(insertResult.getId());
			buffer.addEventType(eventType);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addEventStatus(EventStatus eventStatus) throws CRMException {
		String sqlQuery = "SELECT * FROM add_event_status(name := '" + eventStatus.getName() + "'";
		if (eventStatus.getInfo() != null && eventStatus.getInfo() != "")
			sqlQuery += ", info := '" + eventStatus.getInfo() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "event status with name '" + eventStatus.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			eventStatus.setId(insertResult.getId());
			buffer.addEventStatus(eventStatus);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addCity(City city) throws CRMException {
		String sqlQuery = "SELECT * FROM add_city(name := '" + city.getName() + "'";
		if (city.getCode() != 0)
			sqlQuery += ", code := '" + city.getCode() + "'";
		if (city.getTime_zone() != 0)
			sqlQuery += ", time_zone := '" + city.getTime_zone() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "city with name '" + city.getName() + "'");
		if (insertResult.getErr_code() == 0) {
			city.setId(insertResult.getId());
			buffer.addCity(city);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void addContractor(Contractor contractor) throws CRMException {
		boolean isFirst = true;
		String sqlQuery = "SELECT * FROM add_contractor(";
		if (contractor.getFirst_name() != null && contractor.getFirst_name() != "") {
			if (isFirst) isFirst = false;
			sqlQuery += "first_name := '" + contractor.getFirst_name() + "'"; }
		if (contractor.getMiddle_name() != null && contractor.getMiddle_name() != "") {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "middle_name := '" + contractor.getMiddle_name() + "'"; 
		}
		if (contractor.getLast_name() != null && contractor.getLast_name() != "") {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "last_name := '" + contractor.getLast_name() + "'"; 
		}
		if (contractor.getInfo() != null && contractor.getInfo() != "") {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "info := '" + contractor.getInfo() + "'"; 
		}
		if (contractor.getPhones() != null && contractor.getPhones().size() > 0) {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "phones := '{" + contractor.getPhonesAsString() + "}'";
		}
		if (contractor.getEmails() != null && contractor.getEmails().size() > 0) {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "emails := '{" + contractor.getEmailsAsString() + "}'";
		}
		if (contractor.getSkypes() != null && contractor.getSkypes().size() > 0) {
			if (isFirst) isFirst = false;
			else sqlQuery += ",";
			sqlQuery += "skypes := '{" + contractor.getSkypesAsString() + "}'";
		}
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "contractor with last name '" + contractor.getLast_name() + "'");
		if (insertResult.getErr_code() == 0) contractor.setId(insertResult.getId());
		else throw new CRMException(insertResult.getErr_msg(), "", 0);
	}
	
	public void addEmployee(Employee employee) throws CRMException {
		String sqlQuery = "SELECT * FROM add_employee(";
		sqlQuery += "login := '" + employee.getLogin() + "'";
		sqlQuery += ", password := '" + employee.getPassword() + "'"; 
		sqlQuery += ", first_name := '" + employee.getFirst_name() + "'";
		sqlQuery += ", middle_name := '" + employee.getMiddle_name() + "'";
		sqlQuery += ", last_name := '" + employee.getLast_name() + "'";
		sqlQuery += ", role := '" + employee.getRole() + "'";
		if (employee.getEmail() != null && employee.getEmail() != null)
			sqlQuery += ", email := '" + employee.getEmail() + "'";
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "employee with login '" + employee.getLogin() + "'");
		if (insertResult.getErr_code() == 0) {
			employee.setId(insertResult.getId());
			buffer.addEmployee(employee);
		}
		else {
			String msg = insertResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = insertResult.getErr_code();
			switch (err_code) {
			case -1:
				field_name = "login";
				field_num = 1;
				break;
			case -2:
				field_name = "password";
				field_num = 2;
				break;
			case -3:
				field_name = "first_name";
				field_num = 3;
				break;
			case -4:
				field_name = "middle_name";
				field_num = 4;
				break;
			case -5:
				field_name = "last_name";
				field_num = 5;
				break;
			case -6:
				field_name = "role";
				field_num = 6;
				break;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}

	public void addAttachment(Attachment attachment) throws CRMException {
		log.trace("Start add attachment with id =  " + attachment.getId());
		InsertResult insertResult = null;
		try {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM add_attachment(name := ?,"
				+ "event_id := ?, extension := ?, size := ?, file := ?)");
		ps.setString(1, attachment.getName());
		ps.setInt(2, attachment.getEvent_id());
		ps.setString(3, attachment.getExtension());
		ps.setLong(4, attachment.getSize());
		ps.setBytes(5, attachment.getFile());
		
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next())
			insertResult = new InsertResult(
				resultSet.getInt("id"), 
				resultSet.getInt("err_code"), 
				resultSet.getString("err_msg"));
		resultSet.close();
		ps.close();
		} catch (SQLException e1) {
			log.debug("Error while pickup select add_attachment: ", e1);
			insertResult = new InsertResult(0, -1, defaultErrorMsg);
		}
		log.trace("End add attachment with id =  " + attachment.getId());
		if (insertResult.getErr_code() == 0) attachment.setId(insertResult.getId());
		else throw new CRMException(insertResult.getErr_msg());
	}
	
	public void getAttachmentBytes(Attachment attachment) throws CRMException {
		String err = "Во время загрузки файла произошла ошибка. Повторите попытку или обратитесь в службу поддержки.";
		if (attachment.getId() == 0) 
			throw new CRMException(err);
		log.trace("Start get bytes for attachment with id =  " + attachment.getId());

		String sqlQuery = "SELECT * FROM get_attachment(id := '" + attachment.getId() + "')";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			
			while (resultSet.next())
				if (resultSet.getInt("err_code") != 0) 
					throw new CRMException(resultSet.getString("err_msg"));
				else attachment.setFile(resultSet.getBytes("file_res"));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(err);
			}
		log.trace("End get bytes for attachment with id =  " + attachment.getId());
		//throw new CRMException(err);
	}
	
	private InsertResult databaseInsertExec(String sqlQuery, String object) {
		log.trace("Start add " + object);
		InsertResult insertResult = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			
			while (resultSet.next())
				insertResult = new InsertResult(
					resultSet.getInt("id"), 
					resultSet.getInt("err_code"), 
					resultSet.getString("err_msg"));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.debug("Error while insert " + object + ": ", e1);
				insertResult = new InsertResult(0, -1, defaultErrorMsg);
			}
		log.trace("End add " + object);
		return insertResult;
	}
	
	private UpdateResult databaseUpdateExec(String sqlQuery, String object) {
		log.trace("Start edit " + object);
		UpdateResult updateResult = null;
		try {
			Statement statement = connection.createStatement();
			boolean hasResult = statement.execute(sqlQuery);
			ResultSet rs = null;
			if (hasResult) {
			  rs = statement.getResultSet();
			}
			while (rs.next()) {
				updateResult = new UpdateResult(rs.getInt("err_code"), rs.getString("err_msg"));
			}
			rs.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error while insert " + object + ": ", e1);
				updateResult = new UpdateResult(-1, defaultErrorMsg);
			}
		log.trace("End edit " + object);
		return updateResult;
	}
	
	public Attachment getAttachmentById(int attachment_id) throws CRMException {

        log.trace("Start getting attachment with id = " + attachment_id);
        Attachment attachment = null;

        try {
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM get_attachment_by_id(" + attachment_id + ")");
             while (resultSet.next()) {
                  attachment = new Attachment(
                            attachment_id, 
                            resultSet.getInt("event_id"),
                            resultSet.getString("name"), 
                            resultSet.getString("extension"),
                            resultSet.getLong("size"),
                            resultSet.getBytes("file"));
             }
             resultSet.close();
             statement.close();
             } catch (SQLException e1) {
                  log.debug("Error: ", e1);
                  throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
             }

        log.trace("Finish getting attachment with id = " + attachment_id);

        return attachment;

   }
	
	public List<Tag> getTags() throws CRMException {
		log.trace("Start getting list of Tags");
		List<Tag> tags = new ArrayList<Tag>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_tags()");
			while (resultSet.next())
				tags.add(new Tag(resultSet.getInt("tag_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Tags");
		return tags;
	}
	
	public List<Segment> getSegments() throws CRMException {
		log.trace("Start getting list of Segments");
		List<Segment> segments = new ArrayList<Segment>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_segments()");
			while (resultSet.next())
				segments.add(new Segment(resultSet.getInt("segment_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Segments");
		return segments;
	}
	
	public List<BusinessScale> getBusinessScales() throws CRMException {
		log.trace("Start getting list of EventStatuses");
		List<BusinessScale> businessScales = new ArrayList<BusinessScale>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_business_scales()");
			while (resultSet.next())
				businessScales.add(new BusinessScale(resultSet.getInt("scale_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of EventStatuses");
		return businessScales;
	}
		
	public List<EventType> getEventTypes() throws CRMException {
		log.trace("Start getting list of EventTypes");
		List<EventType> eventTypes = new ArrayList<EventType>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_event_types()");
			while (resultSet.next())
				eventTypes.add(new EventType(resultSet.getInt("type_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of EventTypes");
		return eventTypes;
	}
	
	public List<EventStatus> getEventStatuses() throws CRMException {
		log.trace("Start getting list of EventStatuses");
		List<EventStatus> eventStatuses = new ArrayList<EventStatus>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_event_statuses()");
			while (resultSet.next())
				eventStatuses.add(new EventStatus(resultSet.getInt("status_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of EventStatuses");
		return eventStatuses;
	}
	
	public List<CompanyStatus> getCompanyStatuses() throws CRMException {
		log.trace("Start getting list of EventStatuses");
		List<CompanyStatus> companyStatuses = new ArrayList<CompanyStatus>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_company_statuses()");
			while (resultSet.next())
				companyStatuses.add(new CompanyStatus(resultSet.getInt("status_id"), resultSet.getString("name"), resultSet.getString("info")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of EventStatuses");
		return companyStatuses;
	}

	public List<City> getCities() throws CRMException {
		log.trace("Start getting list of Cities");
		List<City> cities = new ArrayList<City>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_cities()");
			while (resultSet.next())
				cities.add(new City(resultSet.getInt("city_id"), resultSet.getString("name"), resultSet.getInt("code"), resultSet.getInt("time_zone")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Cities");
		return cities;
	}
	
	public List<Attachment> getAttachmentsForEvent(int event_id) throws CRMException {
		log.trace("Start getting list of Attachemtn for event = " + event_id);
		List<Attachment> attachments = new ArrayList<Attachment>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_attachments(" + event_id + ")");
			while (resultSet.next())
				attachments.add(new Attachment(resultSet.getInt("attachment_id"), 
								event_id,
								resultSet.getString("name"), 
								resultSet.getString("extension"),
								resultSet.getLong("size"),
								null));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Attachemtn for event = " + event_id);
		return attachments;
	}
	
	private List<String> parseArray(String str) {
		List<String> res = new ArrayList<String>();
		if (str == null || str.length() == 0 || str.length() == 2) return res;
		str = str.substring(1, str.length() - 1);
		String [] rawStr = str.split(";");
		for (int i = 0; i < rawStr.length; i++) {
			res.add(rawStr[i]);
		}
		return res;
	}
	
	public Contractor getContractor(int id) throws CRMException {
		log.trace("Start getting Contractor with id = " + id);
		Contractor contractor = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_contractor(" + id + ")");
			while (resultSet.next()) {
				List<String> phones = parseArray(resultSet.getString("phones"));
				List<String> emails = parseArray(resultSet.getString("emails"));
				List<String> skypes = parseArray(resultSet.getString("skypes"));
				contractor = new Contractor(
						resultSet.getInt("contractor_id"), 
						resultSet.getString("first_name"),
						resultSet.getString("middle_name"),
						resultSet.getString("last_name"),
						resultSet.getString("info"),
						phones, emails, skypes);
			}
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting Contractor with id = " + id);
		return contractor;
	}
	
	public List<Employee> getEmployees() throws CRMException {
		log.trace("Start getting list of Employees");
		List<Employee> employees = new ArrayList<Employee>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_employees()");
			while (resultSet.next())
				employees.add(new Employee(
						resultSet.getInt("employee_id"),
						resultSet.getString("login"),
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("middle_name"),
						resultSet.getString("last_name"),
						resultSet.getString("role").charAt(0),
						resultSet.getString("email")));
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Employees");
		return employees;
	}

	public Company getCompany(int company_id) throws CRMException {
		log.trace("Start getting Company with id = " + company_id);
		Company company = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_company(" + company_id + ")");
			while (resultSet.next()) {
				List<String> phones = parseArray(resultSet.getString("phones"));
				List<String> emails = parseArray(resultSet.getString("emails"));
				List<String> skypes = parseArray(resultSet.getString("skypes"));
				List<Tag> tags = getCompanyTags(company_id);
				company = new Company(
						resultSet.getInt("company_id"),
						resultSet.getString("company_name"),
						resultSet.getString("info"),
						resultSet.getString("site"), 
						phones, emails, skypes, 
						buffer.getCity(resultSet.getInt("city_id")), 
						buffer.getSegment(resultSet.getInt("segment_id")), 
						buffer.getCompanyStatus(resultSet.getInt("status_id")),
						buffer.getBusinessScale(resultSet.getInt("scale_id")),
						buffer.getEmployee(resultSet.getInt("employee_id")),
						tags);
			}
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting Company with id = " + company_id);
		return company;
	}
		
	/**
	 * @param employee_id
	 * @param offset
	 * @param limit
	 * @param order_col_num
							1	company_id
							2	company_name
							3	info
							4	site
							5	phones
							6	emails
							7	skypes
							8	city_id
							9	segment_id
							10	status_id
							11	scale_id
							12	employee_id
	 * @param order_direction
	 * @param search_col_num
	 * @param search_value
	 * @return
	 * @throws CRMException
	 */
	public List<Company> getCompanies(int employee_id, int offset, int limit, int order_col_num,
			boolean order_direction, int search_col_num, String search_value) throws CRMException {
		List<Company> companies = new ArrayList<Company>();
		String query = "SELECT * FROM companies";
		if (employee_id > 0) {
			query += " WHERE employee_id = " + employee_id;
			if (search_col_num > 1 && search_col_num < 8) {
				query += " AND ";
				switch(search_col_num) {
				case 2: query += "company_name"; break;
				case 3: query += "info"; break;
				case 4: query += "site"; break;
				case 5: query += "phones::text"; break;
				case 6: query += "emails::text"; break;
			    case 7: query += "skypes::text"; break;
				}
				query += " ILIKE '%'||'" + search_value + "'||'%'";
			}
			if (search_col_num > 7) {
				query += " AND ";
				switch(search_col_num) {
				case 8: query += "city_id"; break;
				case 9: query += "segment_id"; break;
				case 10: query += "status_id"; break;
				case 11: query += "scale_id"; break;
				}
				query += " = " + search_value;
			}
			query += " ORDER BY ";
			switch (order_col_num) {
			case 1: query += "company_id"; break;
			case 2: query += "company_name"; break;
			case 3: query += "info"; break;
			case 4: query += "site"; break;
			case 5: query += "phones"; break;
			case 6: query += "emails"; break;
		    case 7: query += "skypes"; break;
			case 8: query += "city_id"; break;
			case 9: query += "segment_id"; break;
			case 10: query += "status_id"; break;
			case 11: query += "scale_id"; break;
			case 12: query += "employee_id"; break;
			default: query += "company_id";
			}
			if (!order_direction)
				query += " ASC";
			else query += " DESC";
			query += " LIMIT " + limit;
			query += " OFFSET " + offset;
		}
		else {
			if (search_col_num > 1 && search_col_num < 8) {
				query += " WHERE ";
				switch(search_col_num) {
				case 2: query += "company_name"; break;
				case 3: query += "info"; break;
				case 4: query += "site"; break;
				case 5: query += "phones::text"; break;
				case 6: query += "emails::text"; break;
			    case 7: query += "skypes::text"; break;
				}
				query += " ILIKE '%'||'" + search_value + "'||'%'";
			}
			if (search_col_num > 7) {
				query += " AND ";
				switch(search_col_num) {
				case 8: query += "city_id"; break;
				case 9: query += "segment_id"; break;
				case 10: query += "status_id"; break;
				case 11: query += "scale_id"; break;
				}
				query += " = " + search_value;
			}
			query += " ORDER BY ";
			switch (order_col_num) {
			case 1: query += "company_id"; break;
			case 2: query += "company_name"; break;
			case 3: query += "info"; break;
			case 4: query += "site"; break;
			case 5: query += "phones"; break;
			case 6: query += "emails"; break;
		    case 7: query += "skypes"; break;
			case 8: query += "city_id"; break;
			case 9: query += "segment_id"; break;
			case 10: query += "status_id"; break;
			case 11: query += "scale_id"; break;
			case 12: query += "employee_id"; break;
			default: query += "company_id";
			}
			if (!order_direction)
				query += " ASC";
			else query += " DESC";
			query += " LIMIT " + limit;
			query += " OFFSET " + offset;
		}
		try {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			City city = buffer.getCity(resultSet.getInt("city_id"));
			Segment segment = buffer.getSegment(resultSet.getInt("segment_id"));
			CompanyStatus companyStatus = buffer.getCompanyStatus(resultSet.getInt("status_id"));
			BusinessScale businessScale = buffer.getBusinessScale(resultSet.getInt("scale_id"));
			Employee employee = buffer.getEmployee(resultSet.getInt("employee_id"));
			List<String> phones = parseArray(resultSet.getString("phones"));
			List<String> emails = parseArray(resultSet.getString("emails"));
			List<String> skypes = parseArray(resultSet.getString("skypes"));
			int company_id = resultSet.getInt("company_id");
			List<Tag> tags = getCompanyTags(company_id);
			companies.add(new Company(
					company_id,
					resultSet.getString("company_name"), 
					resultSet.getString("info"),
					resultSet.getString("site"), 
					phones, emails, skypes, city, segment, 
					companyStatus, businessScale, employee, tags));
		}
		resultSet.close();
		statement.close();
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg);
		}
		return companies;
	}
	
	public int getCompaniesCount(int employee_id, int order_col_num, boolean order_direction, 
			int search_col_num, String search_value) throws CRMException {
		String query = "SELECT COUNT (*) FROM (SELECT * FROM companies";
		if (employee_id > 0) {
			query += " WHERE employee_id = " + employee_id;
			if (search_col_num > 1 && search_col_num < 8) {
				query += " AND ";
				switch(search_col_num) {
				case 2: query += "company_name"; break;
				case 3: query += "info"; break;
				case 4: query += "site"; break;
				case 5: query += "phones::text"; break;
				case 6: query += "emails::text"; break;
			    case 7: query += "skypes::text"; break;
				}
				query += " ILIKE '%'||'" + search_value + "'||'%'";
			}
			if (search_col_num > 7) {
				query += " AND ";
				switch(search_col_num) {
				case 8: query += "city_id"; break;
				case 9: query += "segment_id"; break;
				case 10: query += "status_id"; break;
				case 11: query += "scale_id"; break;
				}
				query += " = " + search_value;
			}
			query += " ORDER BY ";
			switch (order_col_num) {
			case 1: query += "company_id"; break;
			case 2: query += "company_name"; break;
			case 3: query += "info"; break;
			case 4: query += "site"; break;
			case 5: query += "phones"; break;
			case 6: query += "emails"; break;
		    case 7: query += "skypes"; break;
			case 8: query += "city_id"; break;
			case 9: query += "segment_id"; break;
			case 10: query += "status_id"; break;
			case 11: query += "scale_id"; break;
			case 12: query += "employee_id"; break;
			default: query += "company_id";
			}
			if (!order_direction)
				query += " ASC";
			else query += " DESC";
		}
		else {
			if (search_col_num > 1 && search_col_num < 8) {
				query += " WHERE ";
				switch(search_col_num) {
				case 2: query += "company_name"; break;
				case 3: query += "info"; break;
				case 4: query += "site"; break;
				case 5: query += "phones::text"; break;
				case 6: query += "emails::text"; break;
			    case 7: query += "skypes::text"; break;
				}
				query += " ILIKE '%'||'" + search_value + "'||'%'";
			}
			if (search_col_num > 7) {
				query += " AND ";
				switch(search_col_num) {
				case 8: query += "city_id"; break;
				case 9: query += "segment_id"; break;
				case 10: query += "status_id"; break;
				case 11: query += "scale_id"; break;
				}
				query += " = " + search_value;
			}
			query += " ORDER BY ";
			switch (order_col_num) {
			case 1: query += "company_id"; break;
			case 2: query += "company_name"; break;
			case 3: query += "info"; break;
			case 4: query += "site"; break;
			case 5: query += "phones"; break;
			case 6: query += "emails"; break;
		    case 7: query += "skypes"; break;
			case 8: query += "city_id"; break;
			case 9: query += "segment_id"; break;
			case 10: query += "status_id"; break;
			case 11: query += "scale_id"; break;
			case 12: query += "employee_id"; break;
			default: query += "company_id";
			}
			if (!order_direction)
				query += " ASC";
			else query += " DESC";
		}
		query += ") ttjkee";
		return getCount(query);
	}
	/**
	 * @param offset
	 * @param limit
	 * @param order_col_num  	1 contractor_id
	 							2 first_name
	 							3 middle_name
	 							4 last_name
	 							5 info
	 							6 phones
	 							7 emails
	 							8 skypes
	 * @param order_direction
	 * @param search_col_num
	 * @param search_value
	 * @return
	 * @throws CRMException
	 */
	public List<Contractor> getContractors(int offset, int limit, int order_col_num,
			boolean order_direction, int search_col_num, String search_value) throws CRMException {
		List<Contractor> contractors = new ArrayList<Contractor>();
		String query = "SELECT * FROM contractors";
		if (search_col_num > 1) {
			query += " WHERE ";
			switch(search_col_num) {
			case 2: query += "first_name"; break;
			case 3: query += "middle_name"; break;
			case 4: query += "last_name"; break;
			case 5: query += "info"; break;
			case 6: query += "phones::text"; break;
			case 7: query += "emails::text"; break;
		    case 8: query += "skypes::text"; break;
			}
			query += " ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "contractor_id"; break;
		case 2: query += "first_name"; break;
		case 3: query += "middle_name"; break;
		case 4: query += "last_name"; break;
		case 5: query += "info"; break;
		case 6: query += "phones"; break;
	    case 7: query += "emails"; break;
		case 8: query += "skypes"; break;
		default: query += "contractor_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";
		query += " LIMIT " + limit;
		query += " OFFSET " + offset;
		try {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			List<String> phones = parseArray(resultSet.getString("phones"));
			List<String> emails = parseArray(resultSet.getString("emails"));
			List<String> skypes = parseArray(resultSet.getString("skypes"));
			contractors.add(new Contractor(
					resultSet.getInt("contractor_id"), 
					resultSet.getString("first_name"),
					resultSet.getString("middle_name"),
					resultSet.getString("last_name"),
					resultSet.getString("info"),
					phones, emails, skypes));
		}
		resultSet.close();
		statement.close();
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg);
		}
		return contractors;
	}
	
	public int getContractorsCount(int order_col_num,
			boolean order_direction, int search_col_num, String search_value) throws CRMException {
		String query = "SELECT COUNT(*) FROM (SELECT * FROM contractors";
		if (search_col_num > 1) {
			query += " WHERE ";
			switch(search_col_num) {
			case 2: query += "first_name"; break;
			case 3: query += "middle_name"; break;
			case 4: query += "last_name"; break;
			case 5: query += "info"; break;
			case 6: query += "phones::text"; break;
			case 7: query += "emails::text"; break;
		    case 8: query += "skypes::text"; break;
			}
			query += " ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "contractor_id"; break;
		case 2: query += "first_name"; break;
		case 3: query += "middle_name"; break;
		case 4: query += "last_name"; break;
		case 5: query += "info"; break;
		case 6: query += "phones"; break;
	    case 7: query += "emails"; break;
		case 8: query += "skypes"; break;
		default: query += "contractor_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";
		query += ") AS ttjkee";
		return getCount(query);
	}
	
	/**
	 * @param employee_id 0 = ALL
	 * @param date default 2014-01-01
	 * @param offset default 0
	 * @param limit default 50
	 * @param order_col_num default 1 event_id
	 * 								2 title
	 * 								3 info
	 * 								4 date
	 * 								5 duration
	 * 								6 status_id
	 * 								7 type_id
	 * 								8 company_id
	 * 								9 contractor_id
	 * @param order_direction default false (false - AST, true - DESC)
	 * @param search_col_num 0 - without search, field num same with order_col_num
	 * @param search_value
	 * @return List<Event>
	 * @throws CRMException
	 */
	@SuppressWarnings("deprecation")
	public List<Event> getEvents(int employee_id, Date date, int offset, int limit, int order_col_num,
			boolean order_direction, int search_col_num, String search_value) throws CRMException {
		List<Event> events = new ArrayList<Event>();
		String query = "";
		if (date == null) date = new Date(114, 0, 1);
		if (offset < 0) offset = 0;
		if (limit < 0) limit = 50;
		if (employee_id == 0)
			query = "SELECT * FROM events e WHERE e.date > '" + date + "'";
		else
			query = "SELECT e.event_id, e.title, e.info, e.date, e.duration, e.status_id, e.type_id, e.company_id, e.contractor_id" +
			  " FROM events e INNER JOIN companies c ON e.company_id = c.company_id WHERE c.employee_id = " +employee_id +
			  " AND e.date > '" + date + "'";
		if (search_col_num > 5 && search_col_num < 10) {
			query += " AND ";
			switch(search_col_num) {
			case 6: query += "e.status_id"; break;
			case 7: query += "e.type_id"; break;
			case 8: query += "e.company_id"; break;
			case 9: query += "e.contractor_id"; break;
			}
			query += " = " + search_value;
		}
		if (search_col_num > 1 && search_col_num < 6) {
			query += " AND ";
			switch(search_col_num) {
			case 2: query += "e.title"; break;
			case 3: query += "e.info"; break;
			case 4: query += "e.date"; break;
			case 5: query += "e.duration"; break;
			}
			query += " ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "e.event_id"; break;
		case 2: query += "e.title"; break;
		case 3: query += "e.info"; break;
		case 4: query += "e.date"; break;
		case 5: query += "e.duration"; break;
		case 6: query += "e.status_id"; break;
		case 7: query += "e.type_id"; break;
		case 8: query += "e.company_id"; break;
		case 9: query += "e.contractor_id"; break;
		default: query += "e.event_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";
		query += " LIMIT " + limit;
		query += " OFFSET " + offset;
		try {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			List<Attachment> attachments = getAttachmentsForEvent(resultSet.getInt("event_id"));
			EventStatus eventStatus = buffer.getEventStatus(resultSet.getInt("status_id"));
			EventType eventType = buffer.getEventType(resultSet.getInt("type_id"));
			Company company = getCompany(resultSet.getInt("company_id"));
			Contractor contractor = getContractor(resultSet.getInt("contractor_id"));
			Event event = new Event(resultSet.getInt("event_id"), 
					resultSet.getString("title"), 
					resultSet.getString("info"), 
					resultSet.getDate("date"), 
					resultSet.getInt("duration"), 
					attachments, 
					eventStatus, 
					eventType, 
					company, 
					contractor);
			events.add(event);
		}
		resultSet.close();
		statement.close();
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg);
		}
		return events;
	}
	
	@SuppressWarnings("deprecation")
	public int getEventsCount(int employee_id, Date date, int order_col_num,
			boolean order_direction, int search_col_num, String search_value) throws CRMException {
		String query = "";
		if (date == null)
			date = new Date(114, 0, 1);
		if (employee_id == 0)
			query = "SELECT COUNT (*) FROM (SELECT * FROM events e WHERE e.date > '" + date + "'";
		else
			query = "SELECT COUNT (*) FROM (SELECT e.event_id, e.title, e.info, e.date, e.duration, e.status_id, e.type_id, e.company_id, e.contractor_id" +
			  " FROM events e INNER JOIN companies c ON e.company_id = c.company_id WHERE c.employee_id = " +employee_id +
			  " AND e.date > '" + date + "'";
		if (search_col_num > 5 && search_col_num < 10) {
			query += " AND ";
			switch(search_col_num) {
			case 6: query += "e.status_id"; break;
			case 7: query += "e.type_id"; break;
			case 8: query += "e.company_id"; break;
			case 9: query += "e.contractor_id"; break;
			}
			query += " = " + search_value;
		}
		if (search_col_num > 1 && search_col_num < 6) {
			query += " AND ";
			switch(search_col_num) {
			case 2: query += "e.title"; break;
			case 3: query += "e.info"; break;
			case 4: query += "e.date"; break;
			case 5: query += "e.duration"; break;
			}
			query += " ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "e.event_id"; break;
		case 2: query += "e.title"; break;
		case 3: query += "e.info"; break;
		case 4: query += "e.date"; break;
		case 5: query += "e.duration"; break;
		case 6: query += "e.status_id"; break;
		case 7: query += "e.type_id"; break;
		case 8: query += "e.company_id"; break;
		case 9: query += "e.contractor_id"; break;
		default: query += "e.event_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";
		query += ") AS ttjkee";
		return getCount(query);
	}
	
	/**
	 * @param employee_id
	 * @param date
	 * @param offset
	 * @param limit
	 * @param order_col_num
	 * 						1 log_id int
	 * 						2 action ('I', 'U', 'D') char(1)
	 * 						3 object (0 events, 1 companies) int
	 * 						4 value_id int
	 * 						5 value_desc text
	 * 						6 date timestamp
	 * 						7 employee_id int
	 * @param order_direction
	 * @param search_value
	 * @return
	 * @throws CRMException
	 */
	@SuppressWarnings("deprecation")
	public List<Log> getLogs(int employee_id, Date date, int offset, int limit, int order_col_num,
			boolean order_direction, String search_value) throws CRMException {
		List<Log> logs = new ArrayList<Log>();
		String query = "";
		if (date == null) date = new Date(114, 0, 1);
		if (offset < 0) offset = 0;
		if (limit < 0) limit = 50;
		if (employee_id == 0)
			query = "SELECT * FROM logs l WHERE l.date > '" + date + "'";
		else
			query = "SELECT * FROM logs l WHERE l.employee_id = " +employee_id +
			  " AND l.date > '" + date + "'";
		if (search_value != null && !search_value.isEmpty()) {
			query += " AND value_desc ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "l.log_id"; break;
		case 2: query += "l.action"; break;
		case 3: query += "l.object"; break;
		case 4: query += "l.value_id"; break;
		case 5: query += "l.value_desc"; break;
		case 6: query += "l.date"; break;
		case 7: query += "l.employee_id"; break;
		default: query += "l.log_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";
		query += " LIMIT " + limit;
		query += " OFFSET " + offset;
		try {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			Employee employee = buffer.getEmployee(resultSet.getInt("employee_id"));
			char raw_action = resultSet.getString("action").charAt(0);
			String action = "";
			if (raw_action == 'I') action = "insert";
			else if (raw_action == 'U') action = "update";
			else action = "delete";
			int raw_object = resultSet.getInt("object");
			String object = "";
			if (raw_object == 0) object = "event";
			else object = "company";
			Timestamp raw_date = resultSet.getTimestamp("date");
			Date res_date = new Date(raw_date.getTime());
			Log log = new Log(
					resultSet.getInt("log_id"), 
					action, 
					object, 
					resultSet.getInt("value_id"), 
					resultSet.getString("value_desc"), 
					res_date, 
					employee);
			logs.add(log);
		}
		resultSet.close();
		statement.close();
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			e1.printStackTrace();
			throw new CRMException(defaultErrorMsg);
		}
		return logs;
	}
	
	@SuppressWarnings("deprecation")
	public int getLogsCount(int employee_id, Date date, int order_col_num,
			boolean order_direction, String search_value) throws CRMException {
		String query = "";
		if (date == null) date = new Date(114, 0, 1);
		if (employee_id == 0)
			query = "SELECT COUNT (*) FROM (SELECT * FROM logs l WHERE l.date > '" + date + "'";
		else
			query = "SELECT COUNT (*) FROM (SELECT * FROM logs l WHERE l.employee_id = " +employee_id +
			  " AND l.date > '" + date + "'";
		if (search_value != null && !search_value.isEmpty()) {
			query += " AND value_desc ILIKE '%'||'" + search_value + "'||'%'";
		}
		query += " ORDER BY ";
		switch (order_col_num) {
		case 1: query += "l.log_id"; break;
		case 2: query += "l.action"; break;
		case 3: query += "l.object"; break;
		case 4: query += "l.value_id"; break;
		case 5: query += "l.value_desc"; break;
		case 6: query += "l.date"; break;
		case 7: query += "l.employee_id"; break;
		default: query += "l.log_id";
		}
		if (!order_direction)
			query += " ASC";
		else query += " DESC";	
		query += ") AS ttjkee";
		return getCount(query);
	}
	
	public InsertResult addTagToCompany(int tag_id, int company_id) throws CRMException {
		String sqlQuery = "SELECT * FROM add_companies_tag(tag_id := " + tag_id + 
				", company_id := " + company_id + ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "tag with id = " + tag_id +
				" to company with id = " + company_id);
		if (insertResult.getErr_code() != 0) 
			throw new CRMException(insertResult.getErr_msg());
		return insertResult;	
	}
	
	public List<Tag> getCompanyTags(int company_id) throws CRMException {
		log.trace("Start getting list of Company tags");
		List<Tag> tags = new ArrayList<Tag>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM get_company_tags(" + company_id + ")");
			while (resultSet.next()) {
				tags.add(buffer.getTag(resultSet.getInt(1)));
			}
			resultSet.close();
			statement.close();
			} catch (SQLException e1) {
				log.debug("Error: ", e1);
				throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
			}
		log.trace("End getting list of Company tags");
		return tags;
	}
	
	public void addCompany(Company company) throws CRMException {
		String sqlQuery = "SELECT * FROM add_company(employee_id := " + company.getEmployee().getId();
		if (company.getName() != null && !company.getName().isEmpty())
			sqlQuery += ", company_name := '" + company.getName() + "'";
		if (company.getInfo()!= null && !company.getInfo().isEmpty())
			sqlQuery += ", info := '" + company.getInfo() + "'";
		if (company.getSite()!= null && !company.getSite().isEmpty())
			sqlQuery += ", site := '" + company.getSite() + "'";
		if (company.getPhones() != null && company.getPhones().size() > 0)
			sqlQuery += ", phones := '{" + company.getPhonesAsString() + "}'";
		if (company.getEmails() != null && company.getEmails().size() > 0)
			sqlQuery += ", emails := '{" + company.getEmailsAsString() + "}'";
		if (company.getSkypes() != null && company.getSkypes().size() > 0)
			sqlQuery += ", skypes := '{" + company.getSkypesAsString() + "}'";
		if (company.getCity() != null && company.getCity().getId() != 0)
			sqlQuery += ", city_id := " + company.getCity().getId();
		if (company.getSegment() != null && company.getSegment().getId() != 0)
			sqlQuery += ", segment_id := " + company.getSegment().getId();
		if (company.getCompanyStatus() != null && company.getCompanyStatus().getId() != 0)
			sqlQuery += ", status_id := " + company.getCompanyStatus().getId();
		if (company.getBusinessScale() != null && company.getBusinessScale().getId() != 0)
			sqlQuery += ", scale_id := " + company.getBusinessScale().getId();
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "company with name '" + company.getName() + "' " +
		"by employee with id = " + company.getEmployee().getId());
		if (insertResult.getErr_code() == 0) {
			company.setId(insertResult.getId());
			for (int i = 0; i < company.getTags().size(); i++) {
				addTagToCompany(company.getTags().get(i).getId(), company.getId());
			}
		}
		else throw new CRMException(insertResult.getErr_msg(), "", 0);
	}
	
	public void addEvent(Event event) throws CRMException {
		String sqlQuery = "SELECT * FROM add_event("+ 
				  "date := '" + event.getDate() +
				"', status_id := " + event.getEventStatus().getId() +
				", type_id := " + event.getEventType().getId() +
				", company_id := " + event.getCompany().getId();
		if (event.getTitle() != null && !event.getTitle().isEmpty())
			sqlQuery += ", title := '" + event.getTitle() + "'";
		if (event.getInfo() != null && !event.getInfo().isEmpty())
			sqlQuery += ", info := '" + event.getInfo() + "'";
		if (event.getDuration() > 0)
			sqlQuery += ", duration := " + event.getDuration();
		if (event.getContractor() != null && event.getContractor().getId() > 0)
			sqlQuery += ", contractor_id := " + event.getContractor().getId();
		sqlQuery += ")";
		InsertResult insertResult = databaseInsertExec(sqlQuery, "event with title '" + event.getTitle() + "'");
		if (insertResult.getErr_code() == 0)
			event.setId(insertResult.getId());
		else throw new CRMException(insertResult.getErr_msg(), "", 0);
	}
	
	public Employee auth(String login, String password) throws CRMException {
		Employee employee = null;
		String err_msg = "Ошибка. Пользователь с такой парой логин/пароль не обнаружен.";
		for (int i = 0; i < buffer.getEmployees().size(); i++) {
			if (buffer.getEmployees().get(i).getLogin().equalsIgnoreCase(login)) {
				if (buffer.getEmployees().get(i).checkPassword(password)) {
					employee = buffer.getEmployees().get(i);
					return employee;
				}
				else throw new CRMException(err_msg);
			}
		}
		throw new CRMException(err_msg);
	}

	public void editTag(Tag tag) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_tag(" + tag.getId() + ", '" + tag.getName() + "'";
		if (tag.getInfo() != null && tag.getInfo() != "")
			sqlQuery += ", '" + tag.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " tag with name '" + tag.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeTag(tag.getId());
			buffer.addTag(tag);
		} else {
			String msg = updateResult.getErr_msg();
			String field_name = "";
			int field_num = 0;
			int err_code = updateResult.getErr_code();
			if (err_code == -2 || err_code == -3) {
				field_name = "name";
				field_num = 1;
			}
			throw new CRMException(msg, field_name, field_num);
		}
	}
	
	public void editBusinessScale(BusinessScale businessScale) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_business_scale(" + businessScale.getId() + ", '" + businessScale.getName() + "'";
		if (businessScale.getInfo() != null && businessScale.getInfo() != "")
			sqlQuery += ", '" + businessScale.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " businessScale with name '" + businessScale.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeBusinessScale(businessScale.getId());
			buffer.addBusinessScale(businessScale);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editCompanyStatus(CompanyStatus companyStatus) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_company_status(" + companyStatus.getId() + ", '" + companyStatus.getName() + "'";
		if (companyStatus.getInfo() != null && companyStatus.getInfo() != "")
			sqlQuery += ", '" + companyStatus.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " company status with name '" + companyStatus.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeCompanyStatus(companyStatus.getId());
			buffer.addCompanyStatus(companyStatus);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editSegment(Segment segment) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_segment(" + segment.getId() + ", '" + segment.getName() + "'";
		if (segment.getInfo() != null && segment.getInfo() != "")
			sqlQuery += ", '" + segment.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " segment with name '" + segment.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeSegment(segment.getId());
			buffer.addSegment(segment);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editEventType(EventType eventType) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_event_type(" + eventType.getId() + ", '" + eventType.getName() + "'";
		if (eventType.getInfo() != null && eventType.getInfo() != "")
			sqlQuery += ", '" + eventType.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " event type with name '" + eventType.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeEventType(eventType.getId());
			buffer.addEventType(eventType);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editEventStatus(EventStatus eventStatus) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_event_status(" + eventStatus.getId() + ", '" + eventStatus.getName() + "'";
		if (eventStatus.getInfo() != null && eventStatus.getInfo() != "")
			sqlQuery += ", '" + eventStatus.getInfo() + "'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " event status with name '" + eventStatus.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeEventStatus(eventStatus.getId());
			buffer.addEventStatus(eventStatus);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editCity(City city) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_city(" + city.getId() + ", '" + city.getName() + "'";
		if (city.getCode() != 0)
			sqlQuery += ",  " + city.getCode();
		if (city.getTime_zone() != 0)
			sqlQuery += ",  " + city.getTime_zone();
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " city with name '" + city.getName() + "'");
		if (updateResult.getErr_code() == 0) {
			buffer.removeCity(city.getId());
			buffer.addCity(city);
		} else throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editContractor(Contractor contractor) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_contractor(" + contractor.getId();
		if (contractor.getFirst_name() != null && contractor.getFirst_name() != "")
			sqlQuery += ", first_name_n := '" + contractor.getFirst_name() + "'";
		if (contractor.getMiddle_name() != null && contractor.getMiddle_name() != "")
			sqlQuery += ", middle_name_n := '" + contractor.getMiddle_name() + "'"; 
		if (contractor.getLast_name() != null && contractor.getLast_name() != "")
			sqlQuery += ", last_name_n := '" + contractor.getLast_name() + "'"; 
		if (contractor.getInfo() != null && contractor.getInfo() != "")
			sqlQuery += ", info_n := '" + contractor.getInfo() + "'"; 
		if (contractor.getPhones() != null && contractor.getPhones().size() > 0)
			sqlQuery += ", phones_n := '{" + contractor.getPhonesAsString() + "}'";
		if (contractor.getEmails() != null && contractor.getEmails().size() > 0)
			sqlQuery += ", emails_n := '{" + contractor.getEmailsAsString() + "}'";
		if (contractor.getSkypes() != null && contractor.getSkypes().size() > 0)
			sqlQuery += ", skypes_n := '{" + contractor.getSkypesAsString() + "}'";
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " contractor with last name '" + contractor.getLast_name() + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editEmployee(Employee employee) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_employee(" + employee.getId();
		sqlQuery += ", login_n := '" + employee.getLogin() + "'";
		sqlQuery += ", password_n := '" + employee.getPassword() + "'"; 
		sqlQuery += ", first_name_n := '" + employee.getFirst_name() + "'";
		sqlQuery += ", middle_name_n := '" + employee.getMiddle_name() + "'";
		sqlQuery += ", last_name_n := '" + employee.getLast_name() + "'";
		sqlQuery += ", role_n := '" + employee.getRole() + "'";
		if (employee.getEmail() != null && employee.getEmail() != null)
			sqlQuery += ", email_n := '" + employee.getEmail() + "'";
		sqlQuery += ")";
		
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " employee with last name '" + employee.getLast_name() + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editCompany(Company company) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_company(" + company.getId() + ", " + company.getEmployee().getId();
		if (company.getName() != null && !company.getName().isEmpty())
			sqlQuery += ", company_name_n := '" + company.getName() + "'";
		if (company.getInfo()!= null && !company.getInfo().isEmpty())
			sqlQuery += ", info_n := '" + company.getInfo() + "'";
		if (company.getSite()!= null && !company.getSite().isEmpty())
			sqlQuery += ", site_n := '" + company.getSite() + "'";
		if (company.getPhones() != null && company.getPhones().size() > 0)
			sqlQuery += ", phones_n := '{" + company.getPhonesAsString() + "}'";
		if (company.getEmails() != null && company.getEmails().size() > 0)
			sqlQuery += ", emails_n := '{" + company.getEmailsAsString() + "}'";
		if (company.getSkypes() != null && company.getSkypes().size() > 0)
			sqlQuery += ", skypes_n := '{" + company.getSkypesAsString() + "}'";
		if (company.getCity() != null && company.getCity().getId() != 0)
			sqlQuery += ", city_id_n := " + company.getCity().getId();
		if (company.getSegment() != null && company.getSegment().getId() != 0)
			sqlQuery += ", segment_id_n := " + company.getSegment().getId();
		if (company.getCompanyStatus() != null && company.getCompanyStatus().getId() != 0)
			sqlQuery += ", status_id_n := " + company.getCompanyStatus().getId();
		if (company.getBusinessScale() != null && company.getBusinessScale().getId() != 0)
			sqlQuery += ", scale_id_n := " + company.getBusinessScale().getId();
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " company with name '" + company.getName() + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void editEvent(Event event) throws CRMException {
		String sqlQuery = "SELECT * FROM edit_event(" + 
				"id_n := " + event.getId() + 
				", status_id_n := " + event.getEventStatus().getId() +
				", type_id_n := " + event.getEventType().getId() +
				", company_id_n := " + event.getCompany().getId();
		if (event.getTitle() != null && !event.getTitle().isEmpty())
			sqlQuery += ", title_n := '" + event.getTitle() + "'";
		if (event.getInfo() != null && !event.getInfo().isEmpty())
			sqlQuery += ", info_n := '" + event.getInfo() + "'";
		if (event.getDuration() > 0)
			sqlQuery += ", duration_n := " + event.getDuration();
		if (event.getContractor() != null && event.getContractor().getId() > 0)
			sqlQuery += ", contractor_id_n := " + event.getContractor().getId();
		sqlQuery += ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " event with title '" + event.getTitle() + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void removeTagFromCompany(int company_id, int tag_id) throws CRMException {
		try {
			log.debug("Start remove tag with id = " + tag_id + " from company with id = " + company_id);
			String sqlQuery = "SELECT * FROM remove_tag_from_company(cmp_id := " + company_id + 
					", tg_id := " + tag_id + ")";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			resultSet.close();
			statement.close();
			log.debug("Finish remove tag with id = " + tag_id + " from company with id = " + company_id);
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
		}
	}
	
	public void removeTag(int tag_id) throws CRMException {
		try {
			log.debug("Start remove tag with id = " + tag_id);
			String sqlQuery = "SELECT * FROM remove_tag(tg_id := " + tag_id + ")";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			buffer.removeTag(tag_id);
			resultSet.close();
			statement.close();
			log.debug("Finish remove tag with id = " + tag_id);
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
		}
	}
	
	public void removeAttachment(int attachment_id) throws CRMException {
		try {
			log.debug("Start remove attachment with id = " + attachment_id);
			String sqlQuery = "SELECT * FROM remove_attachment(att_id := " + attachment_id + ")";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			resultSet.close();
			statement.close();
			log.debug("Finish remove attachment with id = " + attachment_id);
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
		}
	}
	
	public void removeEmployee(int employee_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_employee(" + employee_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove employee with id =  '" + employee_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeEmployee(employee_id);
	}
	
	public void removeBusinessScale(int business_scale_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_business_scale(bssc_id := " + business_scale_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove busienss scale with id '" + business_scale_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeBusinessScale(business_scale_id);
	}
	
	public void removeCompanyStatus(int company_status_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_company_status(obj_id := " + company_status_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove company status with id '" + company_status_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeCompanyStatus(company_status_id);
	}
	
	public void removeSegment(int segment_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_segment(obj_id := " + segment_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove segment with id '" + segment_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeSegment(segment_id);
	}
	
	public void removeCity(int city_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_city(obj_id := " + city_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove city with id '" + city_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeCity(city_id);
	}
	
	public void removeEventStatus(int event_status_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_event_status(obj_id := " + event_status_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove event status with id '" + event_status_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeEventStatus(event_status_id);
	}
	
	public void removeEventType(int event_type_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_event_type(obj_id := " + event_type_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove event type with id '" + event_type_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
		else buffer.removeEventType(event_type_id);
	}
	
	public void removeContractor(int contractor_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_contractor(obj_id := " + contractor_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove contractor with id '" + contractor_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void removeEvent(int event_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_event(obj_id := " + event_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove event with id '" + event_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void removeCompany(int company_id) throws CRMException {
		String sqlQuery = "SELECT * FROM remove_company(obj_id := " + company_id + ")";
		UpdateResult updateResult = databaseUpdateExec(sqlQuery, " remove company with id '" + company_id + "'");
		if (updateResult.getErr_code() != 0) throw new CRMException(updateResult.getErr_msg());
	}
	
	public void removeTags(int company_id) throws CRMException {
		try {
			log.debug("Start remove all company tags for company with id = " + company_id);
			String sqlQuery = "SELECT * FROM remove_tags(cmp_id := " + company_id + ")";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			resultSet.close();
			statement.close();
			log.debug("Finish remove all company tags for company with id = " + company_id);
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
		}
	}
	
	public int getTagsCount() {
		return buffer.getTags().size();
	}
	public int getSegmentsCount() {
		return buffer.getSegments().size();
	}
	public int getEventStatusesCount() {
		return buffer.getEventStatuses().size();
	}
	public int getEventTypesCount() {
		return buffer.getEventTypes().size();
	}
	public int getBusinessScalesCount() {
		return buffer.getBusinessScales().size();
	}
	public int getCompanyStatusesCount() {
		return buffer.getCompanyStatuses().size();
	}
	public int getCitiesCount() {
		return buffer.getCities().size();
	}
	public int getEmployeesCount() {
		return buffer.getEmployees().size();
	}
	
	private int getCount(String query) throws CRMException {
		int result = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
				result = resultSet.getInt(1);
			resultSet.close();
			statement.close();
		} catch (SQLException e1) {
			log.debug("Error: ", e1);
			throw new CRMException(defaultErrorMsg + "\n" + e1.toString());
		}
		return result;
	}
	/**
	 * @param user like this tomsktest123@yandex.ru
	 * @param password
	 * @param toSend
	 * @param subject
	 * @param text
	 * @param attachments
	 * @param host default smtp.yandex.ru";
	 * @param port default 465
	 * @throws CRMException
	 */
	public void sendEmail(String user, String password,
			String toSend, String subject, String text, List<Attachment> attachments,
			String host, String port) throws CRMException {
		try {
		if (host == null || host.isEmpty())
			host = "smtp.yandex.ru";
		if (port == null || port.isEmpty())
			port = "465";
        Properties props = new Properties();
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "false");
 
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", port);

        Session session = Session.getDefaultInstance(props, null);

        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.setSubject(subject);
        message.addRecipient(RecipientType.TO, new InternetAddress(toSend));
        
        Multipart multiPart = new MimeMultipart();  
        MimeBodyPart messageText = new MimeBodyPart();  
        messageText.setContent(text, "text/plain; charset=\"UTF-8\"");  
        multiPart.addBodyPart(messageText);  
        
        if (attachments != null && attachments.size() > 0)
	        for (int i = 0; i < attachments.size(); i++) {
	        	MimeBodyPart attachment = new MimeBodyPart();  
	        	File temp = File.createTempFile("tmpfilesimplenametooshortoknowlong", null);
	        	temp.deleteOnExit();
	        	BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	        	out.write(attachments.get(i).getFile().toString().toCharArray());
	            out.close();
	        	FileDataSource fds = new FileDataSource(temp);
	        	attachment.setDataHandler(new DataHandler(fds));
	        	attachment.setFileName(attachments.get(i).getName() + "." + attachments.get(i).getExtension());
	        	multiPart.addBodyPart(attachment);       	
	        }
        message.setContent(multiPart);  
        Transport transport = session.getTransport("smtp");
        transport.connect(host, user, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new CRMException("При отправке письма произошла ошибка "+e.getMessage()+". Проверьте настройки соединения с почтовым сервисом и повторите попытку.");
		}
	}
	
	/**
	 * @param length 	0 day
	 * 					1 week
	 * 					2 month
	 * @return
	 */
	public String generateReport(int length) throws CRMException {
		String jsonStr = "{data:[";
		Calendar calendar = Calendar.getInstance();  
		if (length == 0)
			calendar.add(Calendar.DATE, -1);
		else if (length == 1)
			calendar.add(Calendar.DATE, -8);
		else calendar.add(Calendar.DATE, -31);
		List<Log> logs = new ArrayList<Log>();
		java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());  
		try {
			logs = getLogs(0, date, 0, 9999, 0, false, null);
		} catch (CRMException e) {
			throw new CRMException(defaultErrorMsg);
		}
		for (int i = 0; i < buffer.getEmployees().size(); i++) {
			int employee_id = buffer.getEmployees().get(i).getId();
			int cmpInsert = 0; 
			int cmpUpdate = 0;
			int cmpDelete = 0;
			int evtInsert = 0;
			int evtUpdate = 0;
			int evtDelete = 0;
			for (int j = 0; j < logs.size(); j++) {
				if (logs.get(j).getEmployee().getId() == employee_id) {
					if (logs.get(j).getAction().equalsIgnoreCase("insert"))
						if (logs.get(j).getObject().equalsIgnoreCase("event"))
							evtInsert++;
						else cmpInsert++;
					else if (logs.get(j).getAction().equalsIgnoreCase("update"))
						if (logs.get(j).getObject().equalsIgnoreCase("event"))
							evtUpdate++;
						else cmpUpdate++;
					else 
						if (logs.get(j).getObject().equalsIgnoreCase("event"))
							cmpDelete++;
						else evtDelete++;
				}
			}
			Employee e = getEmployee(employee_id);
			
			String employee_fio = e.getLast_name()+" "+e.getFirst_name()+" "+e.getMiddle_name();
			jsonStr += "{";
			jsonStr += "employee_id: '" + employee_id +
						"', employee: '" + employee_fio +
						"', cmpInsert: ' + " + cmpInsert +
						"', cmpUpdate: ' + " + cmpUpdate +
						"', cmpDelete: ' + " + cmpDelete +
						"', evtInsert: ' + " + evtInsert +
						"', evtUpdate: ' + " + evtUpdate +
						"', evtDelete: ' + " + evtDelete + "'";
			jsonStr += "}";
			if (i < buffer.getEmployees().size() - 1) jsonStr +=",";
		}
		jsonStr += "]}";
		return jsonStr;
	}
	
	public Employee getEmployee(int employee_id) throws CRMException {           log.trace("Start getting Employee with id = " + employee_id);           return buffer.getEmployee(employee_id);      }
	
}
