package entity;

public class City {
	private int id;
	private String name;
	private int code;
	private int time_zone;
	
	public City(int id, String name, int code, int time_zone) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.time_zone = time_zone;
	}
	public String isNameValid() {
		String res = "";
		if (name == null || name == "") res += "Пожалуйста, введите название города.";
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(int time_zone) {
		this.time_zone = time_zone;
	}
	public String toString() {
		return new String("id: " + id + "; name: " + name + "; code: " + code + "; time_zone: " + time_zone + "\n");
	}
	public String toJson() {
		String str = "{id: '" + id + "', name: '";
		if (name != null) str += name;
		str += "', code: '" + code + "', time_zone: '"+ time_zone + "'}";
		return str;
	}
}
