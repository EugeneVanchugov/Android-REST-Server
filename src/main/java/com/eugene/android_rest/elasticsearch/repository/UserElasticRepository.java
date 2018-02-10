package com.eugene.android_rest.elasticsearch.repository;

import com.eugene.android_rest.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticRepository extends ElasticsearchRepository<User, Long> {
}
