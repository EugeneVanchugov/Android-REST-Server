package com.eugene.android_rest.aspects;

import com.eugene.android_rest.annotations.ElasticDelete;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class ElasticUpdateAspect {

    private final Map<Class, ElasticsearchRepository> repositoryMap;


    public ElasticUpdateAspect(Set<ElasticsearchRepository> repositorySet) {
        this.repositoryMap = new HashMap<>();
        repositorySet.forEach(repository -> repositoryMap.put(repository.getEntityClass(), repository));
    }

    @AfterReturning(value = "@annotation(com.eugene.android_rest.annotations.ElasticUpdate)", returning = "value")
    public void save(Object value) {
        ElasticsearchRepository repository = repositoryMap.get(value.getClass());
        repository.save(value);
    }

    @Before(value = "@annotation(context) && args(value)", argNames = "value, context")
    public void delete(Serializable value, ElasticDelete context) {
        Class targetClass = context.value();
        ElasticsearchRepository repository = repositoryMap.get(targetClass);
        repository.delete(value);

    }
}
