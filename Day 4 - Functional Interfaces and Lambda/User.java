public class User{
	private String id;
	private String userName;
	private String password;

	public User(String id, String userName, String password){
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String toString(){
		return "The user is with id : "+id+", username : "+userName+", and password : "+password; 
	}	
}