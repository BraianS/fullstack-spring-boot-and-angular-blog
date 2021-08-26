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
import com.github.braians.springblog.service.TagService;
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


@RestController
@RequestMapping(value = "/api/tag")
public class TagController {
    
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "Save Tag")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/")
    public ResponseEntity<AppResponse> save(@RequestBody @Valid TitleRequest titleRequest) {     
        tagService.save(titleRequest);   
        return ResponseEntity.ok().body(new SuccessResponse("Tag Created Successfully"));
    }

    @ApiOperation(value = "Update Tag by id")
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id, @RequestBody @Valid TitleRequest titleRequest, @CurrentUser UserPrincipal  currentUser) { 
        tagService.update(id, titleRequest, currentUser);      
        return ResponseEntity.ok().body(new SuccessResponse("Tag Updated Successfully"));
    }

    @ApiOperation(value = "Save Tag")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Page< PostSummary>> findById(
        @PathVariable("id") Long id,
        @RequestParam(name = "page",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(name = "size",defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size) {       
        return ResponseEntity.ok().body(tagService.findById(id, page,size));
    }

    @ApiOperation(value = "Delete Tag by id")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id,@CurrentUser UserPrincipal  currentUser) {  
        tagService.deleteById(id,currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Tag Deleted Successfully"));
    }

    @ApiOperation(value = "Get Tags")
    @GetMapping(value = "/")
    public ResponseEntity<Set<TagsOrCategoriesResponse>> getTags() {       
        return ResponseEntity.ok().body(tagService.findTags());
    }

    @ApiOperation(value = "Search 10 tags by title")
    @GetMapping(value = "/search")
    public ResponseEntity<Set<TagsOrCategoriesResponse>> findAllByTitleAndLimitBy10(@RequestParam("title") String title) {       
        return ResponseEntity.ok().body(tagService.findAllByTitleAndLimitBy10(title));
    }

}
