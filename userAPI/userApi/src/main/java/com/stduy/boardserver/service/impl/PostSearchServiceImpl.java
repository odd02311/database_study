package com.stduy.boardserver.service.impl;

import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.request.PostSearchRequest;
import com.stduy.boardserver.mapper.PostSearchMapper;
import com.stduy.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper) {
        this.postSearchMapper = postSearchMapper;
    }

    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try{
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch(RuntimeException e) {
            log.error("selectPost 메서드 실패", e.getMessage());
        }

        return postDTOList;
    }
}
