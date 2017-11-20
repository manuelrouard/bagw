package be.mrouard.web.model;

import java.io.Serializable;

public class Total implements Serializable{
	
	private static final long serialVersionUID = 1L;

	float totalCredit=0F;
	float totalDebit=0F;
	float totalCreditTreasury=0F;
	float totalDebitTreasury=0F;
	 
	public float getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(float totalCredit) {
		this.totalCredit = totalCredit;
	}
	public float getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(float totalDebit) {
		this.totalDebit = totalDebit;
	}
	public float getTotalCreditTreasury() {
		return totalCreditTreasury;
	}
	public void setTotalCreditTreasury(float totalCreditTreasury) {
		this.totalCreditTreasury = totalCreditTreasury;
	}
	public float getTotalDebitTreasury() {
		return totalDebitTreasury;
	}
	public void setTotalDebitTreasury(float totalDebitTreasury) {
		this.totalDebitTreasury = totalDebitTreasury;
	}
}
