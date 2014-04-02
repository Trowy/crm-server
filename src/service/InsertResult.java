package service;

public class InsertResult {
	private int err_code;
	private String err_msg;
	private int id;
	
	public InsertResult(int id, int err_code , String err_msg) {
		this.err_code = err_code;
		this.err_msg = err_msg;
		this.id = id;
	}
	
	public int getErr_code() {
		return err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public int getId() {
		return id;
	}
}
