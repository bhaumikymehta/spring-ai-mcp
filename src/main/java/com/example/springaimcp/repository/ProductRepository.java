package com.example.springaimcp.repository;

import com.example.springaimcp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List; // Example if you add custom methods

/**
 * Spring Data JPA repository for the Product entity.
 * This interface handles database operations for Product objects.
 * JpaRepository provides standard CRUD operations and more.
 */
@Repository // Marks this interface as a Spring Data repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JpaRepository already provides methods like:
    // - save(Product entity)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - etc.

    // Example custom query method (uncomment and use if needed):
    // List<Product> findByCategory(String category);
}
