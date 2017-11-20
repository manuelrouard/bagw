package be.mrouard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import be.mrouard.web.BillSummaryPanel;
import be.mrouard.web.BillPanel;
import be.mrouard.web.PersonPanel;
import be.mrouard.web.PersonSummaryPanel;
import be.mrouard.web.TotalPanel;
import be.mrouard.web.TransPanel;
import be.mrouard.web.UploadPanel;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private static TotalPanel totalPanel;
	private static PersonSummaryPanel personSummaryPanel;
	private static BillSummaryPanel billSummaryPanel;
	
	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		add(new TransPanel("transPanel"));
		add(new PersonPanel("personPanel"));
		add(new BillPanel("billPanel"));
		add(personSummaryPanel=new PersonSummaryPanel("personSummaryPanel"));
		add(billSummaryPanel=new BillSummaryPanel("billSummaryPanel"));
		add(new UploadPanel("uploadPanel"));
		add(totalPanel=new TotalPanel("totalPanel"));
	}
	
	// TODO: Add several panels
	public static List<Component> getComponentsToRefresh() {
		List<Component> components=new ArrayList<Component>();
		components.add(personSummaryPanel);
		components.add(billSummaryPanel);
		components.add(totalPanel);
		return components;
	}
}
