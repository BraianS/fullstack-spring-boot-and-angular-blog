package com.github.braians.springblog.service;

import java.util.Set;

import com.github.braians.springblog.exception.ResourceAlreadyExistException;
import com.github.braians.springblog.exception.ResourceNotFoundException;
import com.github.braians.springblog.exception.UnauthorizedException;
import com.github.braians.springblog.model.Category;
import com.github.braians.springblog.model.RoleName;
import com.github.braians.springblog.payload.TitleRequest;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.projection.TagsOrCategoriesResponse;
import com.github.braians.springblog.repository.CategoryRepository;
import com.github.braians.springblog.repository.PostRepository;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.util.SlugUtil;
import com.mysql.cj.exceptions.UnableToConnectException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public CategoryService(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public Category save(TitleRequest titleRequest) {
        Boolean existsByTitle = categoryRepository.existsByTitle(titleRequest.getTitle());

        if (existsByTitle) {
            throw new ResourceAlreadyExistException("Category", "title", titleRequest.getTitle());
        }

        Category category = new Category();
        category.addCategory(titleRequest.getTitle());
        category.setSlug(SlugUtil.makeSlug(titleRequest.getTitle()));
        return categoryRepository.save(category);
    }

    public Category update(Long id, TitleRequest titleRequest, UserPrincipal currentUser) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (category.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            category.addCategory(titleRequest.getTitle());
            category.setSlug(SlugUtil.makeSlug(titleRequest.getTitle()));
            return categoryRepository.save(category);
        } else {
            throw new UnableToConnectException("You don't have permission to update this Category");
        }
    }

    public Page<PostSummary> findPostsByCategoryId(Long id, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return postRepository.findPostsByCategoryId(id, pageable);
    }

    public void deleteById(Long id, UserPrincipal currentUser) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (category.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            categoryRepository.deleteById(id);
        } else {
            throw new UnauthorizedException("You don't have permission to update this Category");
        }
    }

    public Set<TagsOrCategoriesResponse> findCategories() {
        return categoryRepository.findCategories();
    }

    public Set<TagsOrCategoriesResponse> findAllByTitleAndLimitBy10(String title) {
        return categoryRepository.findAllByTitleAndLimitBy10(title);
    }
}