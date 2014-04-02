package service;

public class CRMException extends Exception {
	private static final long serialVersionUID = 1L;
	public String field_name = "";
	public int field_num = 0;
	public String msg = "";
	public CRMException(String message) {
		super(message); 
		this.msg = message;
	}
	public CRMException(String message, String field_name, int field_num) { 
		super(message); 
		this.field_name = field_name;
		this.field_num = field_num;
		this.msg = message;
	}
  //public FooException() { super(); }
  //public FooException(String message, Throwable cause) { super(message, cause); }
  //public FooException(Throwable cause) { super(cause); }
}
