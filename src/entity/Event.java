package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Event {
	private int id;
	private String title;
	private String info;
	private Date date;
	private int duration;
	private List<Attachment> attachments = new ArrayList<Attachment>();
	private EventStatus eventStatus;
	private EventType eventType;
	private Company company;
	private Contractor contractor;
	
	public Event(int id, String title, String info, Date date, int duration, List<Attachment> attachments,
			EventStatus eventStatus, EventType eventType, Company company, Contractor contractor) {
		this.id = id;
		this.title = title;
		this.info = info;
		this.date = date;
		this.duration = duration;
		if (attachments != null && attachments.size() > 0)
			this.attachments = attachments;
		this.eventStatus = eventStatus;
		this.eventType = eventType;
		this.company = company;
		this.contractor = contractor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public EventStatus getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Contractor getContractor() {
		return contractor;
	}
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}
	public String toString() {
		return new String(    "id: " + id +
						    "; title: " + title +
						    "; info: " + info +
						    "; date: " + date +
						    "; duration: " + duration +
						    "; attachments count: " + attachments.size() +
						    "; eventStatus: " + eventStatus +
						    "; eventType: " + eventType +
						    "; company id: " + company.getId() +
						    "; contractor id: " + contractor.getId());
	}
	
	public String toJson() {
		String str = "";
		str += "{id: '" + id;
		str += "', title: '";
		if (title != null) str += title;
		str += "', info: '";
		if (info != null) str += info;
		str += "', date: '";
		if (date != null) str += date;
		str += "', duration: " + duration;
		str += ", Attachments: [";
		if (attachments != null && attachments.size() > 0) {
			for (int i = 0; i < attachments.size(); i++) {
				str += attachments.get(i).toJson();
				if (i < attachments.size() - 1) str +=",";
			}
		}	
		str += "]";
		str += ", EventStatus: ";
		if (eventStatus != null) str += eventStatus.toJson();
		else str += "{}";
		str += ", EventType: ";
		if (eventType != null) str += eventType.toJson();
		else str += "{}";
		str += ", Company: ";
		if (company != null) str += company.toJson();
		else str += "{}";
		str += ", Contractor: ";
		if (contractor != null) str += contractor.toJson();
		else str += "{}";
		str += "}";
		return str;
	}
}
