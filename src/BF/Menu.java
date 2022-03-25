package BF;

public class Menu {
	
	private String dName;
	private String mName;
	private int mPrice;
	private int mCnt;
	private String mDetail;
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getmPrice() {
		return mPrice;
	}
	public void setmPrice(int mPrice) {
		this.mPrice = mPrice;
	}
	public int getmCnt() {
		return mCnt;
	}
	public void setmCnt(int mCnt) {
		this.mCnt = mCnt;
	}
	public String getmDetail() {
		return mDetail;
	}
	public void setmDetail(String mDetail) {
		this.mDetail = mDetail;
	}
	@Override
	public String toString() {
		return "Menu [dName=" + dName + ", mName=" + mName + ", mPrice=" + mPrice + ", mCnt=" + mCnt + ", mDetail="
				+ mDetail + "]";
	}
	
	
	
	

	
	
}
