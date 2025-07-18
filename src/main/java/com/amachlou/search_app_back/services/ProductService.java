package com.amachlou.search_app_back.services;

import com.amachlou.search_app_back.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllByTermThroughIds(String term);

}

