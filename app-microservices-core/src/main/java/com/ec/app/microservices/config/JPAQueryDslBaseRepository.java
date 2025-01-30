package com.ec.app.microservices.config;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class JPAQueryDslBaseRepository<T> extends QuerydslRepositorySupport implements IQueryDslBaseRepository<T> {

    @Autowired
    private HttpServletRequest request;

    private final Class<T> domainClass;

    public JPAQueryDslBaseRepository(Class<T> domainClass) {
        super(domainClass);
        this.domainClass = domainClass;
    }

    public void save(T entity) {
        this.getEntityManager().persist(entity);
    }

    public void update(T entity) {
        this.getEntityManager().merge(entity);
    }

    public void delete(T entity) {
        this.getEntityManager().remove(
                this.getEntityManager().contains(entity) ? entity : this.getEntityManager().merge(entity)
        );
    }

    protected <P> JPQLQuery<P> cloneQuery(JPAQuery<P> query) {
        return query.clone(this.getEntityManager());
    }

    protected <Q> Page<Q> findPageData(JPQLQuery<Q> query, Pageable pageable) {
        JPQLQuery<Q> countQuery = this.cloneQuery((JPAQuery) query);
        List var10000 = this.getQuerydsl().applyPagination(pageable, query).fetch();
        Objects.requireNonNull(countQuery);
        return PageableExecutionUtils.getPage(var10000, pageable, countQuery::fetchCount);
    }

    public Integer getSequence(String sequenceName) {
        StringBuilder query = new StringBuilder()
                .append("SELECT nextval('")
                .append(sequenceName)
                .append("') as num");
        return Integer.parseInt(getEntityManager().createNativeQuery(query.toString()).getSingleResult().toString());
    }

}
