package com.kmou.server.controller;

import com.kmou.server.dto.PostHeadShowDTO;
import com.kmou.server.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final PostService postService;

    @GetMapping("/user")
    public ResponseEntity<Page<PostHeadShowDTO>> getUserPosts(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        Page<PostHeadShowDTO> posts = postService.getPostsByUser(userDetails.getUsername(), pageable)
                .map(post -> new PostHeadShowDTO(post.getId(), post.getGarbageName(), post.getUsername(), post.getTime(), post.isAccepted(), post.isPaid()));

        return ResponseEntity.ok(posts);
    }

}
