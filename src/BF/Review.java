package BF;

public class Review {
	
	private int rNum;
	private String uId;
	private String dName;
	private String rReview;
	private int rStar;
	private String rDate;
	
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getrReview() {
		return rReview;
	}
	public void setrReview(String rReview) {
		this.rReview = rReview;
	}
	public int getrStar() {
		return rStar;
	}
	public void setrStar(int rStar) {
		this.rStar = rStar;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	@Override
	public String toString() {
		return "Review [rNum=" + rNum + ", uId=" + uId + ", dName=" + dName + ", rReview=" + rReview + ", rStar="
				+ rStar + ", rDate=" + rDate + "]";
	}
	
	
	
}
