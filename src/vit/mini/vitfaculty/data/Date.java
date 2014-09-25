package vit.mini.vitfaculty.data;

public class Date {
	private String date;
	private boolean post_status;
	
	public Date(){
		this.setDate(null);
		this.setPost_status(false); 
	}
	
	public Date(String date, boolean post_status){
		this.setDate(date);
		this.setPost_status(post_status); 
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean getPost_status() {
		return post_status;
	}

	public void setPost_status(boolean post_status) {
		this.post_status = post_status;
	}
}
