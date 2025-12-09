package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;


@Service
public class SvcProductImageImp implements SvcProductImage {

	@Value("${app.upload.dir}")
	private String uploadDir;

	@Autowired
	private RepoProductImage repoProductImage;

	@Override
	public ApiResponse upload(Integer productId, DtoProductImageIn in) {
		try {
			// Eliminar prefijo "data:image/png;base64," si existe
			if (in.getImage().startsWith("data:image")) {
				int commaIndex = in.getImage().indexOf(",");
				if (commaIndex != -1)
					in.setImage(in.getImage().substring(commaIndex + 1));
			}

			// Decodificar Base64
			byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

			// Generar nombre único
			String fileName = UUID.randomUUID().toString() + ".png";

			// Construir ruta completa
			Path imagePath = Paths.get(uploadDir, "img", "product", fileName);

			// Crear directorio si no existe
			Files.createDirectories(imagePath.getParent());

			// Guardar el archivo
			Files.write(imagePath, imageBytes);

			// Crear registro en base de datos
			ProductImage productImage = new ProductImage();
			productImage.setProductId(productId);
			productImage.setImage("/img/product/" + fileName);
			productImage.setStatus(1);
			repoProductImage.save(productImage);

			return new ApiResponse("La imagen ha sido registrada");
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public List<DtoProductImageOut> getImages(Integer productId) {
		try {
			// Traer todas las imágenes y filtrar por productId
			List<ProductImage> images = repoProductImage.findAll().stream()
					.filter(img -> img.getProductId().equals(productId))
					.toList();

			List<DtoProductImageOut> result = new ArrayList<>();

			for (ProductImage pi : images) {
				DtoProductImageOut dto = new DtoProductImageOut();
				dto.setProduct_image_id(pi.getProductImageId());
				dto.setProduct_id(pi.getProductId());
				dto.setImage(readProductImageFile(pi.getImage())); // Base64
				result.add(dto);
			}

			return result;
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private String readProductImageFile(String imageUrl) throws IOException {
		if (imageUrl == null || imageUrl.isEmpty()) return "";

		// Quitar "/" inicial si existe
		if (imageUrl.startsWith("/")) imageUrl = imageUrl.substring(1);

		Path imagePath = Paths.get(uploadDir, imageUrl);

		if (!Files.exists(imagePath))
			return "";

		byte[] imageBytes = Files.readAllBytes(imagePath);
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	@Override
	public ApiResponse deleteImage(Integer productId, Integer productImageId) {
		try {
			// Buscar la imagen por ID
			ProductImage image = repoProductImage.findById(productImageId)
					.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "La imagen no existe"));

			// Validar que la imagen pertenezca al producto
			if (!image.getProductId().equals(productId)) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "La imagen no pertenece a este producto");
			}

			// Eliminar archivo del disco
			String imageUrl = image.getImage();
			if (imageUrl.startsWith("/")) imageUrl = imageUrl.substring(1);
			Path imagePath = Paths.get(uploadDir, imageUrl);

			if (Files.exists(imagePath))
				Files.delete(imagePath);

			// Eliminar registro de BD
			repoProductImage.delete(image);

			return new ApiResponse("La imagen ha sido eliminada");
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo");
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
}

