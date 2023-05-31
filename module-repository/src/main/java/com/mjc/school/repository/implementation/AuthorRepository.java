package com.mjc.school.repository.implementation;

import com.mjc.school.repository.AuthorPageRepository;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {


    @Autowired
    @Qualifier(value = "tx")
    private final TransactionTemplate transactionTemplate;
    private final EntityManager entityManager;
    private final AuthorPageRepository authorRepository;

    @Autowired
    public AuthorRepository(EntityManager entityManager, TransactionTemplate transactionTemplate, AuthorPageRepository authorRepository) {
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
        this.authorRepository = authorRepository;
    }

    @Query(value = "select author from AuthorModel author")
    public Page<AuthorModel> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public List<AuthorModel> readAll() {
        String jpql = "select authors from AuthorModel authors order by authors.id";
        TypedQuery<AuthorModel> query = entityManager.createQuery(jpql, AuthorModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        AuthorModel authorModel = entityManager.find(AuthorModel.class, id);
        return Optional.ofNullable(authorModel);
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        transactionTemplate.executeWithoutResult(pop -> entityManager.persist(entity));
//        entityManager.persist(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        long id = entity.getId();
        if (existById(id)) {
            AuthorModel authorModel = readById(id).get();
            authorModel.setName(entity.getName());
            entityManager.merge(authorModel);
            return authorModel;
        }
        return null;
    }

    public Optional<AuthorModel> readAuthorByNewsId(Long newsId) {
        String jpql = " select authors from AuthorModel authors, NewsModel news where news.authorModel = authors and" +
                " news.id =:newsId";
        TypedQuery<AuthorModel> query = entityManager.createQuery(jpql, AuthorModel.class);
        try {
            return Optional.of(query.setParameter("newsId", newsId).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            AuthorModel authorModel = readById(id).get();
            entityManager.remove(authorModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        String jpql = " select count (authors) from AuthorModel authors where authors.id=?1";
        javax.persistence.Query query = entityManager.createQuery(jpql).setParameter(1, id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }
}
