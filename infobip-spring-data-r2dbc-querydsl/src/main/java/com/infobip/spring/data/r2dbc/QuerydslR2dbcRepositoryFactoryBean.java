/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.infobip.spring.data.r2dbc;

import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

public class QuerydslR2dbcRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends R2dbcRepositoryFactoryBean<T, S, ID> {

    private SQLQueryFactory sqlQueryFactory;
    private final Class<?> repositoryBaseClass;

    protected QuerydslR2dbcRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        this(repositoryInterface, SimpleQuerydslR2dbcRepository.class);
    }

    protected QuerydslR2dbcRepositoryFactoryBean(Class<? extends T> repositoryInterface, Class<?> repositoryBaseClass) {
        super(repositoryInterface);
        this.repositoryBaseClass = repositoryBaseClass;
    }

    @Override
    protected RepositoryFactorySupport getFactoryInstance(R2dbcEntityOperations operations) {
        return new QuerydslR2dbcRepositoryFactory(operations, sqlQueryFactory, repositoryBaseClass);
    }

    @Autowired
    public void setSQLQueryFactory(SQLQueryFactory sqlQueryFactory) {
        this.sqlQueryFactory = sqlQueryFactory;
    }
}