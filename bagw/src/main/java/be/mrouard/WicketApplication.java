package be.mrouard;

import java.io.FileNotFoundException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.thoughtworks.xstream.io.StreamException;

import be.mrouard.model.entity.Bag;
import be.mrouard.model.service.BagService;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see be.mrouard.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	
	BagService bagService;
	WebPage homePage;
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		try {
			bagService=new BagService();
			bagService.openFile();
		} catch (FileNotFoundException e) {
			System.out.println("XML source file not found");
		} catch (StreamException e) {
			System.out.println("Error parsing XML source file");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error reading XML source file");
			e.printStackTrace();
		}		
	}
	
	public Bag getBag() {
		return bagService.getBag();
	}

	public void saveFile(Bag bag) {
		bagService.saveFile(bag);
	}
}
