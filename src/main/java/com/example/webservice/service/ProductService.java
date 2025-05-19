package com.example.webservice.service;

import com.example.webservice.dto.ProductDto;
import com.example.webservice.exception.BadRequestException;
import com.example.webservice.exception.ResourceNotFoundException;
import com.example.webservice.model.Product;
import com.example.webservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(ProductDto productDto) {
        if (productRepository.existsByCode(productDto.getCode())) {
            throw new BadRequestException("Product code already exists: " + productDto.getCode());
        }

        Product product = new Product();
        product.setCode(productDto.getCode());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setIllustration(productDto.getIllustration());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }

    public Product updateProduct(String id, ProductDto productDto) {
        Product product = getProductById(id);

        // Check if code is being changed and if it already exists
        if (!product.getCode().equals(productDto.getCode()) &&
                productRepository.existsByCode(productDto.getCode())) {
            throw new BadRequestException("Product code already exists: " + productDto.getCode());
        }

        product.setCode(productDto.getCode());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setIllustration(productDto.getIllustration());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }

    public Product patchProduct(String id, ProductDto productDto) {
        Product product = getProductById(id);

        // Only update non-null fields
        if (productDto.getCode() != null) {
            // Check if code is being changed and if it already exists
            if (!product.getCode().equals(productDto.getCode()) &&
                    productRepository.existsByCode(productDto.getCode())) {
                throw new BadRequestException("Product code already exists");
            }
            product.setCode(productDto.getCode());
        }

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }

        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }

        if (productDto.getIllustration() != null) {
            product.setIllustration(productDto.getIllustration());
        }

        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}