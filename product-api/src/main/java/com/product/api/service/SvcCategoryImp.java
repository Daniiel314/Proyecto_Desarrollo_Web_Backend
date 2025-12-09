package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {
	
	@Autowired
    RepoCategory repo;
	
	@Override
    public List<Category> findAll() {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
	
	@Override
    public List<Category> findActive() {
        try {
            return repo.findByStatusOrderByCategoryAsc(1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
	
	@Override
    public ApiResponse create(DtoCategoryIn in) {
        try {
            Category c = new Category();
            c.setCategory(in.getCategory());
            c.setTag(in.getTag());
            c.setStatus(1);
            repo.save(c);
            return new ApiResponse("La categoría ha sido registrada");
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
            if (e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
            throw new DBAccessException(e);
        }
    }
	
	@Override
	public ApiResponse update(DtoCategoryIn in, Integer id) {
	    validateId(id); 
	    repo.updateCategory(id, in.getCategory(), in.getTag());
	    return new ApiResponse("La categoría ha sido actualizada");
	}

	
	@Override
    public ApiResponse enable(Integer id) {
        validateId(id);
        repo.updateStatus(id, 1);
        return new ApiResponse("La categoría ha sido activada");
    }
	
	@Override
    public ApiResponse disable(Integer id) {
        validateId(id);
        repo.updateStatus(id, 0);
        return new ApiResponse("La categoría ha sido desactivada");
    }
	
	private void validateId(Integer id) {
        if (repo.findById(id).isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
    }
}
