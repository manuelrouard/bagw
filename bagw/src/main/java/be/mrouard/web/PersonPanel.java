package be.mrouard.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import be.mrouard.BagConstants;
import be.mrouard.WicketApplication;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Person;
import be.mrouard.model.entity.Trans;
import be.mrouard.util.Email;
import be.mrouard.web.model.PersonDataProvider;

public class PersonPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private final Bag bag;
	private final Form<?> form;
	private final PersonDataView view;
	private final TextArea<String> comment;

	// TODO: Use AbstractDataView
	public PersonPanel(String id) {
		super(id);

        add(form = new Form<Void>("form"));
        
        bag=((WicketApplication)getApplication()).getBag();
		form.add(view=new PersonDataView("item", new PersonDataProvider(bag), form));	
		
		add(view.getCreateItem());
		
		final AjaxButton sendButton = new AjaxButton("sendEmail"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				String c=comment.getModelObject();
				List<Person> persons=bag.getPersons();
				for (Person p:persons) {
					if (p.isSend() && p.getEmail()!=null) {
						System.out.println("Send email to "+p.getNameLast()+" "+p.getNameFirst());
						String htmlBody=getEmailContent(p, c);
						//Email.sendEmail(BagConstants.mailFrom, "manuel.rouard72@gmail.com", "", BagConstants.mailSubject, htmlBody, null);
						Email.sendEmail(BagConstants.mailFrom, p.getEmail(), "", BagConstants.mailSubject, htmlBody, null);	
					}
				}
			}
		};
		form.add(sendButton);
		form.add(comment=new TextArea<String>("comment", Model.of("")));
	}
	
	private String getEmailContent(Person p, String comment) {
    	float sum=0F, sumDebit=0F, sumCredit=0F;
    	SimpleDateFormat sdf=new SimpleDateFormat(BagConstants.dateFormat);
    	DecimalFormat df=new DecimalFormat("####.00");

		StringBuffer htmlBody=new StringBuffer();
		htmlBody.append("Salut,");
		htmlBody.append("<br><br>");
		htmlBody.append(comment);
		htmlBody.append("<br><br>");
		htmlBody.append("Voici le détail de ton compte:");
		htmlBody.append("<br>");
		htmlBody.append("<table border=\"1\">");
		htmlBody.append("<tr>");
		htmlBody.append("<td>Date</td>");
		htmlBody.append("<td>Note</td>");
		htmlBody.append("<td>Echéance</td>");
		htmlBody.append("<td>Type</td>");
		htmlBody.append("<td>Montant<br/>débité</td>");
		htmlBody.append("<td>Paiement</td>");
		htmlBody.append("<td>Commentaire</td>");
		htmlBody.append("</tr>");
		sum=0F; sumDebit=0F; sumCredit=0F;
		for (Trans trans:bag.getTransactionsByPersonId(p.getId())) {
			if (trans.getType().equalsIgnoreCase("C")) {
			     sum+=trans.getAmount();
			     sumCredit+=trans.getAmount();
			} else {
				sum-=trans.getAmount();
			     sumDebit-=trans.getAmount();
			}
			htmlBody.append("<tr>");
			htmlBody.append("<td>"+((trans.getDate()!=null)?sdf.format(trans.getDate()):"")+"</td>");
			htmlBody.append("<td>"+trans.getBill().getName()+"</td>");
			htmlBody.append("<td>"+((trans.getBill().getDueDate()!=null)?sdf.format(trans.getBill().getDueDate()):"")+"</td>");
			htmlBody.append("<td>"+trans.getFullType()+"</td>");
			if (!trans.getType().equalsIgnoreCase("C")) {
				htmlBody.append("<td>"+ df.format(trans.getAmount()*-1)+"</td>");
			} else {
				htmlBody.append("<td></td>");
			}
			if (trans.getType().equalsIgnoreCase("C")) {
				htmlBody.append("<td>"+ df.format(trans.getAmount())+"</td>");
			} else {
				htmlBody.append("<td></td>");
			}
			htmlBody.append("<td>"+trans.getReference()+"</td>");
			htmlBody.append("</tr>");
		}
		htmlBody.append("<td>Total</td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td>"+df.format(sumDebit)+"</td>");
		htmlBody.append("<td>"+df.format(sumCredit)+"</td>");
		htmlBody.append("<td></td>");
		htmlBody.append("</tr><tr>");
		htmlBody.append("<td>Différence</td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td></td>");
		htmlBody.append("<td colspan=\"2\">"+df.format(sum)+"</td>");
		htmlBody.append("<td></td>");
		htmlBody.append("</tr>");
		htmlBody.append("</table>");
		htmlBody.append("<br>");
		if (sum<0F) 
			 htmlBody.append("Total à payer sur le compte BE13 2500 2060 7939: "+ df.format(sum*-1)+" euros");
		else htmlBody.append("Ton solde est actuellement positif: "+ df.format(sum)+" euros");
		htmlBody.append("<br><br>");
		htmlBody.append("Tous les détails de la dernière note sont disponibles sur ce site: ");
		htmlBody.append("<a href=\"https://docs.google.com/folder/d/0B5AN6JBehGcmNTA5N2Q4ZDktNzhjNy00YjE1LWFiNGYtYzY0NmE1OWUzYjcw/edit\">https://docs.google.com/folder/d/0B5AN6JBehGcmNTA5N2Q4ZDktNzhjNy00YjE1LWFiNGYtYzY0NmE1OWUzYjcw/edit</a>");
		htmlBody.append("<br>");
		htmlBody.append("Si le lien ne fonctionne pas, dis-le moi et je t'enverrai le document.");
		htmlBody.append("<br><br>");
		htmlBody.append("A+,");
		htmlBody.append("<br>");
		htmlBody.append("Manuel");
		return htmlBody.toString();
	}
}
