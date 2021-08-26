package com.github.braians.springblog.repository;

import java.util.Optional;

import com.github.braians.springblog.model.Post;
import com.github.braians.springblog.projection.PostDetail;
import com.github.braians.springblog.projection.PostSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

   @Query(value ="SELECT p.id, p.title, p.content, p.createdAt, p.categories,p.user FROM Post p JOIN p.user user WHERE p.id =:postId")
    Page<PostSummary> findAllByUserId(@Param("postId") Long postId, Pageable pageable);

    Page<PostSummary> findUserByUserUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.tags tag WHERE tag.id =:id")
    Page<PostSummary> findPostsByTagId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.categories category WHERE category.id =:id")
    Page<PostSummary> findPostsByCategoryId(@Param("id") Long id, Pageable pageable);

    Optional<PostDetail> findPostById(@Param("id") Long id);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC ")
    Page<PostSummary> findAllOrderByCreatedAtAsc(Pageable pageable);
}
