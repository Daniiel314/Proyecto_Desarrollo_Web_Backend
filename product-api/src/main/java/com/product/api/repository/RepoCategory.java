package com.product.api.repository;

import org.springframework.stereotype.Repository;
import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {
	List<Category> findByStatusOrderByCategoryAsc(Integer status);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Category c SET c.category = :category, c.tag = :tag WHERE c.category_id = :id")
    void updateCategory(@Param("id") Integer id, @Param("category") String category, @Param("tag") String tag);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Category c SET c.status = :status WHERE c.category_id = :id")
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
