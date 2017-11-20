package be.mrouard.model.entity;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("bill")
public class Bill extends Entity {
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("name")
	private String name;
	@XStreamAlias("dueDate")
	private Date dueDate;
	@XStreamOmitField
	private Float totalCredit;
	@XStreamOmitField
	private Float totalDebit;
	@XStreamOmitField
	private Float totalCreditTreasury;
	@XStreamOmitField
	private Float totalDebitTreasury;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Float getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(Float totalCredit) {
		this.totalCredit = totalCredit;
	}
	public Float getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(Float totalDebit) {
		this.totalDebit = totalDebit;
	}
	public Float getTotalCreditTreasury() {
		return totalCreditTreasury;
	}
	public void setTotalCreditTreasury(Float totalCreditTreasury) {
		this.totalCreditTreasury = totalCreditTreasury;
	}
	public Float getTotalDebitTreasury() {
		return totalDebitTreasury;
	}
	public void setTotalDebitTreasury(Float totalDebitTreasury) {
		this.totalDebitTreasury = totalDebitTreasury;
	}
}
