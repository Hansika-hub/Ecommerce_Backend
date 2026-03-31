package com.example.ecommapp.service;

import com.example.ecommapp.dto.ProductRequest;
import com.example.ecommapp.model.Product;
import com.example.ecommapp.model.RatingSummary;
import com.example.ecommapp.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Qualifier("productMongoTemplate")
    private final MongoTemplate productMongoTemplate;

    public Product addProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCompanyId(request.getCompanyId());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus() != null ? request.getStatus() : "active");
        product.setVariants(request.getVariants());
        product.setImages(request.getImages());
        product.setRatingSummary(new RatingSummary(0.0, 0));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> getAllActiveProducts() {
        return productRepository.findByStatus("active");
    }

    // Spec: db.products.findOne({ _id: "P101" }) → full product with
    // variants/images/rating
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Spec: Search by category: db.products.find({ "category.categoryName": "Shoes"
    // })
    public List<Product> getByCategory(String categoryName) {
        return productRepository.findByCategoryCategoryName(categoryName);
    }

    // Spec: Text search: db.products.find({ $text: { $search: "..." } })
    public List<Product> searchByName(String text) {
        return productRepository.searchByText(text);
    }

    // Spec: Atomic stock update: db.products.updateOne({ _id: "P101",
    // "variants.variantId": "V1" }, { $inc: { "variants.$.stock": -2 } })
    public void deductVariantStock(String productId, String variantId, int qty) {
        Query query = new Query(Criteria.where("_id").is(productId).and("variants.variantId").is(variantId));
        Update update = new Update().inc("variants.$.stock", -qty);
        productMongoTemplate.updateFirst(query, update, Product.class);
    }

    public Product updateProduct(String id, ProductRequest request) {
        Product product = getProductById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCompanyId(request.getCompanyId());
        product.setCategory(request.getCategory());
        product.setStatus(request.getStatus());
        product.setVariants(request.getVariants());
        product.setImages(request.getImages());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public String deleteProduct(String id) {
        Product product = getProductById(id);
        product.setStatus("discontinued");
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return "Product deleted successfully";
    }
}