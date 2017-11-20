package be.mrouard.web;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import be.mrouard.HomePage;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Bill;

public class BillDataView extends AbstractDataView<Bill>{

	private static final long serialVersionUID = 1L;

	private final Bag bag;
	
	protected BillDataView(String id, IDataProvider<Bill> dataProvider, Form<?>  form) {
		super(id, dataProvider, form);
		
		bag=((WicketApplication)getApplication()).getBag();
        // because we are in a form we need to preserve state of the component
        // hierarchy (because it might contain things like form errors that
        // would be lost if the hierarchy for each item was recreated every
        // request by default), so we use an item reuse strategy.
		setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
	}

	@Override
	protected void populateItem(Item<Bill> item) {
		final Bill o = item.getModelObject();
		item.add(new Label("id", o.getId()));
		
		item.add(addAjaxEditableLabel(item, o, "name"));
		item.add(addAjaxEditableDateLabel(item, o, "dueDate"));					
		item.add(getDeleteItem(o.getId()));
	}

	@Override
	protected void createItem() {
		System.out.println("Create Bill");
		bag.createBill();
	}
	
	@Override
	protected void updateItem(Bill o) {
		bag.updateBill(o);	
		saveItem();
	}

	@Override
	protected void deleteItem(Long id) {
		System.out.println("Delete Bill "+id);
		bag.deleteBill(id);	
		saveItem();
	}

	private void saveItem() {
		((WicketApplication)getApplication()).saveFile(bag);
	}

	@Override
	protected List<Component> getTargetsToRefresh() {
		return HomePage.getComponentsToRefresh();
	}

}
