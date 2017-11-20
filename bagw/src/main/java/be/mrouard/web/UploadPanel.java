package be.mrouard.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import be.mrouard.HomePage;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Bill;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;

public class UploadPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private static Bag bag;
	FileUploadField fileUploadField;
	List<Trans> transes;
	TransImportSubPanel transImportSubPanel;
	final DropDownChoice<Bill> billDDC;
	
	public UploadPanel(String id) {
		super(id);
		bag = ((WicketApplication)getApplication()).getBag();
		transes=new ArrayList<Trans>();

		final Form<Void> csvUploadForm = new Form<Void>("csvUploadForm");
		csvUploadForm.setMultiPart(true);
		csvUploadForm.add(fileUploadField = new FileUploadField("fileInput"));

		final AjaxButton ajaxXlsUploadButton = new AjaxButton("xlsUploadButton"){
			private static final long serialVersionUID = -1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				super.onSubmit(target, form);
				if (fileUploadField.getFileUploads()!=null){
					try{
						FileUpload fu = fileUploadField.getFileUploads().get(0);
						transes=readXlsCsvFile(fu, billDDC.getModelObject());
						System.out.println("Number of transactions found: "+transes.size());
						transImportSubPanel.populateList(transes);
						target.add(form);
					}catch(Exception e){
						System.out.println("Error uploading file");
					}
				}
			}
		};
		csvUploadForm.add(ajaxXlsUploadButton);
		
		final AjaxButton ajaxBankUploadButton = new AjaxButton("bankUploadButton"){
			private static final long serialVersionUID = -1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				super.onSubmit(target, form);
				if (fileUploadField.getFileUploads()!=null){
					try{
						FileUpload fu = fileUploadField.getFileUploads().get(0);
						transes=readBankCsvFile(fu, billDDC.getModelObject());
						System.out.println("Number of transactions found: "+transes.size());
						transImportSubPanel.populateList(transes);
						target.add(form);
					}catch(Exception e){
						System.out.println("Error uploading file");
					}
				}
			}
		};
		csvUploadForm.add(ajaxBankUploadButton);		

		transImportSubPanel=new TransImportSubPanel("xlsTransactions", transes);
		csvUploadForm.add(transImportSubPanel);
		
		Model<Bill> bm=new Model<Bill>(new Bill());
        billDDC =  new DropDownChoice<Bill>(
        		"bill", 
        		bm,
                bag.getBills(),
                new ChoiceRenderer<Bill>("name", "id"));
        csvUploadForm.add(billDDC);
        
        Button uploadSaveButton = new Button("uploadSaveButton") {
			private static final long serialVersionUID = 1L;
			@Override
            public void onSubmit() {
				bag.addTransactions(transes);
				((WicketApplication)getApplication()).saveFile(bag);
				System.out.println("transactions imported: "+transes.size());
				setResponsePage(HomePage.class);
            }
        };
        csvUploadForm.add(uploadSaveButton);
		
		add(csvUploadForm);
	}

	private static List<Trans> readXlsCsvFile(FileUpload csvFile, Bill bill) {
		ArrayList<Trans> BankTransList=new ArrayList<Trans>();

		try {
			//create BufferedReader to read csv file
			BufferedReader br=new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
			String strLine = "";
			StringTokenizer st = null;

			// read header
			int i=0;
			while( (i++<5 && (strLine=br.readLine())!=null)) {
				//break comma separated line using ","
				st = new StringTokenizer(strLine, ";");
				//for (int j=0; j<8; j++) st.nextToken();
			}

			//read comma separated file line by line
			i=0;
			while((i++<100) && ((strLine=br.readLine())!=null)) {
				//break comma separated line using ","
				st = new StringTokenizer(strLine, ";");
				String name="";
				float amount=0F;
		    	if (st.hasMoreTokens()) name=getCleanToken(st);
		    	if (name.toLowerCase().startsWith("total")) break;
		    	if (st.hasMoreTokens()) amount=getFloat(getCleanToken(st));
		    	if (amount>0F) {
		    		try {
				    	Person person=bag.getPersonByName(name);
				    	if (person!=null) {
							Trans trans=new Trans();
							trans.setId(bag.getNextId());
				    		trans.setPerson(person);
					    	trans.setBill(bill);
					    	trans.setAmount(amount);
					    	trans.setType("D");
					    	trans.setDate(new Date());
					    	trans.setReference(name);
					    	BankTransList.add(trans);
				    	}
		    		} catch (Exception e) {
		    			System.out.println("Error finding the person: " + name);
		    		}
		    	}
			}
			// Add treasury transactions
			while((i++<100) && ((strLine=br.readLine())!=null)) {
				//break comma separated line using ","
				st = new StringTokenizer(strLine, ",");
				String name="";
				float amount=0F;
		    	if (st.hasMoreTokens()) name=getCleanToken(st);
		    	if (name.toLowerCase().startsWith("end")) break;			
		    	if (st.hasMoreTokens()) amount=getFloat(getCleanToken(st));
		    	if (amount>0F) {
		    		try {
				    	Person person=bag.getPerson(555703);
				    	if (person!=null) {
							Trans trans=new Trans();
							trans.setId(bag.getNextId());
				    		trans.setPerson(person);
					    	trans.setBill(bill);
					    	trans.setAmount(amount);
					    	trans.setType("C");
					    	trans.setDate(new Date());
					    	trans.setReference(name);
					    	BankTransList.add(trans);
				    	}
		    		} catch (Exception e) {
		    			System.out.println("Error finding the person: " + name);
		    		}
		    	}
			}

		} catch(Exception e){
			System.out.println("Exception while reading csv file: " + e);
			e.printStackTrace();
		}
		return BankTransList;
	}
	
	private static List<Trans> readBankCsvFile(FileUpload csvFile, Bill bill) {
		ArrayList<Trans> BankTransList=new ArrayList<Trans>();

		try {
			//create BufferedReader to read csv file
			BufferedReader br = null;
			br=new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
			String strLine = "";
			StringTokenizer st = null;

			// read header
			if ((strLine = br.readLine())!=null) {
				
				//break comma separated line using ";"
				st = new StringTokenizer(strLine, ";");
				for (int i=0; i<8; i++) st.nextToken();
			}

			//read comma separated file line by line
			while( (strLine = br.readLine()) != null) {
		
				//break comma separated line using ";"
				st = new StringTokenizer(strLine, ";");
				String ref="-1", account="", description="";
				Date transDate=new Date();
				Float amount=0F;
		    	if (st.hasMoreTokens()) ref=getCleanToken(st);
		    	if (st.hasMoreTokens()) transDate=getDate(getCleanToken(st));
		    	if (st.hasMoreTokens()) st.nextToken(); // value date
		    	if (st.hasMoreTokens()) amount=getFloat(getCleanToken(st));
		    	if (st.hasMoreTokens()) st.nextToken(); // currency
		    	if (st.hasMoreTokens()) account=st.nextToken(); // account
		    	if (st.hasMoreTokens()) description=st.nextToken(); // description
		    	if (st.hasMoreTokens()) st.nextToken(); // my account number
		    	if (amount>0F) {
		    		try {
				    	Person person=bag.getPersonByAccount(account, description);
				    	if (person!=null) {
							Trans trans=new Trans();
							trans.setId(bag.getNextId());
							trans.setPerson(person);
					    	trans.setBill(bill);
					    	trans.setAmount(amount);
					    	trans.setType("C");
					    	trans.setDate(transDate);
					    	trans.setReference(ref);
					    	BankTransList.add(trans);
				    	}
		    		} catch (Exception e) {
		    			System.out.println("Error finding the person: " + account);
		    		}
		    	}
			}
		} catch(Exception e){
			System.out.println("Exception while reading csv file: " + e);
			e.printStackTrace();
		}
		return BankTransList;
	}
	
	private static String getCleanToken(StringTokenizer st) {
		return st.nextToken().replaceAll("\"", "");
	}
	
	private static Float getFloat(String value){
		if (value!=null && !value.trim().equals("")) {
			try {
				return (new Float(value.replace(",", ".")));
			} catch (Exception e){
				System.out.println("Error converting float "+e.getMessage());
				return null;
			}
		} else {
			return null;
		}
	}	
	
	private static Date getDate(String value){
		if (value!=null && !value.trim().equals("") && !value.trim().equals("null")) {
			try {
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			    //System.out.println(value+" => "+dateFormat.parse(value));
			    return dateFormat.parse(value); 
			} catch (Exception e){
				return null;
			}
		} else {
			return null;
		}
	}	
}

