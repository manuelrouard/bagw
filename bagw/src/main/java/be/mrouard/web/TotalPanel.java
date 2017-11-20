package be.mrouard.web;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import be.mrouard.BagConstants;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;

public class TotalPanel extends Panel {

    private static final long serialVersionUID = 1L;
    
    private Bag bag;
    private DecimalFormat df=new DecimalFormat("####.00");

    public TotalPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        bag = ((WicketApplication)getApplication()).getBag();
        List<Trans> transactions=bag.getTranses();

        Person person=null;
        float totalCredit=0F, totalDebit=0F, totalCreditTreasury=0F, totalDebitTreasury=0F;
        Trans LastTreasuryTransaction=null;
        
        // For each bill, compute
        // Sum VGC+(paid to club-VGC)
        
        for (Trans trans:transactions) {
            person=trans.getPerson();
            if (person!=null) {
                if (person.getNameLast().equalsIgnoreCase(BagConstants.sTreasury)) {
                    if (trans.getType().equalsIgnoreCase("C")) {
                        totalCreditTreasury+=trans.getAmount();
                    } else {
                        totalDebitTreasury-=trans.getAmount();
                        if (isLast(LastTreasuryTransaction, trans)) LastTreasuryTransaction=trans;
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
        
        // Compute amount for accounting
        float total=0F;
        if (LastTreasuryTransaction!=null) {
        	total=LastTreasuryTransaction.getAmount()-(LastTreasuryTransaction.getvAmount()+(LastTreasuryTransaction.getgAmount()+LastTreasuryTransaction.getcAmount()));
        }
        
        // Build final label
        StringBuilder sb=new StringBuilder();
        sb.append(BagConstants.sBoissons); sb.append(": "); sb.append(total); sb.append(" - ");
        sb.append(BagConstants.sVolants); sb.append(": "); sb.append(LastTreasuryTransaction.getvAmount()); sb.append(" - ");
        sb.append(BagConstants.sGrips); sb.append(": "); sb.append(LastTreasuryTransaction.getgAmount()); sb.append(" - ");
        sb.append(BagConstants.sCordage); sb.append(": "); sb.append(LastTreasuryTransaction.getcAmount());
        
        add(new Label("totalCredit", df.format(totalCredit)));
        add(new Label("totalCreditTreasury", df.format(totalCreditTreasury)));
        add(new Label("totalCredit+totalDebit", df.format(totalCredit+totalDebit)));
        
        add(new Label("totalDebit", df.format(totalDebit)));
        add(new Label("totalDebitTreasury", df.format(totalDebitTreasury)));
        add(new Label("totalCreditTreasury+totalDebitTreasury", df.format(totalCreditTreasury+totalDebitTreasury)));
        
        add(new Label("totalDebit2", df.format(totalDebit)));
        add(new Label("totalCreditTreasury2", df.format(totalCreditTreasury)));
        add(new Label("totalCreditTreasury+totalDebit", df.format(totalCreditTreasury+totalDebit)));

        add(new Label("totalDebitTreasury2", df.format(totalDebitTreasury)));
        add(new Label("totalCredit2", df.format(totalCredit)));
        add(new Label("totalCredit+totalDebitTreasury", df.format(totalCredit+totalDebitTreasury)));
        
        add(new Label("totalCredit+totalDebitTreasury2", df.format(totalCredit+totalDebitTreasury)));
        add(new Label("accounting", sb));
    }

    private boolean isLast (Trans lastTreasuryTransaction, Trans trans) {
        if (lastTreasuryTransaction==null) {
        	// First transaction found
        	return true;
        } else if (trans.getDate().after(lastTreasuryTransaction.getDate())) {
        	// More recent transaction
        	return true;
        }
        return false;
    }
    
}

