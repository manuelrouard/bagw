package be.mrouard.model.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("id")
	private long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
