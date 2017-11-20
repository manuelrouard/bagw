package be.mrouard.web;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import be.mrouard.model.entity.Trans;

public class TransImportSubPanel extends AbstractTablePanel<Trans> {

	private static final long serialVersionUID = 1L;
	
	public TransImportSubPanel (String id, List<Trans> transes) {
		super(id, transes);
	}

	@Override
	public List<Trans> populateList() {
		return items;
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
        item.add(new Label("person.nameLast", t.getPerson().getNameLast()));
        item.add(new Label("bill.name", t.getBill().getName()));
        item.add(new Label("reference", t.getReference()));
	}

	public void populateList(List<Trans> transes) {
		items.addAll(transes);
	}
}
