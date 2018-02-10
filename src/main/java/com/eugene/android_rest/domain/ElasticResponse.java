package com.eugene.android_rest.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ElasticResponse {
    private List<User> users = Collections.emptyList();
}
