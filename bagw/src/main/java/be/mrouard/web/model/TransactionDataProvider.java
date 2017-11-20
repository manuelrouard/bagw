package be.mrouard.web.model;

import java.util.Iterator;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Trans;

public class TransactionDataProvider implements IDataProvider<Trans> {

	private static final long serialVersionUID = 1L;
	
	private Bag bag;
	
	public TransactionDataProvider(Bag bag) {
		this.bag=bag;
	}

	@Override
	public void detach() {
	}

	@Override
	public Iterator<? extends Trans> iterator(long first, long count) {
		return bag.getTranses().iterator();
	}

	@Override
	public IModel<Trans> model(Trans o) {
		return new TransactionDetachableModel(bag, o);
	}

	@Override
	public long size() {
		return bag.getTranses().size();
	}
}
