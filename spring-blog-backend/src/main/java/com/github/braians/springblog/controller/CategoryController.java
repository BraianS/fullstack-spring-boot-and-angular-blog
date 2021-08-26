package com.github.braians.springblog.controller;

import java.util.Set;

import javax.validation.Valid;

import com.github.braians.springblog.payload.AppResponse;
import com.github.braians.springblog.payload.SuccessResponse;
import com.github.braians.springblog.payload.TitleRequest;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.projection.TagsOrCategoriesResponse;
import com.github.braians.springblog.security.CurrentUser;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.service.CategoryService;
import com.github.braians.springblog.util.AppConstant;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @ApiOperation(value = "Save Category")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping(value = "/")
    public ResponseEntity<AppResponse> save(@RequestBody @Valid TitleRequest titleRequest) {
        categoryService.save(titleRequest);
        log.info("Category Created Successfully");
        return ResponseEntity.ok().body(new SuccessResponse("Category Created Successfully"));
    }

    @ApiOperation(value = "Update Category by id")
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id, @RequestBody @Valid TitleRequest titleRequest,@CurrentUser UserPrincipal currentUser) {
        categoryService.update(id, titleRequest, currentUser);
        log.info("Category Updated Successfully");
        return ResponseEntity.ok().body(new SuccessResponse("Category Updated Successfully"));
    }
    
    @ApiOperation(value = "Find Posts by Category id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<PostSummary>> findPostsByCategoryId(
        @PathVariable("id") Long id,
        @RequestParam(name = "page",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(name = "size",defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok().body(categoryService.findPostsByCategoryId(id, page, size));
    }

    @ApiOperation(value = "Delete Category by Id")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id,@CurrentUser UserPrincipal currentUser) {
        categoryService.deleteById(id, currentUser);
        log.info("Category Deleted Successfully");
        return ResponseEntity.ok().body(new SuccessResponse("Category Deleted Successfully"));
    }

    @ApiOperation(value = "Get Categories")
    @GetMapping(value = "/")
    public ResponseEntity<Set< TagsOrCategoriesResponse>> getTags() {       
        return ResponseEntity.ok().body(categoryService.findCategories());
    }

    @ApiOperation(value = "Search 10 categories by title")
    @GetMapping(value = "/search")
    public ResponseEntity<Set<TagsOrCategoriesResponse>> findAllByTitleAndLimitBy10(@RequestParam("title") String title) {       
        return ResponseEntity.ok().body(categoryService.findAllByTitleAndLimitBy10(title));
    }

}
