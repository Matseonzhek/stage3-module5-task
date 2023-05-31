package com.mjc.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface BaseRestController<T, R, K> {
    ResponseEntity<CollectionModel<R>> findAll(int page, int size, String sortBy);

    ResponseEntity<R> readById(K id);

    ResponseEntity<R> create(T createRequest);

    ResponseEntity<R> update(K id, T updateRequest);

    ResponseEntity<R> patch(K id, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    void deleteById(K id);
}
