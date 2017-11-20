package be.mrouard.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Trans;

public abstract class AbstractTablePanel<T> extends Panel {

	private static final long serialVersionUID = 1L;
	
	protected static Bag bag;	
	protected long itemId;
	
	protected List<T> items = Collections.synchronizedList(new ArrayList<T>());
	private final WebMarkupContainer wmc = new WebMarkupContainer("wmc"); 

	public AbstractTablePanel(String id) {
		this(id, -1L, null);
	}
	
	public AbstractTablePanel(String id, List<Trans> transes) {
		this(id, -1L, transes);
	}
	
	public AbstractTablePanel(String id, long itemId) {
		this(id, itemId, null);
	}	
		
	@SuppressWarnings("unchecked")
	public AbstractTablePanel(String id, long itemId, List<Trans> transes) {
		super(id);
		setOutputMarkupId(true);
		
		bag = ((WicketApplication)getApplication()).getBag();
		
		this.itemId=itemId;
		if (transes!=null) this.items=(List<T>) transes;
		if (itemId>-1) wmc.setMarkupId(id+"_"+itemId);
		wmc.setOutputMarkupId(true);
		
        IModel<List<T>> model =  new LoadableDetachableModel<List<T>>() {
			private static final long serialVersionUID = -1L;
			protected List<T> load() {
                return populateList();
            }
        };
		
        ListView<T> rv=new ListView<T>("items", model) {
			private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<T> item){
                addTableItems(item);
            }
		};
		addTableHeaders(wmc);
		wmc.add(rv);
		add(wmc);		

	}
	
	public int getItemSize() {
		return items.size();
	}

	public abstract List<T> populateList();
	
	protected abstract void addTableHeaders(WebMarkupContainer wmc);

	protected abstract void addTableItems(ListItem<T> item);
}
