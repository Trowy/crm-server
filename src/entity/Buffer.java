package entity;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	
	private List<EventStatus> eventStatuses = new ArrayList<EventStatus>();
	private List<EventType> eventTypes = new ArrayList<EventType>();
	private List<Employee> employees = new ArrayList<Employee>();
	private List<Tag> tags = new ArrayList<Tag>();
	private List<BusinessScale> businessScales = new ArrayList<BusinessScale>();
	private List<CompanyStatus> companyStatuses = new ArrayList<CompanyStatus>();
	private List<Segment> segments = new ArrayList<Segment>();
	private List<City> cities = new ArrayList<City>();

	public List<EventStatus> getEventStatuses() {
		return eventStatuses;
	}
	public void setEventStatuses(List<EventStatus> eventStatuses) {
		this.eventStatuses = eventStatuses;
	}
	public void addEventStatus(EventStatus eventStatus) {
		eventStatuses.add(eventStatus);
	}
	public EventStatus getEventStatus(int id) {
		for (int i = 0; i < eventStatuses.size(); i++) {
			if (eventStatuses.get(i).getId() == id)
				return eventStatuses.get(i);
		}
		return null;
	}
	public void removeEventStatus(int id) {
		for (int i = 0; i < eventStatuses.size(); i++) {
			if (eventStatuses.get(i).getId() == id)
				eventStatuses.remove(i);
		}
	}
	public List<EventType> getEventTypes() {
		return eventTypes;
	}
	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}
	public void addEventType(EventType eventType) {
		eventTypes.add(eventType);
	}
	public EventType getEventType(int id) {
		for (int i = 0; i < eventTypes.size(); i++) {
			if (eventTypes.get(i).getId() == id)
				return eventTypes.get(i);
		}
		return null;
	}
	public void removeEventType(int id) {
		for (int i = 0; i < eventTypes.size(); i++) {
			if (eventTypes.get(i).getId() == id)
				eventTypes.remove(i);
		}
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public Employee getEmployee(int id) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == id)
				return employees.get(i);
		}
		return null;
	}
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
	public void removeEmployee(int id) {
		for (int i = 0; i < employees.size(); i++) {
			if (employees.get(i).getId() == id)
				employees.remove(i);
		}
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Tag getTag(int id) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getId() == id)
				return tags.get(i);
		}
		return null;
	}
	public void addTag(Tag tag) {
		tags.add(tag);
	}
	public void removeTag(int id) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getId() == id)
				tags.remove(i);
		}
	}
	public List<BusinessScale> getBusinessScales() {
		return businessScales;
	}
	public void setBusinessScales(List<BusinessScale> businessScales) {
		this.businessScales = businessScales;
	}
	public void addBusinessScale(BusinessScale businessScale) {
		businessScales.add(businessScale);
	}
	public void removeBusinessScale(int id) {
		for (int i = 0; i < businessScales.size(); i++) {
			if (businessScales.get(i).getId() == id)
				businessScales.remove(i);
		}
	}
	public BusinessScale getBusinessScale(int id) {
		for (int i = 0; i < businessScales.size(); i++) {
			if (businessScales.get(i).getId() == id)
				return businessScales.get(i);
		}
		return null;
	}
	public List<CompanyStatus> getCompanyStatuses() {
		return companyStatuses;
	}
	public void setCompanyStatuses(List<CompanyStatus> companyStatuses) {
		this.companyStatuses = companyStatuses;
	}
	public void addCompanyStatus(CompanyStatus companyStatus) {
		companyStatuses.add(companyStatus);
	}
	public void removeCompanyStatus(int id) {
		for (int i = 0; i < companyStatuses.size(); i++) {
			if (companyStatuses.get(i).getId() == id)
				companyStatuses.remove(i);
		}
	}
	public CompanyStatus getCompanyStatus(int id) {
		for (int i = 0; i < companyStatuses.size(); i++) {
			if (companyStatuses.get(i).getId() == id)
				return companyStatuses.get(i);
		}
		return null;
	}
	public List<Segment> getSegments() {
		return segments;
	}
	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
	public void addSegment(Segment segment) {
		segments.add(segment);
	}
	public void removeSegment(int id) {
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).getId() == id)
				segments.remove(i);
		}
	}
	public Segment getSegment(int id) {
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).getId() == id)
				return segments.get(i);
		}
		return null;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public void addCity(City city) {
		cities.add(city);
	}
	public void removeCity(int id) {
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).getId() == id)
				cities.remove(i);
		}
	}
	public City getCity(int id) {
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).getId() == id)
				return cities.get(i);
		}
		return null;
	}
}
