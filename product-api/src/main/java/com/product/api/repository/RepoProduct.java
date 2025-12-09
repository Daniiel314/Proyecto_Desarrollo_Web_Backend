package com.product.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {
	// Necesitamos buscar por GTIN para validar el carrito
	Optional<Product> findByGtinAndStatus(String gtin, Integer status);

	@Modifying
	@Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.gtin = :gtin AND p.status = 1")
	int updateStock(@Param("gtin") String gtin, @Param("quantity") Integer quantity);
}
