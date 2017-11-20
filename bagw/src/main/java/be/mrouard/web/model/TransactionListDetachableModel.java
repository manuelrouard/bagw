package be.mrouard.web.model;

import org.apache.wicket.model.LoadableDetachableModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;

public class TransactionListDetachableModel extends LoadableDetachableModel<Total> {

	private static final long serialVersionUID = 1L;

	private Bag bag;
	private Total total=new Total();
	
	public TransactionListDetachableModel(Bag bag) {
		this.bag=bag;
		
	}
	
	@Override
	protected Total load() {
        Person person=null;
        float totalCredit=0F, totalDebit=0F, totalCreditTreasury=0F, totalDebitTreasury=0F;
        for (Trans trans:bag.getTranses()) {
            person=trans.getPerson();
            if (person!=null) {
                if (person.getNameLast().equalsIgnoreCase("tresorerie")) {
                    if (trans.getType().equalsIgnoreCase("C")) {
                        totalCreditTreasury+=trans.getAmount();
                    } else {
                        totalDebitTreasury-=trans.getAmount();
                    }
                } else {
                    if (trans.getType().equalsIgnoreCase("C")) {
                        totalCredit+=trans.getAmount();
                    } else {
                        totalDebit-=trans.getAmount();
                    }
                }
            }
        }
        
        this.total.setTotalCredit(totalCredit);
        this.total.setTotalDebit(totalDebit);
        this.total.setTotalCreditTreasury(totalCreditTreasury);
        this.total.setTotalDebitTreasury(totalDebitTreasury);
        
        return this.total;
	}
	
}
