package com.github.braians.springblog.controller;



import javax.validation.Valid;

import com.github.braians.springblog.payload.AppResponse;
import com.github.braians.springblog.payload.EmailPayload;
import com.github.braians.springblog.payload.NameRequest;
import com.github.braians.springblog.payload.PasswordPayload;
import com.github.braians.springblog.payload.SignInRequest;
import com.github.braians.springblog.payload.SuccessResponse;
import com.github.braians.springblog.payload.UserProfile;
import com.github.braians.springblog.payload.UserSummary;
import com.github.braians.springblog.projection.PostSummary;
import com.github.braians.springblog.security.CurrentUser;
import com.github.braians.springblog.security.UserPrincipal;
import com.github.braians.springblog.service.UserService;
import com.github.braians.springblog.util.AppConstant;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {
     
    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get Current User")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping(value = "/me")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) { 
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getName(), currentUser.getEmail());      
        return ResponseEntity.ok().body(userSummary);
    }    
    
    @ApiOperation(value = "Update User")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value = "/")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id,@RequestBody @Valid SignInRequest userRequest, @CurrentUser UserPrincipal currentUser) { 
        userService.update(id, userRequest, currentUser);      
        return ResponseEntity.ok().body(new SuccessResponse("User update Successfully"));
    }

    @ApiOperation(value = "Find User by username")
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable("username") String username) {       
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @ApiOperation(value = "Delete User by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AppResponse> deleteById(@PathVariable("id") Long id, @CurrentUser UserPrincipal currentUser) {  
        userService.deleteById(id,currentUser);     
        return ResponseEntity.ok().body(new SuccessResponse("User deleted successfully"));
    }

    @ApiOperation(value = "Find Posts by username")
    @GetMapping(value="/{username}/submitted")
    public ResponseEntity<Page<PostSummary>> getPostsByUsername(
        @PathVariable("username") String username,
        @RequestParam(name = "page",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(name = "size",defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size) {
       return ResponseEntity.ok().body(userService.findPostsByUsername(username, page, size));
    }

    @ApiOperation(value = "Find Comments by username")
    @GetMapping(value="/{username}/comments")
    public ResponseEntity<?> getCommentsByUsername(
        @PathVariable("username") String username,
        @RequestParam(name = "page",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(name = "size",defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size) {
       return ResponseEntity.ok().body(userService.findCommentsByUsername(username, page, size));
    }

    @ApiOperation(value = "Check Email Availability")
    @GetMapping(value="/checkEmailAvailability")
    public ResponseEntity<AppResponse> checkEmailAvailability(@RequestParam String email) {
        userService.checkEmailAvailability(email);
        return ResponseEntity.ok().body(new SuccessResponse("Email Available"));
    }

    @GetMapping(value="/checkUsernameAvailability")
    @ApiOperation(value = "Check Username Availability")
    public ResponseEntity<AppResponse> checkUsernameAvailability(@RequestParam String username) {
        userService.checkUsernameAvailability(username);
        return ResponseEntity.ok().body(new SuccessResponse("Username Available"));
    }
    
    @ApiOperation(value = "Update User")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value="/{username}/changePassword")
    public ResponseEntity<AppResponse> updatePassword(@PathVariable("username") String username,@RequestBody @Valid PasswordPayload passwordPayload, @CurrentUser UserPrincipal currentUser) { 
        userService.updatePassword(username, passwordPayload, currentUser);      
        return ResponseEntity.ok().body(new SuccessResponse("Password updated Successfully"));
    }

    @ApiOperation(value = "Update User")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value="/{username}/changeEmail")
    public ResponseEntity<AppResponse> updateEmail(@PathVariable("username") String username,@RequestBody @Valid EmailPayload emailPayload, @CurrentUser UserPrincipal currentUser) { 
        userService.updateEmail(username, emailPayload, currentUser);      
        return ResponseEntity.ok().body(new SuccessResponse("Email updated Successfully"));
    }

    @ApiOperation(value = "Update User")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PutMapping(value="/{username}/changeName")
    public ResponseEntity<AppResponse> updateName(@PathVariable("username") String username,@RequestBody @Valid NameRequest nameRequest, @CurrentUser UserPrincipal currentUser) { 
        userService.updateName(username, nameRequest, currentUser);      
        return ResponseEntity.ok().body(new SuccessResponse("Name updated Successfully"));
    }
    
}
