package be.mrouard.model.entity;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("trans")
public class Trans extends Entity {
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("id")
	private long id;
	@XStreamAlias("amount")
	private float amount;
	@XStreamAlias("type")
	private String type;
	@XStreamAlias("date")
	private Date date;
	@XStreamAlias("person")
	private Person person;
	@XStreamAlias("bill")
	private Bill bill;
	@XStreamAlias("bAmount")
	private float bAmount;
	@XStreamAlias("vAmount")
	private float vAmount;
	@XStreamAlias("gAmount")
	private float gAmount;
	@XStreamAlias("cAmount")
	private float cAmount;
	@XStreamAlias("reference")
	private String reference;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public float getbAmount() {
		return bAmount;
	}
	public void setbAmount(float bAmount) {
		this.bAmount = bAmount;
	}
	public float getvAmount() {
		return vAmount;
	}
	public void setvAmount(float vAmount) {
		this.vAmount = vAmount;
	}
	public float getgAmount() {
		return gAmount;
	}
	public void setgAmount(float gAmount) {
		this.gAmount = gAmount;
	}
	public float getcAmount() {
		return cAmount;
	}
	public void setcAmount(float cAmount) {
		this.cAmount = cAmount;
	}
	public String getFullType() {
		String type=getType().toUpperCase();
		if (type.equals("C")) return "Crédit";
		if (type.equals("D")) return "Débit";
		if (type.equals("P")) return "Pénalité";
		return "";
	}
}
