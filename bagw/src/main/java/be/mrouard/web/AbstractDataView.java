package be.mrouard.web;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.PropertyModel;

public abstract class AbstractDataView<T> extends DataView<T> {

	private static final long serialVersionUID = 1L;
	
	protected final Form<?> form;

	protected AbstractDataView(String id, IDataProvider<T> dataProvider, Form<?> form) {
		super(id, dataProvider);
        this.form=form;
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
			System.out.println("Transaction update saved");
			for (Component p:getTargetsToRefresh()) target.add(p);
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

	public AjaxLink<Void> getCreateItem() {
		AjaxLink<Void> createLink=new AjaxLink<Void>("create") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				createItem();
				for (Component p:getTargetsToRefresh()) target.add(p);
				target.add(form);
			}
		};
		return createLink;
	}
	
	protected AjaxLink<Void> getDeleteItem(final long id) {
		AjaxLink<Void> deleteLink=new AjaxLink<Void>("delete") {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void onClick(AjaxRequestTarget target) {
				try {
					deleteItem(id);
				} catch (Exception e) {
					System.out.println("Error deleting item");
				}
				for (Component p:getTargetsToRefresh()) target.add(p);
				target.add(form);
			}
	
			@Override
	        protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes); 
	             
	            attributes.getAjaxCallListeners().add(new AjaxCallListener() {
	            	private static final long serialVersionUID = 1L;
	
					@Override
	                public CharSequence getPrecondition(Component component) {
						return "if (!confirm('Do you really want to delete this item ?')) return false;" + super.getPrecondition(component);
	                }
	            });
	        }
		};
		return deleteLink;
	}
	
	protected abstract void updateItem(T o);

	protected abstract void deleteItem(Long id);
	
	protected abstract void createItem();
	
	protected abstract List<Component> getTargetsToRefresh();
}
