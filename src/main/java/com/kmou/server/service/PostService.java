package com.kmou.server.service;

import com.kmou.server.dto.PostHeadShowDTO;
import com.kmou.server.dto.UserPostDTO;
import com.kmou.server.entity.Post;
import com.kmou.server.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Transactional
    public void acceptedPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setAccepted(true);
    }

    @Transactional(readOnly = true)
    public Page<PostHeadShowDTO> getPostsByUser(String username, Pageable pageable) {
        return postRepository.findByUserUsername(username, pageable).map(this::convertToDto);
    }

    private PostHeadShowDTO convertToDto(Post post) {
        return new PostHeadShowDTO(post.getId(), post.getGarbageName(), post.getUser().getName(), post.getCreateDate(), post.isAccepted(), post.isPaid());
    }

    @Transactional(readOnly = true)
    public Page<UserPostDTO> getPostsShowByUser(String username, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createDate"));

        Page<UserPostDTO> posts = postRepository.findByUserUsername(username, sortedPageable)
                .map(post -> new UserPostDTO(
                        post.getId(),
                        post.getGarbageName(),
                        post.getUser().getUsername(),
                        post.getCreateDate(),
                        post.getAddress(),
                        post.getUser().getPhoneNumber(),
                        post.getPrice(),
                        post.getImage(),
                        post.isAccepted(),
                        post.isPaid()
                ));

        return posts;
    }

}
