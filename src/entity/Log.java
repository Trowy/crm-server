package entity;

import java.sql.Date;

public class Log {
	private int id;
	private String action;	
	private String object;
	private int value_id;
	private String value_desc; 	// title or company name
	private Date date;
	private Employee employee;
	
	
	public Log(int id, String action, String object, int value_id, String value_desc, Date date, Employee employee) {
		this.id = id;
		this.action = action;
		this.object = object;
		this.value_id = value_id;
		this.value_desc = value_desc;
		this.date = date;
		this.employee = employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public int getValue_id() {
		return value_id;
	}
	public void setValue_id(int value_id) {
		this.value_id = value_id;
	}
	public String getValue_desc() {
		return value_desc;
	}
	public void setValue_desc(String value_desc) {
		this.value_desc = value_desc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String toJson() {
		String str = "{"
				+   "id: '" + id
				+"', action: '" + action
				+"', object: '" + object
				+"', value_id: '" + value_id
				+"', value_desc: '" + value_desc
				+"', date: '" + date
				+"', Employee:" + employee.toJson() + "}";
		return str;
	}
}
