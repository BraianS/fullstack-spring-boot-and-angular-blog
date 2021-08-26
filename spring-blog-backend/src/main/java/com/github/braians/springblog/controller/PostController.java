package com.github.braians.springblog.controller;

import javax.validation.Valid;

import com.github.braians.springblog.payload.AppResponse;
import com.github.braians.springblog.payload.PostRequest;
import com.github.braians.springblog.payload.SuccessResponse;
import com.github.braians.springblog.projection.PostDetail;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.security.CurrentUser;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.service.PostService;
import com.github.braians.springblog.util.AppConstant;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value = "/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "Save Post")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/")
    public ResponseEntity<AppResponse> save(@RequestBody @Valid PostRequest postRequest, @CurrentUser UserPrincipal currentUser) {   
        postService.save(postRequest, currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Post Created Successfully"));
    }
    
    @ApiOperation(value = "Update Post by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id,@RequestBody @Valid PostRequest postRequest, @CurrentUser UserPrincipal currentUser) {  
        postService.update(id, postRequest,currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Post Updated Successfully"));
    }

    @ApiOperation(value = "Find Post by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDetail> findById(@PathVariable("id") Long id) {       
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @ApiOperation(value = "Delete Post by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @DeleteMapping(value = "/{id}")    
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser) {  
        postService.deleteById(id,currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Post Deleted Successfully"));
    }

    @ApiOperation(value = "Find All Posts Order By Created At Asc")
    @GetMapping(value = "/")
    public ResponseEntity<Page<PostSummary>> getAllPostsByCreatedAtAsc(
        @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size)
     {       
        return ResponseEntity.ok().body(postService.findAllOrderByCreatedAtAsc(page,size));
    }
    
}
