package com.stduy.boardserver.service.impl;

import com.stduy.boardserver.dto.PostDTO;
import com.stduy.boardserver.dto.UserDTO;
import com.stduy.boardserver.mapper.PostMapper;
import com.stduy.boardserver.mapper.UserProfileMapper;
import com.stduy.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final UserProfileMapper userProfileMapper;
    private final PostMapper postMapper;

    // 생성자 주입
    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if(memberInfo != null) {
            postMapper.register(postDTO);
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
}