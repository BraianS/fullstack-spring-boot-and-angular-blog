package com.github.braians.springblog.controller;



import javax.validation.Valid;

import com.github.braians.springblog.payload.AppResponse;
import com.github.braians.springblog.payload.CommentRequest;
import com.github.braians.springblog.payload.SuccessResponse;
import com.github.braians.springblog.security.CurrentUser;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {       
       this.commentService = commentService;
    }

    @ApiOperation(value = "Save Comment in Post by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(value = "/{postId}")
    public ResponseEntity<AppResponse> saveComment(
        @ApiParam(value = "post id") @PathVariable("postId") Long postId, @RequestBody @Valid CommentRequest commentRequest) {     
        commentService.save(postId,commentRequest);   
        return ResponseEntity.ok().body(new SuccessResponse("Comment Created Successfully"));
    }

    @ApiOperation(value = "Save sub Comment")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(value = "/{postId}/subComment/{commentId}")
    public ResponseEntity<AppResponse> saveSubComment(
        @ApiParam(value = "post id") @PathVariable("postId") Long postId,
        @ApiParam(value = "comment id") @PathVariable("commentId") Long commentId,
        @RequestBody @Valid CommentRequest commentRequest, @CurrentUser UserPrincipal currentUser) {       
        commentService.addCommentToParentId(postId,commentId, commentRequest, currentUser);
        return ResponseEntity.ok().body(new SuccessResponse("Comment Created Successfully"));
    }

    @ApiOperation(value = "Update Comment by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<AppResponse> update(
        @ApiParam(value = "comment id") @PathVariable("commentId") Long commentId,
        @RequestBody @Valid CommentRequest commentRequest,
        @CurrentUser UserPrincipal currentUser) {       
        commentService.update(commentId,commentRequest,currentUser);
        return ResponseEntity.ok().body(new SuccessResponse("Comment Updated Successfully"));
    }

    @ApiOperation(value = "Find Comment by id")
   
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {       
        return ResponseEntity.ok().body(commentService.findById(id));
    }

    @ApiOperation(value = "Delete Comment by id")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/{id}/delete")
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id,@CurrentUser UserPrincipal currentUser) {  
        commentService.deleteById(id, currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("Comment Deleted Successfully"));
    }
    
}
