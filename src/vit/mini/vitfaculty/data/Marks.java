package vit.mini.vitfaculty.data;

import java.util.List;

public class Marks{
	
	private List<String> regnos;
	private List<Integer> marks;
	
	public Marks(){
		this.setRegnos(null);
		this.setMarks(null);
	}
	public Marks(List<String> regnos, List<Integer> marks){
		this.setRegnos(regnos);
		this.setMarks(marks);
	}
	public List<String> getRegnos() {
		return regnos;
	}
	public void setRegnos(List<String> regnos) {
		this.regnos = regnos;
	}
	public List<Integer> getMarks() {
		return marks;
	}
	public void setMarks(List<Integer> marks) {
		this.marks = marks;
	}
	
	public int getMark(String regno){
		int mark = 0;
		for (int i = 0;i<regnos.size();i++){
			if(regnos.get(i).equals(regno))
				mark = marks.get(i);
		}
		if(mark == 0){
			return -1;
		}
		else{
			return mark;
		}
	}
	
}