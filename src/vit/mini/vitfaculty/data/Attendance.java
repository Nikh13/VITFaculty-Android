package vit.mini.vitfaculty.data;

public class Attendance {
	private String regno;
	private boolean status;
	
	public Attendance(){
		this.setRegno(null);
		this.setStatus(false);
	}
	
	public Attendance(String regno){
		this.setRegno(regno);
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
