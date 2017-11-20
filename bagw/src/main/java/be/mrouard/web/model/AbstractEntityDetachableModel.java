package be.mrouard.web.model;

import org.apache.wicket.model.LoadableDetachableModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Entity;

public abstract class AbstractEntityDetachableModel<T> extends LoadableDetachableModel<T>{
	private static final long serialVersionUID = 1L;

	protected long id;
	protected Bag bag;
	
	public AbstractEntityDetachableModel(Bag bag, Entity e) {
		this(e.getId());
		this.bag=bag;
	}

	public AbstractEntityDetachableModel(long id) {
        if (id==0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
	}

}
