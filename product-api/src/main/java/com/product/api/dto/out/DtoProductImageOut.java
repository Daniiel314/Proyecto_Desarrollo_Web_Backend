package com.product.api.dto.out;

public class DtoProductImageOut {

	private Integer product_image_id;
    private Integer product_id;
    private String image; 
    
    // getters & setters
	public Integer getProduct_image_id() {
		return product_image_id;
	}
	public void setProduct_image_id(Integer product_image_id) {
		this.product_image_id = product_image_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    
    
}
