package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.TagPageRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    private final EntityManager entityManager;
    private final TagPageRepository tagRepository;

    @Autowired
    public TagRepository(EntityManager entityManager, TagPageRepository tagRepository) {
        this.entityManager = entityManager;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagModel> readAll() {
        String jpql = "select tag from TagModel tag order by tag.id";
        TypedQuery<TagModel> query = entityManager.createQuery(jpql, TagModel.class);
        return query.getResultList();
    }

    @Query(value = "select tag from TagModel tag")
    public Page<TagModel> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        TagModel tagModel = entityManager.find(TagModel.class, id);
        return Optional.ofNullable(tagModel);
    }

    public List<TagModel> readTagsByNewsId(Long newsId) {
        String jpql = " select tags from TagModel tags inner join tags.tags news where news.id =:newsId";
        TypedQuery<TagModel> query = entityManager.createQuery(jpql, TagModel.class);
        return query.setParameter("newsId", newsId).getResultList();
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
        if (existById(entity.getId())) {
            TagModel updatedTagModel = readById(entity.getId()).get();
            updatedTagModel.setName(entity.getName());
            entityManager.persist(updatedTagModel);
            return updatedTagModel;
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            TagModel tagModel = readById(id).get();
            entityManager.remove(tagModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        String jpql = " select count (tags)  from TagModel tags where tags.id=?1";
        javax.persistence.Query query = entityManager.createQuery(jpql).setParameter(1, id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }
}
