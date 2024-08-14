package com.altimetrik.online_shop.product_service.products.repository;

import com.altimetrik.online_shop.product_service.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.price.amount ASC")
//    List<Product> findByCategoryOrderByPriceAmountAsc(@Param("category") String category);
//
//    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.price.amount DESC")
//    List<Product> findByCategoryOrderByPriceAmountDesc(@Param("category") String category);
//
//    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.inventory.available ASC")
//    List<Product> findByCategoryOrderByInventoryAvailableAsc(@Param("category") String category);
//
//    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.inventory.available DESC")
//    List<Product> findByCategoryOrderByInventoryAvailableDesc(@Param("category") String category);
//
//    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0")
//    List<Product> findByCategory(@Param("category") String category);


    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.price.amount ASC")
    List<Product> findByCategoryOrderByPriceAmountAsc(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.price.amount DESC")
    List<Product> findByCategoryOrderByPriceAmountDesc(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.inventory.available ASC")
    List<Product> findByCategoryOrderByInventoryAvailableAsc(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0 ORDER BY p.inventory.available DESC")
    List<Product> findByCategoryOrderByInventoryAvailableDesc(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.inventory.available > 0")
    List<Product> findByCategory(@Param("category") String category);
}
