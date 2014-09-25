package vit.mini.vitfaculty.data;

import java.util.List;

public class Course {
	private int class_number;
	private String course_name;
	private String course_code;
	private String room;
	private List<Student> students;
	private String slot;
	
	public Course(){
		this.class_number=0;
		this.course_name=null;
		this.room = null;
		this.course_code = null;
		this.students = null;
		this.setSlot(null);
	}
	
	public Course(int class_number,String course_name,String room,List<Student> students,String course_code,String slot){
		setClass_number(class_number);
		setCourse_name(course_name);
		setRoom(room);
		setStudents(students);
		setCourse_code(course_code);
		setSlot(slot);
	}
	
	public int getClass_number() {
		return class_number;
	}
	public void setClass_number(int class_number) {
		this.class_number = class_number;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}
	
	
}
