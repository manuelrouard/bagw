package be.mrouard.web;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.PropertyModel;

import be.mrouard.HomePage;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;

public class PersonDataView extends AbstractDataView<Person>{

	private static final long serialVersionUID = 1L;

	private final Bag bag;
	
	protected PersonDataView(String id, IDataProvider<Person> dataProvider, Form<?>  form) {
		super(id, dataProvider, form);
		
		bag=((WicketApplication)getApplication()).getBag();
        // because we are in a form we need to preserve state of the component
        // hierarchy (because it might contain things like form errors that
        // would be lost if the hierarchy for each item was recreated every
        // request by default), so we use an item reuse strategy.
		setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
	}

	@Override
	protected void populateItem(final Item<Person> item) {
		final Person o = item.getModelObject();
		item.add(new Label("id", o.getId()));
		
		item.add(addAjaxEditableLabel(item, o, "nameLast"));
		item.add(addAjaxEditableLabel(item, o, "nameFirst"));
		item.add(addAjaxEditableLabel(item, o, "mobile"));
		item.add(addAjaxEditableLabel(item, o, "email"));
		item.add(addAjaxEditableLabel(item, o, "account"));
		item.add(addAjaxEditableLabel(item, o, "reminder"));
		item.add(new Label("balance", o.getBalance()));
		
		item.add(new AjaxCheckBox("cbSend", new PropertyModel<Boolean>(o, "send")) {
			private static final long serialVersionUID = 1L;
			@Override
		    protected void onUpdate(AjaxRequestTarget target) {
				// binding does the update
		    }
		});	
		
		item.add(getDeleteItem(o.getId()));			
	}


	@Override
	protected void createItem() {
		System.out.println("Create Person");
		bag.createPerson();
	}
	
	@Override
	protected void updateItem(Person o) {
		bag.updatePerson(o);	
		saveItem();
	}

	@Override
	protected void deleteItem(Long id) {
		System.out.println("Delete Person "+id);
		bag.deletePerson(id);	
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
