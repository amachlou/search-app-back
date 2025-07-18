package com.amachlou.search_app_back.services;

import com.amachlou.search_app_back.entities.Product;
import com.amachlou.search_app_back.repositories.elastic.ProductSearchRepository;
import com.amachlou.search_app_back.repositories.jpa.ProductRepository;
import com.amachlou.search_app_back.repositories.search.ProductSearchCustomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductSearchCustomRepository productSearchCustomRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductSearchRepository productSearchRepository,
                              ProductSearchCustomRepository productSearchCustomRepository) {
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
        this.productSearchCustomRepository = productSearchCustomRepository;
    }

    @Override
    public List<Product> getAllByTermThroughIds(String term) {
        List<Long> ids = productSearchCustomRepository.fetchAllElasticIds();
        return productRepository.findAllById(ids);
    }

}

