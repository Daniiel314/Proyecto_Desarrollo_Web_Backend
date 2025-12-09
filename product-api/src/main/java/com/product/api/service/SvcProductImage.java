package com.product.api.service;

import java.util.List;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;

public interface SvcProductImage {
	
	public ApiResponse upload(Integer productId, DtoProductImageIn in);
    public List<DtoProductImageOut> getImages(Integer productId);
    public ApiResponse deleteImage(Integer productId, Integer productImageId);
}
