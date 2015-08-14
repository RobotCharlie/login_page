package client_server_project.servlet;

import java.io.Serializable;

public class ResultJSONBean implements Serializable{

	private static final long serialVersionUID = -7960139244590130558L;
	
	private String result;
	private String errorMsg;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
