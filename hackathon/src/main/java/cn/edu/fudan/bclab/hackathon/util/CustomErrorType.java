package cn.edu.fudan.bclab.hackathon.util;

public class CustomErrorType {
	
	private int errorCode;
    private String errorMessage;
 
    public CustomErrorType(int errorCode, String errorMessage){
    	this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
 
}
