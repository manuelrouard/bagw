package be.mrouard.model.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import be.mrouard.model.entity.Bag;
import be.mrouard.model.entity.Trans;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

public class BagService {

	private static String fileName="bag.xml";
	
	private FileOutputStream outputStream;
	private OutputStreamWriter writer;
	private XStream xStream;
	
	private Bag bag;
	
	public void saveFile(Bag bag) {
		try {
			outputStream = new FileOutputStream(fileName);
			writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
			xStream.toXML(bag, writer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Bag openFile() throws FileNotFoundException, StreamException, Exception {
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader reader = new InputStreamReader(fis, Charset.forName("UTF-8"));
        if (xStream==null) xStream=new XStream();
        xStream.processAnnotations(Bag.class);
		xStream.processAnnotations(Trans.class);
		xStream.registerConverter(new DateConverter());
		bag=(Bag) xStream.fromXML(reader);
		bag.init();
        return bag;
	}
	
	public Bag getBag() {
		return bag;
	}

}
