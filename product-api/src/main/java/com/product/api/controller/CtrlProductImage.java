package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.service.SvcProductImage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "ProductImage", description = "Gestión de imágenes de productos")
@RestController
@RequestMapping("/product")
public class CtrlProductImage {

    @Autowired
    SvcProductImage svcProductImage;

    @Operation(summary = "Listar imágenes", 
    		description = "Devuelve las imágenes (Base64) de un producto.")
    @GetMapping("/{id}/image")
    public ResponseEntity<List<DtoProductImageOut>> getProductImages(@PathVariable("id") Integer productId) {
        List<DtoProductImageOut> images = svcProductImage.getImages(productId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @Operation(summary = "Subir imagen", 
    		description = "Registra una imagen para el producto.")
    @PostMapping("/{id}/image")
    public ResponseEntity<ApiResponse> createProductImage(
            @PathVariable("id") Integer productId,
            @Valid @RequestBody DtoProductImageIn in) {

        ApiResponse response = svcProductImage.upload(productId, in);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar imagen", 
    		description = "Elimina una imagen del producto.")
    @DeleteMapping("/{id}/image/{product-image-id}")
    public ResponseEntity<ApiResponse> deleteProductImage(
            @PathVariable("id") Integer productId,
            @PathVariable("product-image-id") Integer productImageId) {

        ApiResponse response = svcProductImage.deleteImage(productId, productImageId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
