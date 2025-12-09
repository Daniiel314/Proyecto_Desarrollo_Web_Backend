package com.product.api.controller;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import io.swagger.v3.oas.annotations.Operation; // Importación añadida
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Category", description = "Catálogo de categorías")
@RestController
public class CtrlCategory {

    @Autowired
    private SvcCategory svc;
    
    
    @Operation(summary = "Listar todas las categorías", 
    		description = "Devuelve una lista de todas las categorías,"
    				+ " tanto activas como inactivas.")
    @GetMapping("/category")
    public ResponseEntity<List<Category>> findAll() {
    	return ResponseEntity.ok(svc.findAll());
    }
    
    @Operation(summary = "Listar categorías activas", 
    		description = "Devuelve una lista de solo las categorías"
    				+ " que están marcadas como activas.")
    @GetMapping("/category/active")
    public ResponseEntity<List<Category>> findActive(){
    	return ResponseEntity.ok(svc.findActive());
    }
    
    @Operation(summary = "Crear categoría", 
    		description = "Registra una nueva categoría en el sistema.")
    @PostMapping("/category")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCategoryIn in) {
        return ResponseEntity.ok(svc.create(in));
    }

    @Operation(summary = "Actualizar categoría", 
    		description = "Actualiza los datos de una categoría existente, "
    				+ "identificada por su ID.")
    @PutMapping("/category/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, 
    		@Valid @RequestBody DtoCategoryIn in) {
        return ResponseEntity.ok(svc.update(in, id));
    }
    
    @Operation(summary = "Habilitar categoría", 
    		description = "Marca una categoría como activa usando su ID.")
    @PatchMapping("/category/{id}/enable")
    public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.enable(id));
    }
    
    @Operation(summary = "Deshabilitar categoría", 
    		description = "Marca una categoría como inactiva usando su ID.")
    @PatchMapping("/category/{id}/disable")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.disable(id));
    }
}