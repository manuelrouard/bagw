package be.mrouard.web;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;

import be.mrouard.model.entity.Trans;

public abstract class TransSubPanel extends AbstractTablePanel<Trans> {

	private static final long serialVersionUID = 1L;
	
	public TransSubPanel (String id, long personId) {
		super(id, personId);
	}

	@Override
	protected void addTableHeaders(WebMarkupContainer wmc) {
		// wmc.add(new Label("common.name", new StringResourceModel("common.name", this, null)));
	}

	@Override
	protected void addTableItems(ListItem<Trans> item) {
		Trans t=(Trans) item.getModelObject();
        item.add(new Label("type", t.getType()));
        item.add(new Label("amount", t.getAmount()));
        item.add(new Label("date", t.getDate()));
        String personLastName="";
        if (t.getPerson()!=null) personLastName=t.getPerson().getNameLast();
        item.add(new Label("person.nameLast", personLastName));
        String billName="";
        if (t.getBill()!=null) billName=t.getBill().getName();   
        item.add(new Label("bill.name", billName));
        item.add(new Label("reference", t.getReference()));
    }

}
