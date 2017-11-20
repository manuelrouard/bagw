package be.mrouard.web.model;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import be.mrouard.model.entity.Bag;

public abstract class AbstractEntityDataProvider<T> implements IDataProvider<T>{

	private static final long serialVersionUID = 1L;
	
	protected Bag bag;
	
	public AbstractEntityDataProvider(Bag bag) {
		this.bag=bag;
	}	
	
	@Override
	public void detach() {
	}	
}
