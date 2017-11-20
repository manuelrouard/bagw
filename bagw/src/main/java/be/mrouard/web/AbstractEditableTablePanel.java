package be.mrouard.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.PropertyModel;

import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;

public abstract class AbstractEditableTablePanel<T> extends Panel {

	private static final long serialVersionUID = 1L;
	
	protected static Bag bag;	
	protected final Form<?> form;
	protected List<T> items = Collections.synchronizedList(new ArrayList<T>());

	public AbstractEditableTablePanel(String id) {
		super(id);
		
		bag = ((WicketApplication)getApplication()).getBag();
		
        form = new Form<Void>("form");
        add(form);
        
		
		add(new AjaxLink<Void>("create") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target){
				createItem();
				target.add(form);
			}
		});		
		/*
		add(new AjaxLink<Void>("save") {
			private static final long serialVersionUID = 1L;
		
			@Override
			public void onClick(AjaxRequestTarget target){
				System.out.println("File saved");
				((WicketApplication)getApplication()).saveFile(bag);
			}
		});
		*/	
    }

	/**
	 * Methode commune de sauvegarde lors de l'edition des infos dans la table.
	 * 
	 * @param item
	 *            L'item a sauver.
	 * @param target
	 *            Le target ajax.
	 */
	protected void ajaxSave(final Item<T> item, AjaxRequestTarget target) {
		try {
			T o = (T) item.getModel().getObject();
			updateItem(o);
			((WicketApplication)getApplication()).saveFile(bag);
			System.out.println("Transaction update saved");
			target.add(form);
		} catch (Exception e) {
			System.out.println("Error saving transaction update");
			e.printStackTrace();
		}
	}	
	
	protected AjaxEditableLabel<String> addAjaxEditableLabel (final Item<T> item, T o, String name) {
		AjaxEditableLabel<String> itemAjaxEditableLabel = new AjaxEditableLabel<String>(name, new PropertyModel<String>(o, name)) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				ajaxSave(item, target);
				super.onSubmit(target);
			}
		};
		return itemAjaxEditableLabel;
	}

	protected AjaxEditableLabel<Float> addAjaxEditableFloatLabel (final Item<T> item, T o, String name) {
		AjaxEditableLabel<Float> itemAjaxEditableLabel = new AjaxEditableLabel<Float>(name, new PropertyModel<Float>(o, name)) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				ajaxSave(item, target);
				super.onSubmit(target);
			}
	
		};
		return itemAjaxEditableLabel;
	};
	
	protected AjaxEditableLabel<Date> addAjaxEditableDateLabel (final Item<T> item, T o, String name) {
		AjaxEditableLabel<Date> itemAjaxEditableLabel = new AjaxEditableLabel<Date>(name, new PropertyModel<Date>(o, name)) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				ajaxSave(item, target);
				super.onSubmit(target);
			}
	
		};
		return itemAjaxEditableLabel;
	};
	
	protected abstract void updateItem(T o);

	protected abstract void createItem();
}
