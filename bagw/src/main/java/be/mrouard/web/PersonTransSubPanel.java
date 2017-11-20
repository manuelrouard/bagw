package be.mrouard.web;

import java.util.List;

import be.mrouard.model.entity.Trans;

public class PersonTransSubPanel extends TransSubPanel {

	private static final long serialVersionUID = 1L;

	public PersonTransSubPanel(String id, long personId) {
		super(id, personId);
	}

	@Override
	public List<Trans> populateList() {
		return bag.getTransactionsByPersonId(itemId);
	}
}
