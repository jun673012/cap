package com.kmou.server.controller;

import com.kmou.server.dto.PostBodyShowDTO;
import com.kmou.server.dto.PostHeadShowDTO;
import com.kmou.server.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostBodyShowDTO> getPost(@PathVariable Long id) {
        return postService.findById(id)
                .map(post -> {
                    PostBodyShowDTO responseDTO = new PostBodyShowDTO();
                    responseDTO.setId(post.getId());
                    responseDTO.setAddress(post.getAddress());
                    responseDTO.setImage(post.getImage());
                    responseDTO.setUserName(post.getUser().getName());
                    responseDTO.setUserId(post.getUser().getUsername());
                    responseDTO.setPrice(post.getPrice());
                    responseDTO.setAccepted(post.isAccepted());
                    responseDTO.setPaid(post.isPaid());
                    responseDTO.setGarbageName(post.getGarbageName());
                    responseDTO.setGarbageContent(post.getGarbageContent());
                    responseDTO.setTime(post.getCreateDate());
                    return ResponseEntity.ok(responseDTO);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/posts")
    public ResponseEntity<Page<PostHeadShowDTO>> getAllPosts(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createDate"));

        Page<PostHeadShowDTO> postDTOs = postService.getAllPosts(sortedPageable).map(post -> {
            PostHeadShowDTO dto = new PostHeadShowDTO();
            dto.setId(post.getId());
            dto.setGarbageName(post.getGarbageName());
            dto.setUsername(post.getUser().getName());
            dto.setTime(post.getCreateDate());
            dto.setAccepted(post.isAccepted());
            dto.setPaid(post.isPaid());
            return dto;
        });
        return ResponseEntity.ok(postDTOs);
    }

}
