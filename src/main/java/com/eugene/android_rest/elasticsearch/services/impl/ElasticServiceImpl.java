package com.eugene.android_rest.elasticsearch.services.impl;

import com.eugene.android_rest.database.repository.UserRepository;
import com.eugene.android_rest.elasticsearch.repository.UserElasticRepository;
import com.eugene.android_rest.elasticsearch.services.interfaces.ElasticService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;

@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService {

    private final ElasticsearchTemplate es;
    private final UserRepository userRepository;
    private final UserElasticRepository userElasticRepository;

    @Autowired
    public ElasticServiceImpl(ElasticsearchTemplate es, UserRepository userRepository, UserElasticRepository userElasticRepository) {
        this.es = es;
        this.userRepository = userRepository;
        this.userElasticRepository = userElasticRepository;
    }

    public CompletableFuture<Boolean> refresh() {
        log.info("Start refreshing ElasticSearch indices");

        CompletableFuture.allOf(userFuture()).join();

        log.info("Refresh SUCCESS!");

        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    private CompletableFuture<Void> userFuture() {
        return CompletableFuture
                .runAsync(() -> userRepository.findAll().forEach(userElasticRepository::index))
                .thenRun(() -> log.info("Users refreshed!"));
    }

    @Override
    public <T> List<T> search(String query, Class<T> clazz) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(query)
                                .lenient(true)
                                .defaultOperator(Operator.AND)
                ).should(
                        QueryBuilders.queryStringQuery("*" + query + "*")
                                .lenient(true)
                                .defaultOperator(Operator.AND)
                );

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(new PageRequest(0, 10000))
                .build();
        return es.queryForList(searchQuery, clazz);
    }




}
