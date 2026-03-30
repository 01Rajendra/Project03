package in.co.rays.project_3.dto;

import java.util.Date;

public class BankDTO extends BaseDTO {

	private String Bank_Name;
	private String Account_NO;
	private String Customer_Name;
	private Date Dob;
	private String Address;

	public String getBank_Name() {
		return Bank_Name;
	}

	public void setBank_Name(String bank_Name) {
		Bank_Name = bank_Name;
	}

	public String getAccount_NO() {
		return Account_NO;
	}

	public void setAccount_NO(String account_NO) {
		Account_NO = account_NO;
	}

	public String getCustomer_Name() {
		return Customer_Name;
	}

	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
