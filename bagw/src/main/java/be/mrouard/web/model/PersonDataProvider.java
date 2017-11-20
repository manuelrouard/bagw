package be.mrouard.web.model;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;

public class PersonDataProvider implements IDataProvider<Person> {

	private static final long serialVersionUID = 1L;
	
	private Bag bag;
	
	public PersonDataProvider(Bag bag) {
		this.bag=bag;
	}

	@Override
	public void detach() {
	}

	@Override
	public Iterator<? extends Person> iterator(long first, long count) {
		return bag.getPersons().iterator();
	}

	@Override
	public IModel<Person> model(Person o) {
		return new PersonDetachableModel(bag, o);
	}

	@Override
	public long size() {
		return bag.getPersons().size();
	}
}
