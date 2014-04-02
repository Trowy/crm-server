package entity;

public class CompanyStatus {
	private int id;
	private String name;
	private String info;
	
	public CompanyStatus(int id, String name, String info) {
		this.id = id;
		this.name = name;
		this.info = info;
	}
	public String isNameValid() {
		String res = "";
		if (name == null || name == "") res += "Пожалуйста, введите название статуса компании.";
		return res;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return new String("id: " + id + "; name: " + name + "; info: " + info + "\n");
	}
	public String toJson() {
		String str = "{id: '" + id + "', name: '";
		if (name != null) str += name;
		str += "', info: '";
		if (info != null) str += info;
		str += "'}";
		return str;
	}
}
