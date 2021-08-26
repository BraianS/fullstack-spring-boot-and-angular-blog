package com.github.braians.springblog.service;

import com.github.braians.springblog.exception.ResourceAlreadyExistException;
import com.github.braians.springblog.exception.ResourceNotFoundException;
import com.github.braians.springblog.exception.UnauthorizedException;
import com.github.braians.springblog.model.RoleName;
import com.github.braians.springblog.model.User;
import com.github.braians.springblog.payload.EmailPayload;
import com.github.braians.springblog.payload.NameRequest;
import com.github.braians.springblog.payload.PasswordPayload;
import com.github.braians.springblog.payload.SignInRequest;
import com.github.braians.springblog.payload.UserProfile;
import com.github.braians.springblog.projection.CommentSummary;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.repository.CommentRepository;
import com.github.braians.springblog.repository.PostRepository;
import com.github.braians.springblog.repository.UserRepository;
import com.github.braians.springblog.security.UserPrincipal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PostRepository postRepository,
            CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User update(Long id, SignInRequest userRequest, UserPrincipal currentUser) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            if (userRepository.existsByUsername(userRequest.getUsername())) {
                throw new ResourceAlreadyExistException("User", "username", userRequest.getUsername());
            }

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new ResourceAlreadyExistException("User", "email", userRequest.getUsername());
            }

            if (userRepository.existsByPassword(userRequest.getPassword())) {
                throw new ResourceAlreadyExistException("Password already exist");
            }

            user.addUser(userRequest.getName(), userRequest.getUsername(), userRequest.getPassword(),
                    userRequest.getEmail());

            return userRepository.save(user);
        }

        throw new UnauthorizedException("You don't have permission to update this user");

    }

    public UserProfile findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserProfile userProfile = new UserProfile(user.getId(), user.getName(), user.getUsername(),
                user.getCreatedAt());
        return userProfile;
    }

    public void deleteById(Long id, UserPrincipal currentUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            userRepository.deleteById(id);
        }
        throw new UnauthorizedException("You don't have permission to update this user");

    }

    public Page<PostSummary> findPostsByUsername(String username, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());

        return postRepository.findUserByUserUsername(username, pageable);
    }

    public Page<CommentSummary> findCommentsByUsername(String username, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return commentRepository.findUserByUserUsername(username, pageable);
    }

    public Boolean checkEmailAvailability(String email) {
        Boolean existEmail = userRepository.existsByEmail(email);
        if (existEmail) {
            throw new ResourceAlreadyExistException("User", "email", email);
        }
        return existEmail;
    }

    public Boolean checkUsernameAvailability(String username) {
        Boolean existUsername = userRepository.existsByUsername(username);
        if (existUsername) {
            throw new ResourceAlreadyExistException("User", "username", username);
        }
        return existUsername;
    }

    public void updatePassword(String username, PasswordPayload passwordPayload, UserPrincipal currentUser) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (currentUser.getUsername().equals(user.getUsername())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            boolean isOldpasswordMatchUserPassword = passwordEncoder.matches(passwordPayload.getOldPassword(),
                    user.getPassword());

            boolean isNewPasswordMatchUserPassword = passwordEncoder.matches(passwordPayload.getNewPassword(),
                    user.getPassword());

            if (isOldpasswordMatchUserPassword) {

                if (isNewPasswordMatchUserPassword) {
                    throw new ResourceAlreadyExistException("Change password");
                }

                else if (passwordPayload.getNewPassword().equals(passwordPayload.getConfirmPassword())) {
                    user.setPassword(passwordEncoder.encode(passwordPayload.getNewPassword()));
                    userRepository.save(user);
                }

                else {
                    throw new ResourceAlreadyExistException("Incorrect password");
                }
            }

            else {
                throw new ResourceAlreadyExistException("Incorrect password");
            }

        } else {
            throw new UnauthorizedException("You don't have permission to update this user");
        }
    }

    public Boolean updateEmail(String username, EmailPayload emailPayload, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            user.setEmail(emailPayload.getEmail());
            userRepository.save(user);
            return true;
        }

        if (user.getUsername().equals(currentUser.getUsername())) {

            boolean isPassworMatchUserPassword = passwordEncoder.matches(emailPayload.getPassword(),
                    user.getPassword());

            if (isPassworMatchUserPassword && !user.getEmail().equals(emailPayload.getEmail())) {
                user.setEmail(emailPayload.getEmail());
                userRepository.save(user);

            } else if (!isPassworMatchUserPassword) {
                throw new ResourceAlreadyExistException("Incorrect password");
            }

            else if (user.getEmail().equals(emailPayload.getEmail())) {
                throw new ResourceAlreadyExistException("User", "Email", emailPayload.getEmail());
            }

            else {
                throw new ResourceAlreadyExistException("Incorrect password");
            }
            return true;
        } else {
            throw new UnauthorizedException("You don't have permission to update this email");
        }

    }

    public void updateName(String username, NameRequest nameRequest, UserPrincipal currentUser) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (currentUser.getUsername().equals(user.getUsername())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
                    user.setName(nameRequest.getName());
                    userRepository.save(user);
        }

        else {
            throw new UnauthorizedException("You don't have permission to update this email");
        }
       
    }
}
