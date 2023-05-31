package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.implementation.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "authors")
@Validated
@Api(produces = "application/json",
        value = "Operations for creating, updating, retrieving and deleting AUTHORS in the application")
public class AuthorController implements BaseRestController<AuthorDtoRequest, AuthorDtoResponse, Long> {

    private final AuthorService authorService;
    private final ObjectMapper mapper;


    public AuthorController(AuthorService authorService, ObjectMapper mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }


    @GetMapping
    @Override
    @ApiOperation(value = "View list of all authors", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed page of authors"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<CollectionModel<AuthorDtoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<AuthorDtoResponse> authorDtoResponsePage = authorService.findAll(pageable);
        for (AuthorDtoResponse authorDtoResponse : authorDtoResponsePage) {
            Long id = authorDtoResponse.getId();
            Link selLink = linkTo(AuthorController.class).slash(id).withSelfRel();
            authorDtoResponse.add(selLink);
        }
        Link link = linkTo(AuthorController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(authorDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    @ApiOperation(value = "Get an author by ID", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<AuthorDtoResponse> readById(@PathVariable @NotBlank Long id) {
        AuthorDtoResponse authorDtoResponse = authorService.readById(id);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "news/{id}/authors")
    @ApiOperation(value = "Get an author by News ID", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully displayed an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<AuthorDtoResponse> readAuthorByNewsId(@PathVariable @NotBlank Long id) {
        AuthorDtoResponse authorDtoResponse = authorService.readAuthorByNewsId(id);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    @ApiOperation(value = "Create an author", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<AuthorDtoResponse> create(
            @RequestBody @Valid AuthorDtoRequest createRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.create(createRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}", consumes = "application/json", produces = {"application/json", "application/xml"})
    @Override
    @ApiOperation(value = "Update an author", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<AuthorDtoResponse> update(
            @PathVariable @NotBlank Long id,
            @RequestBody @Valid AuthorDtoRequest updateRequest) {
        AuthorDtoResponse authorDtoResponse = authorService.update(updateRequest);
        return new ResponseEntity<>(authorDtoResponse, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @ApiOperation(value = "Update an author", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<AuthorDtoResponse> patch(
            @PathVariable Long id,
            @RequestBody JsonPatch patch) {
        try {
            AuthorDtoResponse authorDtoResponse = authorService.readById(id);
            AuthorDtoRequest updateRequest = applyPatchAuthor(patch, authorDtoResponse);
            AuthorDtoResponse authorPatched = authorService.update(updateRequest);
            return new ResponseEntity<>(authorPatched, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete an author")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public void deleteById(
            @PathVariable @NotBlank Long id) {
        authorService.deleteById(id);
    }

    private AuthorDtoRequest applyPatchAuthor(JsonPatch patch, AuthorDtoResponse authorDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(authorDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, AuthorDtoRequest.class);
    }
}
