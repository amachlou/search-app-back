package com.amachlou.search_app_back.repositories.jpa;

import com.amachlou.search_app_back.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);

}