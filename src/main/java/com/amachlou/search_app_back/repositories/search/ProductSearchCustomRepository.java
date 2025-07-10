package com.amachlou.search_app_back.repositories.search;

import com.amachlou.search_app_back.entities.ProductDocument;

import java.util.List;

public interface ProductSearchCustomRepository {
    List<ProductDocument> fullTextSearch(String query);
    List<ProductDocument> searchByTerm(String term);
}

