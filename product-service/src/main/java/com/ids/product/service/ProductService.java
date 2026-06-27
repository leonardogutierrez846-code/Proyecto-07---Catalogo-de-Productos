package com.ids.product.service;

import com.ids.product.exception.ResourceNotFoundException;
import com.ids.product.model.Product;
import com.ids.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    public Product update(Long id, Product productData) {
        Product product = findById(id);
        product.setNombre(productData.getNombre());
        product.setDescripcion(productData.getDescripcion());
        product.setPrecio(productData.getPrecio());
        product.setCategoria(productData.getCategoria());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
