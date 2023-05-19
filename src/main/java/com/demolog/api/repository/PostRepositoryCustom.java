package com.demolog.api.repository;

import com.demolog.api.domain.Post;
import com.demolog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
