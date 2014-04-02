package entity;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private int id;
	private String name;
	private String info;
	private String site;
	private List<String> phones = new ArrayList<String>();
	private List<String> emails = new ArrayList<String>();
	private List<String> skypes = new ArrayList<String>();
	private City city;
	private Segment segment;
	private CompanyStatus companyStatus;
	private BusinessScale businessScale;
	private Employee employee;
	private List<Tag> tags = new ArrayList<Tag>();
	
	public Company (int id, String name, String info, String site, List<String> phones, List<String> emails,
			List<String> skypes, City city, Segment segment, CompanyStatus companyStatus, 
			BusinessScale businessScale, Employee employee, List<Tag> tags) {
		this.id = id;
		this.name = name;
		this.info = info;
		this.site = site;
		this.phones = phones;
		this.emails = emails;
		this.skypes = skypes;
		this.city = city;
		this.segment = segment;
		this.businessScale = businessScale;
		this.companyStatus = companyStatus;
		this.employee = employee;
		this.tags = tags;
	}
	public String isEmployeeValid() {
		String res = "";
		if (employee == null || employee.getId() == 0) res += "Пожалуйста, выберите сотрудника.";
		return res;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public List<String> getPhones() {
		return phones;
	}
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<String> getSkypes() {
		return skypes;
	}
	public void setSkypes(List<String> skypes) {
		this.skypes = skypes;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Segment getSegment() {
		return segment;
	}
	public void setSegment(Segment segment) {
		this.segment = segment;
	}
	public CompanyStatus getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(CompanyStatus companyStatus) {
		this.companyStatus = companyStatus;
	}
	public BusinessScale getBusinessScale() {
		return businessScale;
	}
	public void setBusinessScale(BusinessScale businessScale) {
		this.businessScale = businessScale;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getPhonesAsString() {
		if(phones == null) return "";
		String phoneString = "";
		for (String phone : phones) {
			phoneString += phone + ";";
		}
		phoneString = phoneString.substring(0, phoneString.length() - 1);
		return phoneString;
	}
	public String getSkypesAsString() {
		if(skypes == null) return "";
		String skypeString = "";
		for (String skype : skypes) {
			skypeString += skype + ";";
		}
		skypeString = skypeString.substring(0, skypeString.length() - 1);
		return skypeString;
	}
	public String getEmailsAsString() {
		if(emails == null) return "";
		String emailString = "";
		for (String email : emails) {
			emailString += email + ";";
		}
		emailString = emailString.substring(0, emailString.length() - 1);
		return emailString;
	}
	
	public String toJson() {
		String str = "{id: '" + id;
		str += "', name: '";
		if (name != null) str += name;
		str += "', info: '";
		if (info != null) str += info;
		str += "', site: '";
		if (site != null) str += site;
		str += "', phones: [";
		if (phones != null)
		for (int i = 0; i < phones.size(); i++) {
			str += "'" + phones.get(i) + "'";
			if (i < phones.size() - 1) str +=",";
		}
		str += "], emails: [";
		if (emails != null)
		for (int i = 0; i < emails.size(); i++) {
			str += "'" + emails.get(i) + "'";
			if (i < emails.size() - 1) str +=",";
		}
		if (skypes != null)
		str += "], skypes: [";
		for (int i = 0; i < skypes.size(); i++) {
			str += "'" + skypes.get(i) + "'";
			if (i < skypes.size() - 1) str +=",";
		}
		str += "]";
		str += ", City: ";
		if (city != null) str += city.toJson();
		else str += "{}";
		str += ", Segment: ";
		if (segment != null) str += segment.toJson();
		else str += "{}";
		str += ", BusinessScale: ";
		if (businessScale != null) str += businessScale.toJson();
		else str += "{}";
		str += ", CompanyStatus: ";
		if (companyStatus != null) str += companyStatus.toJson();
		else str += "{}";
		str += ", Employee:";
		if (employee != null) str += employee.toJson();
		else str += "{}";
		str += ", Tags: [";
		if (tags != null && tags.size() > 0) {
			for (int i = 0; i < tags.size(); i++) {
				str += tags.get(i).toJson();
				if (i < tags.size() - 1) str +=",";
			}
		}
		str += "]";
		str += "}";
		return str;
	}	 
}
