package com.mjc.school.repository;

import com.mjc.school.repository.model.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentPageRepository extends PagingAndSortingRepository<CommentModel, Long> {
    Page<CommentModel> findAll(Pageable pageable);
}
