package BF;

public class Diner {
	String dCate;
	String dName;
	String dTime;
	int dMinPr;
	String uId;
	public String getdCate() {
		return dCate;
	}
	public void setdCate(String dCate) {
		this.dCate = dCate;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getdTime() {
		return dTime;
	}
	public void setdTime(String dTime) {
		this.dTime = dTime;
	}
	public int getdMinPr() {
		return dMinPr;
	}
	public void setdMinPr(int dMinPr) {
		this.dMinPr = dMinPr;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	@Override
	public String toString() {
		return "Diner [dCate=" + dCate + ", dName=" + dName + ", dTime=" + dTime + ", dMinPr="
				+ dMinPr + ", uId=" + uId + "]";
	}
	
	
	
}
