public class Email_Class {
	
	private int count;
	private String email;
	
	public Email_Class(String name){
		count = 1;
		email = name;
	}
	
	public String getName() {
		return email;
	}
	
	public int getCount() {
		return count;
	}
	
	public void incrementCount() {
		count++;
	}
}
