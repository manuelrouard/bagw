package be.mrouard.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;

import be.mrouard.model.entity.Bill;
import be.mrouard.web.model.AbstractAjaxTablePanel;

public class BillSummaryPanel extends AbstractAjaxTablePanel<Bill> {

	private static final long serialVersionUID = 1L;
	
	public BillSummaryPanel(String id) {
		super(id);
	}

	@Override
	public List<Bill> populateList() {
		return bag.getBills();
	}

	@Override
	protected void addTableHeaders(WebMarkupContainer wmc) {
		// wmc.add(new Label("common.name", new StringResourceModel("common.name", this, null)));
	}

	@Override
	protected void addTableItems(ListItem<Bill> item) {
		Bill b=((Bill) item.getModelObject());
        item.add(new Label("name", b.getName()));
        item.add(new Label("dueDate", b.getDueDate()));
        item.add(new Label("totalCredit", b.getTotalCredit()));
        item.add(new Label("totalDebit", b.getTotalDebit()));
        item.add(new Label("totalCreditTreasury", b.getTotalCreditTreasury()));
        item.add(new Label("totalDebitTreasury", b.getTotalDebitTreasury()));
        item.add(new BillTransSubPanel("billTransactions", b.getId()));

        Label transDetails=new Label("transDetails", "[Details]");
		final String onClickScript="toggle('billTransactions_"+b.getId()+"')";
		
		AttributeModifier onClick = new AttributeModifier("onclick", 
		        new AbstractReadOnlyModel<String>(){
					private static final long serialVersionUID = 1L;
					public String getObject(){
			            return onClickScript;
			          }
		        });
		transDetails.add(onClick);
		item.add(transDetails);
	}

}
