package com.amachlou.search_app_back.repositories.elastic;

import com.amachlou.search_app_back.entities.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    // Full-text search on name or description or Price
    List<ProductDocument> findByNameContainingOrDescriptionContaining(String name, String description);

    // Search with Price
    List<ProductDocument> findByPriceContaining(Double price);

}