package be.mrouard.web.model;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Bill;
import be.mrouard.model.entity.Entity;

public class BillDetachableModel extends AbstractEntityDetachableModel<Bill> {

	private static final long serialVersionUID = 1L;

	public BillDetachableModel(Bag bag, Entity e) {
		super(bag, e);
	}

	@Override
	protected Bill load() {
		try {
			return bag.getBill(id);
		} catch (Exception e) {
			return new Bill();
		}
	}

}
