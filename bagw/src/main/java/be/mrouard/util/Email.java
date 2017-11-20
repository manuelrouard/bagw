package be.mrouard.util;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;

import be.mrouard.BagConstants;

public class Email {
	public static void sendEmail(String sFrom, String sTo, String sCc, String sSubject, String sBody, String sAttachment) {
        System.out.println("Send email - start");
	    String sHtmlBody = "";
	    sHtmlBody += "<html><head><title>"+BagConstants.mailSubject+"</title></head>";
	    sHtmlBody += "<body bgcolor=\"white\">";
	    sHtmlBody += "<p>"+sBody+"</p>";    

	    try {
	        Properties oProperties = new Properties();
	        oProperties.put("mail.smtp.host", BagConstants.sSmtpServer);
	        Session oSession = Session.getDefaultInstance(oProperties, null);
	        Transport oTransport = oSession.getTransport("smtp");
	        
	        MimeMessage oMessage = new MimeMessage(oSession);
	        oMessage.setFrom(new InternetAddress(sFrom));
	        oMessage.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(sTo, false));
	        if (sCc != null) {
	            oMessage.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(sCc, false));
	        }
	      
	        String sEncoding = "ISO-8859-1";
	        //String sEncoding = "UTF-8";
	        oMessage.setSubject(sSubject, sEncoding);
	        MimeMultipart oContent = new MimeMultipart("alternative");
	        MimeBodyPart oTextBody = new MimeBodyPart();
	        MimeBodyPart oHtmlBody = new MimeBodyPart();
	        MimeBodyPart oFileBody = new MimeBodyPart();
	        oTextBody.setText(sBody,sEncoding);
	        oHtmlBody.setContent(sHtmlBody, "text/html; charset=\""+ sEncoding +"\"");
	        oContent.addBodyPart(oTextBody);
	        oContent.addBodyPart(oHtmlBody);
	        
	        if (sAttachment!=null) {
	        	FileDataSource ds=new FileDataSource(sAttachment);
	        	oFileBody.setDataHandler(new DataHandler(ds));
	            oFileBody.setFileName(sAttachment);
	            oContent.addBodyPart(oFileBody);
	        }
	        
	        oMessage.setContent(oContent);
	        System.out.println("from      ="+sFrom);
	        System.out.println("to        ="+sTo);
	        System.out.println("cc        ="+sCc);
	        System.out.println("attachment="+sAttachment);
	        System.out.println("subject   ="+sSubject);
	        System.out.println("body      ="+sBody);
	        System.out.println("htmlbody  ="+sHtmlBody);
	        Transport.send(oMessage);
	        oTransport.close();
	    }
	    catch (Exception e) {
			System.out.println("XCPT: Email - "+e.getMessage());
	    }
	}

}
