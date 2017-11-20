package be.mrouard.model.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class c) {
		return c.equals(Date.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		DateTime dt = new DateTime(source);
		writer.setValue(dt.toString(formatter));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String val = reader.getValue();
		return formatter.parseDateTime(val).toDate();

	}
}
