package be.mrouard.web.model;

import java.util.Iterator;

import org.apache.wicket.model.IModel;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Bill;

public class BillDataProvider extends AbstractEntityDataProvider<Bill> {

	private static final long serialVersionUID = 1L;

	public BillDataProvider(Bag bag) {
		super(bag);
	}

	@Override
	public Iterator<Bill> iterator(long first, long count) {
		return bag.getBills().iterator();
	}

	@Override
	public IModel<Bill> model(Bill o) {
		return new BillDetachableModel(bag, o);
	}

	@Override
	public long size() {
		return bag.getBills().size();
	}

}


