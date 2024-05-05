package com.kmou.server.controller;

import com.kmou.server.dto.AdminPostDTO;
import com.kmou.server.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<AdminPostDTO>> adminGetAllPosts(Pageable pageable) {
        logger.info("Admin get all posts with pagination");

        Pageable sortedByCreateDateDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("createDate").descending()
        );

        Page<AdminPostDTO> postDTOs = postService.getAllPosts(sortedByCreateDateDesc).map(post -> {
            AdminPostDTO dto = new AdminPostDTO();
            dto.setId(post.getId());
            dto.setAddress(post.getAddress());
            dto.setName(post.getUser().getName());
            dto.setPaid(post.isPaid());
            dto.setAccepted(post.isAccepted());
            dto.setTime(post.getCreateDate());
            return dto;
        });

        return ResponseEntity.ok(postDTOs);
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<Void> acceptPost(@PathVariable Long id) {
        postService.acceptedPost(id);
        logger.info("Admin accepted post id: {}", id);
        return ResponseEntity.ok().build();
    }
}
