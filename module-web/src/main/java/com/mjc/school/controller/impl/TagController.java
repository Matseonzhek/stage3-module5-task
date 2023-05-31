package com.mjc.school.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.implementation.TagService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/tags")
@Api(produces = "application/json",
        value = "Operations for creating, updating, retrieving and deleting TAGS in the application")
public class TagController implements BaseRestController<TagDtoRequest, TagDtoResponse, Long> {

    private final TagService tagService;
    private final ObjectMapper mapper;

    public TagController(TagService tagService, ObjectMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping
    @Override
    @ApiOperation(value = "View list of all news", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully displayed page of news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<CollectionModel<TagDtoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<TagDtoResponse> tagDtoResponsePage = tagService.findAll(pageable);
        for (TagDtoResponse tagDtoResponse : tagDtoResponsePage) {
            Long id = tagDtoResponse.getId();
            Link selfLink = linkTo(TagController.class).slash(id).withSelfRel();
            tagDtoResponse.add(selfLink);
        }
        Link link = linkTo(TagController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(tagDtoResponsePage, link), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Override
    @ApiOperation(value = "Get tag by ID", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully displayed tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<TagDtoResponse> readById(
            @PathVariable @NotBlank Long id) {
        TagDtoResponse tagDtoResponse = tagService.readById(id);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @GetMapping(value = "news/{id}/tags")
    @ApiOperation(value = "Get tags by news ID", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully displayed tags"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<List<TagDtoResponse>> readTagsByNewsId(@PathVariable @NotBlank Long id) {
        List<TagDtoResponse> tagDtoResponseList = tagService.readTagsByNewsId(id);
        return new ResponseEntity<>(tagDtoResponseList, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    @ApiOperation(value = "Create tag", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<TagDtoResponse> create(
            @RequestBody @Valid TagDtoRequest createRequest) {
        TagDtoResponse tagDtoResponse = tagService.create(createRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Override
    @ApiOperation(value = "Update tag", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<TagDtoResponse> update(
            @PathVariable @NotBlank Long id,
            @RequestBody @Valid TagDtoRequest updateRequest) {
        TagDtoResponse tagDtoResponse = tagService.update(updateRequest);
        return new ResponseEntity<>(tagDtoResponse, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Override
    @ApiOperation(value = "Update tag", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated tag"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public ResponseEntity<TagDtoResponse> patch(
            @PathVariable @NotBlank Long id,
            @RequestBody JsonPatch patch) {
        try {
            TagDtoResponse tagDtoResponse = tagService.readById(id);
            TagDtoRequest tagDtoRequest = applyPatchAuthor(patch, tagDtoResponse);
            TagDtoResponse tagPatched = tagService.update(tagDtoRequest);
            return new ResponseEntity<>(tagPatched, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @DeleteMapping(value = "/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    }
    )
    public void deleteById(
            @PathVariable @NotBlank Long id) {
        tagService.deleteById(id);
    }

    private TagDtoRequest applyPatchAuthor(JsonPatch patch, TagDtoResponse tagDtoResponse) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(tagDtoResponse, JsonNode.class));
        return mapper.treeToValue(patched, TagDtoRequest.class);
    }
}
