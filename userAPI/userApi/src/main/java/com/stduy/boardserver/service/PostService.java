package com.stduy.boardserver.service;

import com.stduy.boardserver.dto.CommentDTO;
import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);
    List<PostDTO> getMyPosts(int accountId);
    void updatePosts(PostDTO postDTO);
    void deletePosts(int userId, int postId);

    // --- 댓글 ---
    void registerComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    void deletePostComment(int userId, int commentId);

    // --- 태그 ---
    void registerTag(TagDTO tagDTO);
    void updateTag(TagDTO tagDTO);
    void deleteTag(int userId, int tagId);

}
