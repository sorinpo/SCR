package sorinpo.scr.edu.dto;

import flexjson.JSONSerializer;

public class ActionResponse {

	public ActionResponse(boolean success) {
		this(success, null, null);
	}
	
	public ActionResponse(boolean success, String message) {
		this(success, message, null);
	}
	
	public ActionResponse(boolean success, Object root) {
		this(success, null, root);
	}
	
	public ActionResponse(boolean success, String message, Object root) {
		super();
		this.success = success;
		this.message = message;
		this.root = root;
	}

	private boolean success;
	private String message;
	private Object root;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public Object getRoot() {
		return root;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

}
