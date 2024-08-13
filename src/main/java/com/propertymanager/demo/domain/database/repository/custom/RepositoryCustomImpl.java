package com.propertymanager.demo.domain.database.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class RepositoryCustomImpl<T> implements RepositoryCustom<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<T> searchByCriteria(Class<T> entityClass, Map<String, String> query, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder(); // CriteriaBuilder para cuidar dos criterios de resposta como ordenacao, limite de resultados paginacao etc...
        CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass); // CriteriaQuery com metodo createQuery que sera res[pstavel pelas clausulas SQL como por exemplo SELECT, FROM, WHERER, AND etc..
        Root<T> root = criteriaQuery.from(entityClass); // Root<T> que sera a classe raiz ou referenciando o JPQL um alias para a classe algo equivalente a "SELECT * FROM"

        List<Predicate> predicates = new ArrayList<>();

        // Process the query parameters
        for (Map.Entry<String, String> entry : query.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.trim().isEmpty()) {
                Path<?> path = buildPath(root, key);
                predicates.add(cb.equal(path, value));
            }
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<T> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private Path<?> buildPath(Root<T> root, String key) {
        Path<?> path = root;
        String[] parts = key.split("\\.");

        for (String part : parts) {
            path = path.get(part);
        }

        return path;
    }

}

