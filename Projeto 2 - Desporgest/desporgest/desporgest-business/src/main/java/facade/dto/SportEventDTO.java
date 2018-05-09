package facade.dto;

import java.io.Serializable;

public class SportEventDTO implements Serializable {

	private static final long serialVersionUID = -4087131153704256744L;
	private String designation;
	private long id;

	public SportEventDTO() {
		
	}
	public SportEventDTO(String designation, long id) {
		this.designation = designation;
		this.id = id;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public long getId() {
		return id;
	}
	
}
