package com.amachlou.search_app_back.repositories.search;

import com.amachlou.search_app_back.entities.ProductDocument;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
public class ProductSearchCustomRepositoryImpl implements ProductSearchCustomRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    public ProductSearchCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<ProductDocument> fullTextSearch(String term) {

        var QueryTermMultipleSearch = NativeQuery
                                            .builder()
                                            .withQuery(q -> q
                                                    .multiMatch(mm -> mm
                                                            .query("*")
                                                            .fields("name", "description", "category")
                                                    )
                                            )
                                            .build();

        var QueryTermSearch = NativeQuery.builder()
                                        .withQuery(q -> q
                                                .match(m -> m
                                                        .field("name")
                                                        .query(term)
                                                )
                                        )
                                        .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(QueryTermSearch, ProductDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<ProductDocument> findAllWithFields() {
        Query query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .withSourceFilter(new FetchSourceFilter(true,
                        new String[] {"name", "description", "price"}, null
                ))
                .withMaxResults(100)
                .build();

        return elasticsearchOperations
                .search(query, ProductDocument.class)
                .stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    public List<ProductDocument> searchByTerm(String term) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(term)
                                .fields("name", "description", "price")
                        )
                )
                .withSourceFilter(new FetchSourceFilter(
                        true, new String[] {"name", "description"}, null
                ))
                .withMaxResults(100)
                .build();

        return elasticsearchOperations
                .search(query, ProductDocument.class)
                .stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    @Override
    public List<Long> fetchAllElasticIds() {
        NativeQuery query = new NativeQueryBuilder()
                .withFields()
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(query, ProductDocument.class);

        return hits.getSearchHits()
                .stream()
                .map(SearchHit::getId)
                .map(Long::valueOf)
                .toList();
    }


}
