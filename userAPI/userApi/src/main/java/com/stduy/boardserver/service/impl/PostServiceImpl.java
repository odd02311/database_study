package com.stduy.boardserver.service.impl;

import com.stduy.boardserver.dto.CommentDTO;
import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.TagDTO;
import com.stduy.boardserver.dto.UserDTO;
import com.stduy.boardserver.mapper.CommentMapper;
import com.stduy.boardserver.mapper.PostMapper;
import com.stduy.boardserver.mapper.TagMapper;
import com.stduy.boardserver.mapper.UserProfileMapper;
import com.stduy.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final UserProfileMapper userProfileMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

    // 생성자 주입
    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }

    @CacheEvict(value = "getPosts", allEntries = true)
    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo != null) {
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            for(int i=0; i<postDTO.getTagDTOList().size(); i++) {
                TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                tagMapper.register(tagDTO);
                Integer tagId = tagDTO.getId();
                tagMapper.createPostTag(tagId, postId);
            }
        } else {
            log.error("register Error! {}", postDTO);
            throw new RuntimeException("register Error! 게시글 등록 메서드를 확인해주세요" + postDTO);
        }

    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if(postDTO!=null && postDTO.getId() !=0){
            postMapper.updatePosts(postDTO);
        } else {
            log.error("updatePosts Error! {}", postDTO);
            throw new RuntimeException("updatePosts Error! 게시글 수정 메서드를 확인해주세요!" + postDTO);
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId !=0 && postId !=0){
            postMapper.deletePosts(postId);
        } else {
            log.error("deletePosts Error! {}", postId);
            throw new RuntimeException("deletePosts Error! 게시글 삭제 메서드를 확인해주세요" + postId);
        }
    }

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() != 0){
            commentMapper.register(commentDTO);
        } else {
            log.error("registerComment Error! {}", commentDTO);
            throw new RuntimeException("registerComment Error" + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO != null){
            commentMapper.updateComments(commentDTO);
        } else {
            log.error("updateComment Error!");
            throw new RuntimeException("updateComment Error");
        }
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if(userId !=0 && commentId !=0){
            commentMapper.deletePostComments(commentId);
        }  else {
            log.error("deletePostComment Error! {}", commentId);
            throw new RuntimeException("deletePostComment Error" + commentId);
        }
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null){
            tagMapper.register(tagDTO);
        } else{
            log.error("registerTag Error! {}", tagDTO);
            throw new RuntimeException("registerTag Error" + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null){
            tagMapper.updateTags(tagDTO);
        } else{
            log.error("updateTag Error!");
            throw new RuntimeException("updateTag Error");
        }
    }

    @Override
    public void deleteTag(int userId, int tagId) {
        if(userId !=0 && tagId !=0){
            tagMapper.deletePostTag(tagId);
        }  else {
            log.error("deletePostTag Error! {}", tagId);
            throw new RuntimeException("deletePostTag Error" + tagId);
        }
    }
}
