package be.mrouard.web.model;

import org.apache.wicket.model.LoadableDetachableModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Trans;

public class TransactionDetachableModel extends LoadableDetachableModel<Trans> {
	
	private static final long serialVersionUID = 1L;

	private long id;
	private Bag bag;
	
	public TransactionDetachableModel(Bag bag, Trans t) {
		this(t.getId());
		this.bag=bag;
	}

	public TransactionDetachableModel(long id) {
        if (id==0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
	}

	@Override
	protected Trans load() {
		try {
			return bag.getTransaction(id);
		} catch (Exception e) {
			return new Trans();
		}
	}

}
