package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.CommentPageRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long> {

    private final EntityManager entityManager;
    private final CommentPageRepository commentRepository;

    @Autowired
    public CommentRepository(EntityManager entityManager, CommentPageRepository commentRepository) {
        this.entityManager = entityManager;
        this.commentRepository = commentRepository;
    }

    public Page<CommentModel> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public List<CommentModel> readAll() {
        String jpql = " select comments from CommentModel comments order by comments.id ";
        TypedQuery<CommentModel> query = entityManager.createQuery(jpql, CommentModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        CommentModel commentModel = entityManager.find(CommentModel.class, id);
        return Optional.ofNullable(commentModel);
    }

    public List<CommentModel> readCommentsByNewsId(Long newsId) {
        String jpql = " select comments from CommentModel comments inner join comments.newsModel news where news.id =:newsId";
        TypedQuery<CommentModel> query = entityManager.createQuery(jpql, CommentModel.class);
        return query.setParameter("newsId", newsId).getResultList();
    }

    @Override
    public CommentModel create(CommentModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CommentModel update(CommentModel entity) {
        long id = entity.getId();
        if (existById(id)) {
            CommentModel updatedComment = readById(id).get();
            updatedComment.setContent(entity.getContent());
            entityManager.merge(updatedComment);
            return updatedComment;
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(readById(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        String jpql = " select count (comments)  from CommentModel comments where comments.id=?1";
        Query query = entityManager.createQuery(jpql).setParameter(1, id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }
}
