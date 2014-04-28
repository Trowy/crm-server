package entity;

import service.BCrypt;

public class Employee {
	// Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
	private static int workload = 10;
	private int id;
	private String login;
	private String password;
	private String first_name;
	private String middle_name;
	private String last_name;
	private char role;
	private String email;
	
	public Employee (int id, String login, String password, String first_name, String middle_name,
			String last_name, char role, String email) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.role = role;
		this.email = email;
	}
	public String isLoginValid() {
		String res = "";
		if (login == null || login == "") res += "Пожалуйста, укажите логин сотрудника.";
		return res;
	}
	public String isPasswordValid() {
		String res = "";
		if (password == null || password == "") res += "Пожалуйста, укажите пароль сотрудника.";
		return res;
	}
	public String isFirstNameValid() {
		String res = "";
		if (first_name == null || first_name == "") res += "Пожалуйста, укажите имя сотрудника.";
		return res;
	}
	public String isMiddleNameValid() {
		String res = "";
		if (middle_name == null || middle_name == "") res += "Пожалуйста, укажите отчество сотрудника.";
		return res;
	}
	public String isLastNameValid() {
		String res = "";
		if (last_name == null || last_name == "") res += "Пожалуйста, укажите фамилию сотрудника.";
		return res;
	}
	public String isRoleValid() {
		String res = "";
		if (role == ' ' || (role != 'S' && role != 'M')) res += "Пожалуйста, укажите роль сотрудника.";
		return res;
	}
	public String isEmalValid() {
		String res = "";
		if (email != null && email != "") {
			if (!email.contains("@") || !email.contains(".")) res += "Пожалуйста, проверьте правильность заполнения поля email сотрудника.";
		}
		return res;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public char getRole() {
		return role;
	}
	public void setRole(char role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	/**
	 * This method can be used to generate a string representing an account password
	 * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
	 * hash string of length=60
	 * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
	 * A workload of 12 is a very reasonable safe default as of 2013.
	 * This automatically handles secure 128-bit salt generation and storage within the hash.
	 * @param password_plaintext The account's plaintext password as provided during account creation,
	 *			     or when changing an account's password.
	 * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
	 */
	public void hashPassword() {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(this.password, salt);
		this.password = hashed_password;
	}
 
	/**
	 * This method can be used to verify a computed hash from a plaintext (e.g. during a login
	 * request) with that of a stored hash from a database. The password hash from the database
	 * must be passed as the second variable.
	 * @param password_plaintext The account's plaintext password, as provided during a login request
	 * @param stored_hash The account's stored password hash, retrieved from the authorization database
	 * @return boolean - true if the password matches the password of the stored hash, false otherwise
	 */
	public boolean checkPassword(String password_plaintext) {
		boolean password_verified = false;
 
		if(null == this.password || !this.password.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
 
		password_verified = BCrypt.checkpw(password_plaintext, this.password);

		return(password_verified);
	}
	
	public String toString() {
		return new String(    "id: " + id +
							"; login: " + login +
							"; password: " + password +
						    "; first_name: " + first_name +
						    "; middle_name: " + middle_name +
						    "; last_name: " + last_name +
						    "; role: " + role +
							"; email: " + email +
							"\n");
	}
	public String toJson() {
		String str = "{ id: '" + id +
					 "', login: '" + login +
					 "', password: '" + password +
					 "', first_name: '" + first_name +
					 "', middle_name: '" + middle_name +
					 "', last_name: '" + last_name +
					 "', fio: '" + last_name + " " + first_name + " " + middle_name + 
					 "', role: '" + role +
					 "', email: '";
		if (email != null) str += email;
		str += "'}";
		return str;
	}
}