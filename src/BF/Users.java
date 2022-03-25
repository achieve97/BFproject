package BF;

public class Users {

	private String uId;
	private String uPw;
	private String uName;
	private String uAddr;
	private String uEmail;
	private String uPhone;
	private String uBirth;
	private int uCode;
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuPw() {
		return uPw;
	}
	public void setuPw(String uPw) {
		this.uPw = uPw;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuAddr() {
		return uAddr;
	}
	public void setuAddr(String uAddr) {
		this.uAddr = uAddr;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuBirth() {
		return uBirth;
	}
	public void setuBirth(String uBirth) {
		this.uBirth = uBirth;
	}
	public int getuCode() {
		return uCode;
	}
	public void setuCode(int uCode) {
		this.uCode = uCode;
	}
	@Override
	public String toString() {
		return "Users [uId=" + uId + ", uPw=" + uPw + ", uName=" + uName + ", uAddr=" + uAddr + ", uEmail=" + uEmail
				+ ", uPhone=" + uPhone + ", uBirth=" + uBirth + ", uCode=" + uCode + "]";
	}
	
	
}
