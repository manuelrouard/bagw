package be.mrouard.web;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.web.model.BillDataProvider;

public class BillPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private final Form<?> form;
	private final BillDataView view;

	public BillPanel(String id) {
		super(id);

        add(form = new Form<Void>("form"));
        
		final Bag bag=((WicketApplication)getApplication()).getBag();
		form.add(view=new BillDataView("item", new BillDataProvider(bag), form));	
		
		add(view.getCreateItem());
	}
}
