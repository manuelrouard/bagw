package be.mrouard.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("person")
public class Person extends Entity {

	private static final long serialVersionUID = 1L;

	@XStreamAlias("nameLast")
	private String nameLast;
	@XStreamAlias("nameFirst")
	private String nameFirst;
	@XStreamAlias("mobile")
	private String mobile;
	@XStreamAlias("email")
	private String email;
	@XStreamAlias("account")
	private String account;
	@XStreamAlias("reminder")
	private int reminder;
	@XStreamOmitField
	private float balance=0;
	@XStreamOmitField
	private boolean send=false;
	
	public String getNameLast() {
		return nameLast;
	}
	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}
	public String getNameFirst() {
		return nameFirst;
	}
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getReminder() {
		return reminder;
	}
	public void setReminder(int reminder) {
		this.reminder = reminder;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public boolean isSend() {
		return send;
	}
	public void setSend(boolean send) {
		this.send = send;
	}
}
