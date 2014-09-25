package vit.mini.vitfaculty.data;

import java.util.List;

public class Student {
	
	private String registration_number;
	private int class_number;
	private List<String> attending_dates;
	
	public Student(String registration_number,int class_number,List<String> attending_dates){
		setRegistration_number(registration_number);
		setAttending_dates(attending_dates);
		setClass_number(class_number);
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
		this.registration_number = null;
		this.class_number = 0;
		this.attending_dates = null;
	}

	public String getRegistration_number() {
		return registration_number;
	}
	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}
	public int getClass_number() {
		return class_number;
	}
	public void setClass_number(int class_number) {
		this.class_number = class_number;
	}
	public List<String> getAttending_dates() {
		return attending_dates;
	}
	public void setAttending_dates(List<String> attending_dates) {
		this.attending_dates = attending_dates;
	}

}
