package com.github.braians.springblog.repository;

import java.util.Set;

import com.github.braians.springblog.model.Category;
import com.github.braians.springblog.projection.TagsOrCategoriesResponse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Object findCategories = null;

    Boolean existsByTitle(@Param("title") String title);
    
    Set<Category> findBySlugIn(Set<String> slugs);
    Set<Category> findByTitleIn(Set<String> slugs);

    @Query(value="SELECT * FROM category", nativeQuery = true)
    Set<TagsOrCategoriesResponse> findCategories();

    @Query(value="SELECT * FROM category WHERE category.title LIKE %:title% LIMIT 10 ",nativeQuery = true)
    Set<TagsOrCategoriesResponse> findAllByTitleAndLimitBy10(@Param("title") String title);
}
