package be.mrouard.web;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.AbstractReadOnlyModel;

import be.mrouard.model.entity.Person;

public class PersonSummaryPanel extends AbstractTablePanel<Person> {

	private static final long serialVersionUID = 1L;
	
	public PersonSummaryPanel(String id) {
		super(id);
	}

	@Override
	public List<Person> populateList() {
		return bag.getPersons();
	}

	@Override
	protected void addTableHeaders(WebMarkupContainer wmc) {
		// wmc.add(new Label("common.name", new StringResourceModel("common.name", this, null)));
	}

	@Override
	protected void addTableItems(ListItem<Person> item) {
		Person p=(Person) item.getModelObject();
        item.add(new Label("nameLast", p.getNameLast()));
        item.add(new Label("nameFirst", p.getNameFirst()));
        item.add(new Label("balance", p.getBalance()));
        item.add(new Label("reminder", p.getReminder()));
        item.add(new Label("email", p.getEmail()));
        item.add(new PersonTransSubPanel("personTransactions", p.getId()));

        Label transDetails=new Label("transDetails", "[Details]");
		final String onClickScript="toggle('personTransactions_"+p.getId()+"')";
		
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
