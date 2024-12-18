package com.stduy.boardserver.mapper;

import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}
