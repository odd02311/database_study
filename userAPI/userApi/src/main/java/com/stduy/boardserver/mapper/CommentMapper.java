package com.stduy.boardserver.mapper;

import com.stduy.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public int register(CommentDTO commentDTO);
    public void updateComments(CommentDTO commentDTO);
    public void deletePostComments(int commentId);
}
