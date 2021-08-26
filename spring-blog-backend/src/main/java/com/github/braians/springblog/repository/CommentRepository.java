package com.github.braians.springblog.repository;

import java.util.Set;

import com.github.braians.springblog.model.Comment;
import com.github.braians.springblog.projection.CommentResponse;
import com.github.braians.springblog.projection.CommentSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

   Set<CommentResponse> findByParentCommentIsNullAndPostId(@Param("post_id") Long id);
   
   Page<CommentSummary> findUserByUserUsername(@Param("username") String username, Pageable pageable);
   

}
