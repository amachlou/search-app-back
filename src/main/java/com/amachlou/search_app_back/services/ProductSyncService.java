package com.amachlou.search_app_back.services;

import com.amachlou.search_app_back.entities.Product;
import com.amachlou.search_app_back.entities.ProductDocument;
import com.amachlou.search_app_back.repositories.elastic.ProductSearchRepository;
import com.amachlou.search_app_back.repositories.jpa.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSyncService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;

    public ProductSyncService(ProductRepository productRepository,
                              ProductSearchRepository productSearchRepository) {
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
    }

    @Async
    @PostConstruct
    public void syncDatabaseToElastic() {
        List<Product> products = productRepository.findAll();

        List<ProductDocument> documents = products.stream()
                                                .map(ProductDocument::fromEntity)
                                                .toList();

        productSearchRepository.saveAll(documents);
    }
}

