package be.mrouard.web;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.PropertyModel;

import be.mrouard.HomePage;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bill;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;
import be.mrouard.web.model.TransactionDataProvider;

public class TransPanel extends AbstractEditableTablePanel<Trans> {

	private static final long serialVersionUID = 1L;

	private final DataView<Trans> view;

	// TODO: Use AbstractDataView
	public TransPanel(String id) {
		super(id);

		view=new DataView<Trans>("item", new TransactionDataProvider(bag)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Trans> item) {
				final Trans o = item.getModelObject();
				item.add(new Label("id", o.getId()));
				
				item.add(addAjaxEditableLabel(item, o, "type"));
				item.add(addAjaxEditableFloatLabel(item, o, "amount"));
				item.add(addAjaxEditableDateLabel(item, o, "date"));

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
		            }
		        });
		        billDDC.setOutputMarkupId(true);
		        item.add(billDDC);
		        
		        item.add(addAjaxEditableFloatLabel(item, o, "bAmount"));
		        item.add(addAjaxEditableFloatLabel(item, o, "vAmount"));
		        item.add(addAjaxEditableFloatLabel(item, o, "gAmount"));
		        item.add(addAjaxEditableFloatLabel(item, o, "cAmount"));
		        
				AjaxEditableLabel<String> reference = new AjaxEditableLabel<String>("reference", new PropertyModel<String>(o, "reference")) {
					private static final long serialVersionUID = 1L;
					@Override
					protected void onSubmit(AjaxRequestTarget target) {
						ajaxSave(item, target);
						super.onSubmit(target);
					}

				};
				item.add(reference);				
				
				item.add(new AjaxLink<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							long id=((Trans)item.getModelObject()).getId();
							System.out.println("Delete Trans "+id);
							bag.deleteTransaction(id);
							((WicketApplication)getApplication()).saveFile(bag);
						} catch (Exception e) {
							System.out.println("Error deleting Trans");
						}
						setResponsePage(HomePage.class);
					}

					 @Override
	                 protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
	                     super.updateAjaxAttributes(attributes); 
	                     
	                     attributes.getAjaxCallListeners().add(new AjaxCallListener() {
							private static final long serialVersionUID = 1L;

							@Override
	                         public CharSequence getPrecondition(Component component) {
	                             return "if (!confirm('Do you really want to delete this item ?')) return false;" + super.getPrecondition(component);
	                         }
	                     });
	                 }
				});				
			}
			
		};
		form.add(view);	
		
        // because we are in a form we need to preserve state of the component
        // hierarchy (because it might contain things like form errors that
        // would be lost if the hierarchy for each item was recreated every
        // request by default), so we use an item reuse strategy.
		view.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());		
	}
	
	@Override
	protected void updateItem(Trans o) {
		bag.updateTrans(o);
	}

	@Override
	protected void createItem() {
		System.out.println("Create Transaction");
		bag.createTrans();
	}		

}
