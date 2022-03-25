package BF;

public class Account {
	
	private String account;
	private String uId;
	private int balance;
	
	
	

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	@Override
	public String toString() {
		return "Account [account=" + account + ", uId=" + uId + ", balance=" + balance + "]";
	}
	
	
	
	
}
