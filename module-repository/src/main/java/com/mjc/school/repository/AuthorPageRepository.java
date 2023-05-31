package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorPageRepository extends PagingAndSortingRepository<AuthorModel, Long> {

    Page<AuthorModel> findAll(Pageable pageable);
}
