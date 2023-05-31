package com.mjc.school.repository;

import com.mjc.school.repository.model.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagPageRepository extends PagingAndSortingRepository<TagModel, Long> {
    Page<TagModel> findAll(Pageable pageable);
}
