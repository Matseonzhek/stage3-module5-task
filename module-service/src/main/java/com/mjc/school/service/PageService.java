package com.mjc.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageService<T, R, K> {

    Page<R> findAll(Pageable pageable);
}
