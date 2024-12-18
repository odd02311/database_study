package com.stduy.boardserver.service;

import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);

}
