package be.mrouard.web;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.PropertyModel;

import be.mrouard.HomePage;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Bill;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;

public class TransDataView extends AbstractDataView<Trans>{

	private static final long serialVersionUID = 1L;

	private final Bag bag;
	
	protected TransDataView(String id, IDataProvider<Trans> dataProvider, Form<?>  form) {
		super(id, dataProvider, form);
		
		bag=((WicketApplication)getApplication()).getBag();
        // because we are in a form we need to preserve state of the component
        // hierarchy (because it might contain things like form errors that
        // would be lost if the hierarchy for each item was recreated every
        // request by default), so we use an item reuse strategy.
		setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
	}

	@Override
	protected void populateItem(final Item<Trans> item) {
		final Trans o = item.getModelObject();
		item.add(new Label("id", o.getId()));
		
		item.add(addAjaxEditableLabel(item, o, "type"));
		item.add(addAjaxEditableFloatLabel(item, o, "amount"));
		item.add(addAjaxEditableDateLabel(item, o, "date"));
		item.add(addAjaxEditableDateLabel(item, o, "reference"));

		PropertyModel<Person> ppm=new PropertyModel<Person>(o, "person");
        final DropDownChoice<Person> personDDC =  new DropDownChoice<Person>(
        		"person", 
        		ppm,
                bag.getPersons(),
                new ChoiceRenderer<Person>("nameLast", "id"));

        personDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			@Override
            protected void onUpdate(AjaxRequestTarget target)
            {
				System.out.println("Person dropdown - Updated");
    			((WicketApplication)getApplication()).saveFile(bag);
    			//for (Component p:getTargetsToRefresh()) target.add(p);
            }
        });
        personDDC.setOutputMarkupId(true);
        item.add(personDDC);
		
		PropertyModel<Bill> bpm=new PropertyModel<Bill>(o, "bill");
        final DropDownChoice<Bill> billDDC =  new DropDownChoice<Bill>(
        		"bill", 
        		bpm,
                bag.getBills(),
                new ChoiceRenderer<Bill>("name", "id"));

        billDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;
			@Override
            protected void onUpdate(AjaxRequestTarget target)
            {
				System.out.println("Bill dropdown - Updated");
    			((WicketApplication)getApplication()).saveFile(bag);
    			//for (Component p:getTargetsToRefresh()) target.add(p);
            }
        });
        billDDC.setOutputMarkupId(true);
        item.add(billDDC);
		
		item.add(getDeleteItem(o.getId()));
	}


	@Override
	protected void createItem() {
		System.out.println("Create Trans");
		bag.createTrans();
	}
	
	@Override
	protected void updateItem(Trans o) {
		bag.updateTrans(o);	
		saveItem();
	}

	@Override
	protected void deleteItem(Long id) {
		System.out.println("Delete Trans "+id);
		bag.deleteTransaction(id);	
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
