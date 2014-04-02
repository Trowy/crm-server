package service;

public class UpdateResult {
	private int err_code;
	private String err_msg;
	
	public UpdateResult(int err_code , String err_msg) {
		this.err_code = err_code;
		this.err_msg = err_msg;
	}
	
	public int getErr_code() {
		return err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
}
