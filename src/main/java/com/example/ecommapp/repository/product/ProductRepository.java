package com.example.ecommapp.repository.product;

import com.example.ecommapp.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategoryCategoryName(String categoryName);

    @Query("{ $text: { $search: ?0 } }")
    List<Product> searchByText(String text);

    List<Product> findByStatus(String status);
}
