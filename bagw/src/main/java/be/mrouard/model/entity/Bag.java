package be.mrouard.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import be.mrouard.BagException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("bag")
public class Bag implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XStreamOmitField
	private long nextId;
	
	@XStreamAlias("persons")
	private List<Person> persons;

	@XStreamAlias("bills")
	private List<Bill> bills;

	@XStreamAlias("transactions")
	private List<Trans> transes;

	public void init() {
		for (Trans  t:getTranses()) if (this.nextId<=t.getId()) this.nextId=t.getId()+1;
		for (Person p:getPersons()) if (this.nextId<=p.getId()) this.nextId=p.getId()+1;
		for (Bill   b:getBills())   if (this.nextId<=b.getId()) this.nextId=b.getId()+1;
		updateBalance();
		sortTransactions();
		sortPersons();
		sortBills();
	}
	
	public long getNextId() {
		return this.nextId++;
	}
	
	public List<Trans> getTranses() {
		return transes;
	}

	public void setTranses(List<Trans> transes) {
		if (transes==null) transes=new ArrayList<Trans>();
		this.transes = transes;
	}
	
	public Trans getTransaction(long id) throws BagException {
		for (Trans t:transes) if (t.getId()==id) return t;
		throw new BagException();
	}		

	public void addTrans(Trans t) {
		if (transes==null) transes=new ArrayList<Trans>();
		transes.add(t);
		sortTransactions();
	}
	
	public Trans createTrans() {
		Trans t=new Trans();
		t.setId(getNextId());
		t.setType("C");
		t.setAmount(0);
		t.setDate(new Date());
		addTrans(t);
		sortTransactions();
		return t;
	}

	public void updateTrans(Trans t) {
		deleteTransaction(t.getId());
		addTrans(t);
		updateBalance(t.getPerson(), t.getBill());
		sortTransactions();
	}
	
	
	public void deleteTransaction(long id) {
		for (int i=0; i<transes.size(); i++) {
			if (transes.get(i).getId()==id) {
				Person p=transes.get(i).getPerson();
				Bill b=transes.get(i).getBill();
				transes.remove(i);
				System.out.println("Transaction deleted");
				updateBalance(p, b);
			}
		}
	}
	
	public List<Person> getPersons() {
		if (persons==null) persons=new ArrayList<Person>();
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public Person getPerson(long id) throws BagException {
		for (Person o:persons) if (o.getId()==id) return o;
		throw new BagException();
	}		

	public void addPerson(Person o) {
		if (persons==null) persons=new ArrayList<Person>();
		persons.add(o);
		sortPersons();
	}
	
	public Person createPerson() {
		Person o=new Person();
		o.setId(getNextId());
		o.setNameLast("");
		o.setNameFirst("");
		addPerson(o);
		sortPersons();
		return o;
	}

	public void updatePerson(Person o) {
		deletePerson(o.getId());
		addPerson(o);
		sortPersons();
	}
	
	public void deletePerson(long id) {
		for (int i=0; i<persons.size(); i++) {
			if (persons.get(i).getId()==id) {
				persons.remove(i);
				System.out.println("Person deleted");
			}
		}
	}

	public List<Bill> getBills() {
		if (bills==null) bills=new ArrayList<Bill>();
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	public Bill getBill(long id) throws BagException {
		for (Bill o:bills) if (o.getId()==id) return o;
		throw new BagException();
	}		

	public void addBill(Bill o) {
		if (bills==null) bills=new ArrayList<Bill>();
		bills.add(o);
		sortBills();
	}
	
	public Bill createBill() {
		Bill o=new Bill();
		o.setId(getNextId());
		o.setName("");
		o.setDueDate(new Date());
		addBill(o);
		sortBills();
		return o;
	}

	public void updateBill(Bill o) {
		deleteBill(o.getId());
		addBill(o);
		sortBills();
	}
	
	public void deleteBill(long id) {
		for (int i=0; i<bills.size(); i++) {
			if (bills.get(i).getId()==id) {
				bills.remove(i);
				System.out.println("Bill deleted");
			}
		}
	}

	private void sortTransactions() {
		Collections.sort(transes, new TransComparator());
	}

	private class TransComparator implements Comparator<Trans> {
		@Override
		public int compare(Trans o1, Trans o2) {
			int c=o2.getDate().compareTo(o1.getDate());
			if (c==0) return o1.getPerson().getNameLast().compareTo(o2.getPerson().getNameLast());
			return c;
		}
		
	}
	
	private void sortPersons() {
		Collections.sort(persons, new PersonComparator());
	}

	private class PersonComparator implements Comparator<Person> {
		@Override
		public int compare(Person o1, Person o2) {
			int c=o1.getNameLast().compareTo(o2.getNameLast());
			if (c==0) return o1.getNameFirst().compareTo(o2.getNameFirst());
			return c;
		}
		
	}
	
	private void sortBills() {
		Collections.sort(bills  , new BillComparator());
	}
	
	private class BillComparator implements Comparator<Bill> {
		@Override
		public int compare(Bill o1, Bill o2) {
			return o1.getName().compareTo(o2.getName());
		}
		
	}
	
	private void updateBalance(Person p, Bill b) {
		if (p!=null) updateBalance(p);
		if (b!=null) updateBalance(b);		
	}

	private void updateBalance(Person p) {
		float balance=0;
		List<Trans> personTranses=getTransactionsByPersonId(p.getId());
		for (Trans t:personTranses) { 
			if (t.getType().equals("C")) 
				 balance+=t.getAmount();
			else balance-=t.getAmount();
		}
		p.setBalance(balance);		
	}

	private void updateBalance(Bill b) {
		float totalCredit=0;
		float totalDebit=0;
		float totalCreditTreasury=0;
		float totalDebitTreasury=0;
		List<Trans> billTranses=getTransactionsByBillId(b.getId());
		for (Trans t:billTranses) { 
			if (t.getType().equals("C"))
				if (t.getPerson().getId()!=555703)
					  totalCredit+=t.getAmount();
				else  totalCreditTreasury+=t.getAmount();
			else if (t.getPerson().getId()!=555703)
			 		  totalDebit+=t.getAmount();
			 	 else totalDebitTreasury+=t.getAmount();
		}
		b.setTotalCredit(totalCredit);
		b.setTotalDebit(totalDebit);
		b.setTotalCreditTreasury(totalCreditTreasury);
		b.setTotalDebitTreasury(totalDebitTreasury);		
	}

	public void updateBalance() {
		for (Person p:persons) updateBalance(p);
		for (Bill b:bills) updateBalance(b);
	}
	
	
	public List<Trans> getTransactionsByPersonId(long id) {
		ArrayList<Trans> result=new ArrayList<Trans>(); 
		for (Trans t:transes) {
			Person p=t.getPerson();
			if (p!=null && p.getId()==id) result.add(t);
		}
		return result;
	}	
	
	public List<Trans> getTransactionsByBillId(long id) {
		ArrayList<Trans> result=new ArrayList<Trans>(); 
		for (Trans t:transes) {
			Bill b=t.getBill();
			if (b!=null && b.getId()==id) result.add(t);
		}
		return result;
	}	
	
	public void addTransactions(List<Trans> transes) {
		this.transes.addAll(transes);
	}	
	
	public Person getPersonByName(String name)  throws BagException {
		for (Person p:persons) {
			if (   name.toLowerCase().contains(p.getNameLast().toLowerCase()) 
				&& name.toLowerCase().contains(p.getNameFirst().toLowerCase())) {
				return p;
			}
		}
		throw new BagException();
	}	

	public Person getPersonByAccount(String account, String description)  throws BagException {
		for (Person p:persons) {
			if (p.getAccount()!=null) {
				String[] accounts=p.getAccount().split(";");
				for (String a:accounts) {
					String cleanAccount=a.replaceAll("-", "").replaceAll(" ", "");
					if (   cleanAccount!=null && cleanAccount.length()>0 
						&& (   account.replaceAll(" ", "").replaceAll("-", "").contains(cleanAccount)
						    || description.replaceAll(" ", "").replaceAll("-", "").contains(cleanAccount))) {
						return p;
					}
				}
			}
		}
		throw new BagException();
	}
}
