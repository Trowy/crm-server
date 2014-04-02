package entity;

import java.util.ArrayList;
import java.util.List;

public class Contractor {
	private int id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String info;
	private List<String> phones = new ArrayList<String>();
	private List<String> emails = new ArrayList<String>();
	private List<String> skypes = new ArrayList<String>();
	
	public Contractor(int id, String first_name, String middle_name, String last_name, String info,
			List<String> phones, List<String> emails, List<String> skypes) {
		this.id = id;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.info = info;
		if (phones != null && phones.size() > 0) 
			this.phones = phones;
		if (emails != null && emails.size() > 0) 
			this.emails = emails;
		if (skypes != null && skypes.size() > 0) 
			this.skypes = skypes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public List<String> getSkypes() {
		return skypes;
	}
	public void setSkypes(List<String> skypes) {
		this.skypes = skypes;
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
	public String toString() {
		return new String(    "id: " + id +
						    "; first_name: " + first_name +
						    "; middle_name: " + middle_name +
						    "; last_name: " + last_name +
						    "; info: " + info +
						    "; phones: " + phones.toString() +
						    "; emails: " + emails.toString() +
						    "; skypes: " + skypes.toString()) + "\n";
	}
	public String toJson() {
		String str = "{id: '" + id;
		str += "', first_name: '";
		if (first_name != null) str += first_name;
		str += "', middle_name: '";
		if (middle_name != null) str += middle_name;
		str += "', last_name: '";
		if (last_name != null) str += last_name;
		str += "', info: '";
		if (info != null) str += info;
		str += "', phones: [";
		for (int i = 0; i < phones.size(); i++) {
			str += "'" + phones.get(i) + "'";
			if (i < phones.size() - 1) str +=",";
		}
		str += "], emails: [";
		for (int i = 0; i < emails.size(); i++) {
			str += "'" + emails.get(i) + "'";
			if (i < emails.size() - 1) str +=",";
		}
		str += "], skypes: [";
		for (int i = 0; i < skypes.size(); i++) {
			str += "'" + skypes.get(i) + "'";
			if (i < skypes.size() - 1) str +=",";
		}
		str += "]}";
		return str;
	}	 
}
