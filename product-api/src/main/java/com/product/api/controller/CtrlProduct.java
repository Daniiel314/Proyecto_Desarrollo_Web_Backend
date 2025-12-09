package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.service.SvcProduct;
import com.product.api.commons.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Product", description = "Gestión de productos (CRUD)")
@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svc;

	@Operation(summary = "Listar productos", 
			description = "Devuelve una lista resumida de"
					+ " todos los productos en el sistema.")
	@GetMapping
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		return svc.getProducts();
	}

	@Operation(summary = "Obtener producto por ID", 
			description = "Devuelve los detalles completos de "
					+ "un producto específico, buscado por su ID.")
	@GetMapping("/{id}")
	public ResponseEntity<DtoProductOut> getProduct(@PathVariable Integer id) {
		return svc.getProduct(id);
	}

	@Operation(summary = "Crear producto", 
			description = "Registra un nuevo producto en el sistema.")
	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody DtoProductIn in) {
		return svc.createProduct(in);
	}

	@Operation(summary = "Actualizar producto", 
			description = "Actualiza la información de un "
					+ "producto existente, identificado por su ID.")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody DtoProductIn in) {
		return svc.updateProduct(id, in);
	}

	@Operation(summary = "Habilitar producto", 
			description = "Marca un producto como activo (disponible) "
					+ "usando su ID.")
	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableProduct(@PathVariable Integer id) {
		return svc.enableProduct(id);
	}

	@Operation(summary = "Deshabilitar producto", 
			description = "Marca un producto como inactivo (no disponible) "
					+ "usando su ID.")
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableProduct(@PathVariable Integer id) {
		return svc.disableProduct(id);
	}
	
	@GetMapping("/gtin/{gtin}")
	public ResponseEntity<DtoProductOut> getProductByGtin(@PathVariable String gtin) {
	    return ResponseEntity.ok(svc.getProductByGtin(gtin));
	}

	@PutMapping("/{gtin}/stock")
	public ResponseEntity<ApiResponse> updateProductStock(@PathVariable String gtin, @RequestBody Integer quantity) {
	    return ResponseEntity.ok(svc.updateStock(gtin, quantity));
	}
}