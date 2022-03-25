package BF;

public class OrderList {
	
	private String uId;
	private String dName;
	private int Onum;
	private String oDate;
	private String oPay;
	
	
	
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
	public int getOnum() {
		return Onum;
	}
	public void setOnum(int onum) {
		Onum = onum;
	}
	public String getoDate() {
		return oDate;
	}
	public void setoDate(String oDate) {
		this.oDate = oDate;
	}
	public String getoPay() {
		return oPay;
	}
	public void setoPay(String oPay) {
		this.oPay = oPay;
	}
	@Override
	public String toString() {
		return "Orderlist [uId=" + uId + ", dName=" + dName + ", Onum=" + Onum + ", oDate=" + oDate + ", oPay=" + oPay
				+ "]";
	}
	public int getoCnt() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setmName(String mName) {
		// TODO Auto-generated method stub
		
	}
	public void setoCnt(int mCnt) {
		// TODO Auto-generated method stub
		
	}
	public String getmName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
