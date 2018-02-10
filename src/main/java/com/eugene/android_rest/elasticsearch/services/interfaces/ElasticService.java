package com.eugene.android_rest.elasticsearch.services.interfaces;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ElasticService {

    CompletableFuture<Boolean> refresh();

    <T> List<T> search(String query, Class<T> clazz);
}
