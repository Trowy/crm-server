package entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class Attachment {
	private int id;
	private int event_id;
	private String name;
	private String extension;
	private long size;
	private byte[] file;
	
	public Attachment(int id, int event_id, String name, String extension, long  size, byte[] file) {
		this.id = id;
		this.event_id = event_id;
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.file = file;
	}
	
	public Attachment(String path, int event_id) {
		loadFile(path);
		this.event_id = event_id;
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
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public void parseFileNameAndExtension() {
		String newFileName = "unnamed";
		String newFileExt = "unknow";
		int index = name.lastIndexOf(".");
		if(index >= 0) {
			newFileName = name.substring(0, index);
			newFileExt = name.substring(index + 1, name.length());
		}
		name = newFileName;
		extension = newFileExt;
	}
	
	public String loadFile(String path) {
		try {
		File file = new File(path);
		name = file.getName();
		parseFileNameAndExtension();
		size = file.length();
		InputStream input = new FileInputStream(file);
		byte[] bytes = new byte[(int)file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead=input.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
		input.close();
		this.file = bytes;
		} catch (FileNotFoundException e1) {
			return "Ошибка! Файл не найден.";
		} catch (IOException e2) {
			return "Во время добавления файла произошла ошибка. Попробуйте снова или обратитесь в службу поддержки.";
		}
		return "";
	}
	
	public String saveFile(String path) {
		  try { 
			  String fileName = name + "." + extension; 
			  OutputStream outStream = new FileOutputStream(path + fileName); 
			  outStream = new java.io.BufferedOutputStream(outStream); 
			  outStream.write(file); 
			  outStream.close(); 
			  } catch (IOException e) { 
				  return "Во время сохранения файла произошла ошибка. Попробуйте снова или обратитесь в службу поддержки.";
			  }
		return "";
	}
	
	public String getSizeString() {
		String sizeString = null;
		double attachSize = (double) size;
		if (attachSize < 1024) sizeString = format(attachSize, 1) + " b";
		else {
			attachSize = (double) attachSize / 1024;
			if (attachSize < 1024) sizeString = format(attachSize, 1) + " kb";
			else {
				attachSize = attachSize / 1024;
				sizeString = format(attachSize, 1) + " mb";
			}
		}
		return sizeString;
	}
	
	private double format(double num, int col) {
		NumberFormat aFormat = NumberFormat.getNumberInstance(Locale.US);
		aFormat.setMaximumFractionDigits(col);
		return Double.parseDouble(aFormat.format(num));
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public String toJson() {
		return new String(
			   "{ id: '" + id + 
			  "', event_id: '" + event_id + 
			  "', name: '" + name + 
			  "', extension: '"+ extension +
			  "', size: '" + size + 
			  "', file: '" + file + "'}");
	}
	public String fileToJson() {
		return new String ("{file: '}" + file + "'}");
	}
}
