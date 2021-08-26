package com.github.braians.springblog.service;

import java.util.Set;

import com.github.braians.springblog.exception.ResourceNotFoundException;
import com.github.braians.springblog.exception.UnauthorizedException;
import com.github.braians.springblog.model.Category;
import com.github.braians.springblog.model.Post;
import com.github.braians.springblog.model.RoleName;
import com.github.braians.springblog.model.StatusName;
import com.github.braians.springblog.model.Tag;
import com.github.braians.springblog.model.User;
import com.github.braians.springblog.payload.PostRequest;
import com.github.braians.springblog.projection.PostDetail;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.repository.CategoryRepository;
import com.github.braians.springblog.repository.PostRepository;
import com.github.braians.springblog.repository.TagRepository;
import com.github.braians.springblog.repository.UserRepository;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.util.SlugUtil;
import com.github.braians.springblog.util.SummaryUtil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository,
            CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Post save(PostRequest postRequest, UserPrincipal currentUser) {

        Set<Tag> tags = tagRepository.findByTitleIn(postRequest.getTags());

        Set<Category> categories = categoryRepository.findByTitleIn(postRequest.getCategories());

        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        Post post = new Post();

        if (postRequest.getStatus().contains(StatusName.PUBLIC.toString())) {
            post.setStatus(StatusName.PUBLIC);
        } else {
            post.setStatus(StatusName.PRIVATE);
        }

        post.addTag(postRequest.getTitle(), postRequest.getContent(), tags, categories);
        post.setSlug(SlugUtil.makeSlug(postRequest.getTitle()));
        post.setSummary(SummaryUtil.makeSummary(postRequest.getContent()));
        post.addUser(user);
        return postRepository.save(post);
    }

    public Post update(Long id, PostRequest postRequest, UserPrincipal currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        if (postRequest.getStatus().contains(StatusName.PUBLIC.toString())) {
            post.setStatus(StatusName.PUBLIC);
        } else {
            post.setStatus(StatusName.PRIVATE);
        }

        if (post.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            Set<Tag> tags = tagRepository.findBySlugIn(postRequest.getTags());
            Set<Category> categories = categoryRepository.findBySlugIn(postRequest.getCategories());

            post.addTag(postRequest.getTitle(), postRequest.getContent(), tags, categories);
            return postRepository.save(post);
        }
        throw new UnauthorizedException("You don't have permission to update this post");
    }

    public PostDetail findById(Long id) {
        return postRepository.findPostById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public void deleteById(Long id, UserPrincipal currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        if (post.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            postRepository.deleteById(id);
        }
        throw new UnauthorizedException("You don't have permission to delete this post");
    }

    public Page<PostSummary> findAllByUserId(Long userId, int page, int size, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order, "createdAt"));
        return postRepository.findAllByUserId(userId, pageable);
    }

    public Page<PostSummary> findAllOrderByCreatedAtAsc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllOrderByCreatedAtAsc(pageable);
    }
}