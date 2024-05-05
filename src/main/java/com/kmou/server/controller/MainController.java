package com.kmou.server.controller;

import com.kmou.server.dto.PostHeadShowDTO;
import com.kmou.server.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MainController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getPostsHomepage(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();

        Pageable sortedPageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<PostHeadShowDTO> allPosts = postService.getAllPosts(sortedPageable).map(post -> new PostHeadShowDTO(post.getId(), post.getGarbageName(), post.getUser().getName(), post.getCreateDate(), post.isAccepted(), post.isPaid()));
        response.put("allPosts", allPosts);

        if (userDetails != null) {
            Page<PostHeadShowDTO> myPosts = postService.getPostsByUser(userDetails.getUsername(), sortedPageable).map(post -> new PostHeadShowDTO(post.getId(), post.getGarbageName(), post.getUsername(), post.getTime(), post.isAccepted(), post.isPaid()));
            response.put("myPosts", myPosts);
        } else {
            response.put("myPosts", "Unauthorized: No user logged in.");
        }

        return ResponseEntity.ok(response);
    }

}
