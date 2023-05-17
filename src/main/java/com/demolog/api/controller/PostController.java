package com.demolog.api.controller;

import com.demolog.api.domain.Post;
import com.demolog.api.request.PostCreate;
import com.demolog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id) {
        Post post = postService.get(id);
        return post;
    }

}
