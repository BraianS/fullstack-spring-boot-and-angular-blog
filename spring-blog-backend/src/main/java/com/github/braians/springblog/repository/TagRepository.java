package com.github.braians.springblog.repository;

import java.util.Set;

import com.github.braians.springblog.model.Tag;
import com.github.braians.springblog.projection.TagsOrCategoriesResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsTagByTitleIgnoreCase(String slug);
    
    Set<Tag> findBySlugIn(Set<String> slugs);
    Set<Tag> findByTitleIn(Set<String> slugs);

    @Query("SELECT tag FROM Tag tag")
    Set<TagsOrCategoriesResponse> findTags();

    @Query(value="SELECT * FROM Tag tag WHERE tag.title LIKE %:title% LIMIT 10 ",nativeQuery = true)
    Set<TagsOrCategoriesResponse> findAllByTitleAndLimitBy10(@Param("title") String title);
}
