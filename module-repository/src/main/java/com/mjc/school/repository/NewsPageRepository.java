package com.mjc.school.repository;

import com.mjc.school.repository.model.NewsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsPageRepository extends PagingAndSortingRepository<NewsModel, Long> {

    Page<NewsModel> findAll(Pageable pageable);
}
