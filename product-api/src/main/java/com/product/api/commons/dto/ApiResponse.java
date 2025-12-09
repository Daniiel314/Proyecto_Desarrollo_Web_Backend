package com.product.api.commons.dto;

public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.setMessage(message);
    }
    
    // Getter y Setter
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
