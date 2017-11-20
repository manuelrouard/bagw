package be.mrouard.web.model;

import org.apache.wicket.model.LoadableDetachableModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;

public class PersonDetachableModel extends LoadableDetachableModel<Person> {
	
	private static final long serialVersionUID = 1L;

	private long id;
	private Bag bag;
	
	public PersonDetachableModel(Bag bag, Person o) {
		this(o.getId());
		this.bag=bag;
	}

	public PersonDetachableModel(long id) {
        if (id==0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
	}

	@Override
	protected Person load() {
		try {
			return bag.getPerson(id);
		} catch (Exception e) {
			return new Person();
		}
	}

}
