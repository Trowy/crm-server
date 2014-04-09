package vkr;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Attachment;
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
import service.CRMException;
import service.Service;

public class Main {
	
	//private static final Logger log = Logger.getLogger(Main.class);
	public static Service service;
	
	public static void main(String[] args) {
		try {
			service = new Service();
			//feelDatabase(); // random generate data for all tables
			
			//List<Event> events = service.getEvents(7, new Date(114, 0, 1), 0, 50, 1, false, 2, "title2999");
			//for (int i = 0; i < events.size(); i++)
			//	System.out.println(events.get(i).toJson());
			//System.out.println(service.getEventsCount(7, new Date(114, 0, 1), 1, false, 2, "title2999"));
			
			//List<Contractor> contractors = service.getContractors(0, 15, 6, true, 6, "phone");
			//for (int i = 0; i < contractors.size(); i++)
			//	System.out.println(contractors.get(i).toJson());
			//System.out.println(service.getContractorsCount(6, true, 6, "phone"));
			
			//List<Company> companies = service.getCompanies(1, 0, 15, 1, true, 5, "phone");
			//for (int i = 0; i < companies.size(); i++)
			//	System.out.println(companies.get(i).toJson());
			//System.out.println(service.getCompaniesCount(1, 1, true, 5, "phone"));
			
			//service.removeTags(14);
			//service.removeBusinessScale(15);
			//service.removeCompanyStatus(3);
			//service.removeSegment(2);
			//service.removeCity(2);
			//service.removeEventStatus(2);
			//service.removeEventType(2);
			//service.removeEvent(15);
			//service.removeCompany(1);
			
			//List<Log> logs = service.getLogs(0, new Date(114,3,5), 0, 50, 0, false, null);
			//for (int i = 0; i < logs.size(); i++)
			//	System.out.println(logs.get(i).toJson());
			//System.out.println(service.getLogsCount(0, null, 0, true, "QQQ"));
			
			//Attachment att = new Attachment("E:\\tt\\Обои\\2.bmp", 0);
			//List<Attachment> atts = new ArrayList<Attachment>();
			//atts.add(att);
			//service.sendEmail("tomsktest123@yandex.ru", "ghbdtnrfrltkf", "tomsktest123@yandex.ru", 
			//	"test send mail", "simple text, hate this word", atts, null, null);
			//System.out.println(service.generateReport(2));
		} catch (CRMException e) {
			e.printStackTrace();
			System.err.println("#1 MSG: " + e.msg);
			System.err.println("#2 FIELD_NUM: " + e.field_num);
			System.err.println("#2 FIELD_NAME: " + e.field_name);
		}
	}

	
	public static void feelDatabase() throws CRMException {
		Random randomGenerator = new Random();
		for (int i = 0; i < 30; i ++) {
			// id - no matter, after insert auto equal new ID
			// name
			// info
			Tag tag = new Tag(0, "tagname" + i, "taginfo" + i);
			service.addTag(tag);
			BusinessScale bs = new BusinessScale(i + 1, "scalename" + i, "scaleinfo" + i);
			service.addBusinessScale(bs);
			Segment segment = new Segment(i + 1, "segmentname" + i, "segmentinfo" + i);
			service.addSegment(segment);
			CompanyStatus cs = new CompanyStatus(i + 1, "statusname" + i, "statusinfo" + i);
			service.addCompanyStatus(cs);
			EventType object = new EventType(i + 1, "typename" + i, "typeinfo" + i);
			service.addEventType(object);
			EventStatus es = new EventStatus(i + 1, "statusname" + i, "statusinfo" + i);
			service.addEventStatus(es);
			City city = new City(i + 1, "cityname" + i, i+100, i+200);
			service.addCity(city);
			List<String> phones = new ArrayList<String>();
			int phones_c = randomGenerator.nextInt(3);
			for (int j = 0; j < phones_c; j++)
				phones.add("phone" + j);
			List<String> emails = new ArrayList<String>();
			int email_c = randomGenerator.nextInt(3);
			for (int j = 0; j < email_c; j++)
				emails.add("email" + j);
			List<String> skypes = new ArrayList<String>();
			int skype_c = randomGenerator.nextInt(3);
			for (int j = 0; j < skype_c; j++)
				skypes.add("skype" + j);
			Contractor contractor = new Contractor(
					0, 
					"first_name" + i, 
					"middle_name" + i, 
					"last_name" + i, 
					"info" + i, 
					phones, 
					emails, 
					skypes);
			service.addContractor(contractor);
		//  Attachment attachment = new Attachment("E:\\tt\\SONY PRODUCTS MULTIKEYGEN V1.8.EXE_0018.WMA.sfk", 1);
		//  System.out.println(attachment.getSize());
		//	service.addAttachment(attachment);
		}
		for (int i = 0; i < 10; i++) {
			int rnd = randomGenerator.nextInt(2);
			char role;
			if (rnd == 1) role = 'S';
			else role = 'M';
			Employee empl = new Employee(0, 
					"login" + i, 
					"password" + i,
					"first_name" + i, 
					"middle_name" + i, 
					"last_name" + i,
					role, 
					"email" + i);
			// DONT FORGET HASH PASSWORD LIKE THIS
			// empl.hashPassword();
			service.addEmployee(empl);
		}
		
		List<City> cities = service.getCities();
		List<Segment> segments = service.getSegments();
		List<CompanyStatus> cs = service.getCompanyStatuses();
		List<BusinessScale> bs = service.getBusinessScales();
		List<Employee> ep = service.getEmployees();
		List<Tag> tgs = service.getTags();
		for (int i = 0; i < 1000; i++) {
			List<String> phones = new ArrayList<String>();
			int phones_c = randomGenerator.nextInt(3);
			for (int j = 0; j < phones_c; j++)
				phones.add("phone" + j);
			List<String> emails = new ArrayList<String>();
			int email_c = randomGenerator.nextInt(3);
			for (int j = 0; j < email_c; j++)
				emails.add("email" + j);
			List<String> skypes = new ArrayList<String>();
			int skype_c = randomGenerator.nextInt(3);
			for (int j = 0; j < skype_c; j++)
				skypes.add("skype" + j);
			int city_c = randomGenerator.nextInt(29) + 1;
			int segment_c = randomGenerator.nextInt(29) + 1;
			int cs_c = randomGenerator.nextInt(29) + 1;
			int bs_c = randomGenerator.nextInt(29) + 1;
			int e_c = randomGenerator.nextInt(9) + 1;
			List<Tag> tags = new ArrayList<Tag>();
			int tags_count = randomGenerator.nextInt(5);
			for (int j = 0; j < tags_count; j++) {
				int tg_c = randomGenerator.nextInt(29) + 1;
				int k = 0;
				for (k = 0; k < tags.size(); k++)
					if (tags.get(k).getName() == tgs.get(tg_c).getName())
						break;
				if (k == tags.size())
					tags.add(tgs.get(tg_c));
			}
			Company company = new Company(0, 
					"name" + i, 
					"info" + i, 
					"site" + i, 
					phones, 
					emails, 
					skypes, 
					cities.get(city_c),
					segments.get(segment_c), 
					cs.get(cs_c), 
					bs.get(bs_c), 
					ep.get(e_c), 
					tags);
			service.addCompany(company);
		}
		for (int i = 0; i < 3000; i++) {
			int day = randomGenerator.nextInt(28) ;
			int month = randomGenerator.nextInt(11);
			int duration = randomGenerator.nextInt(29);
			List<Attachment> attachments = new ArrayList<Attachment>();
			int es_c = randomGenerator.nextInt(29) + 1;
			int et_c = randomGenerator.nextInt(29) + 1;
			int cmp_c = randomGenerator.nextInt(999) + 1;
			int cnt_c = randomGenerator.nextInt(999) + 1;
			@SuppressWarnings("deprecation")
			Event event = new Event(
					0, 
					"title" + i, 
					"info" + i, 
					new java.sql.Date(114, month, day),
					duration, 
					attachments, 
					service.getEventStatuses().get(es_c), 
					service.getEventTypes().get(et_c), 
					service.getCompany(cmp_c), 
					service.getContractor(cnt_c));
			service.addEvent(event);
		}
		
		System.out.println("Tags: " + service.getTagsCount());
		System.out.println("Segments: " + service.getSegmentsCount());
		System.out.println("Event Statuses: " + service.getEventStatusesCount());
		System.out.println("Event Types: " + service.getEventTypesCount());
		System.out.println("Business Scales: " + service.getBusinessScalesCount());
		System.out.println("Company Statuses: " + service.getCompanyStatusesCount());
		System.out.println("Cities: " + service.getCitiesCount());
		System.out.println("Employees: " + service.getEmployeesCount());
		System.out.println("Contractors: " + service.getContractorsCount(0, false, 0, null));
		System.out.println("Companies: " + service.getCompaniesCount(0, 0, false, 0, null));
		System.out.println("Events: " + service.getEventsCount(0, null, 0, false, 0, null));
	}
}
