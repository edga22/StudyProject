package system;

import java.sql.Timestamp;

public class Members {
	private String id;
	private String passwd;
	private String nickname;
	private String email;
	private Timestamp registerDate;
	
	Members(String id, String passwd, String nickname, String email, Timestamp registerDate){
		this.id = id;
		this.passwd = passwd;
		this.nickname = nickname;
		this.email = email;	
		this.registerDate = registerDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}
		
}
