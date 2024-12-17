package com.stduy.boardserver.service;

/*
    UserService 인터페이스는 실제 메서드의 시그니처만 작성
    메서드 내용은 작성하지 않는다.
 */

import com.stduy.boardserver.dto.UserDTO;

public interface UserService {

    void register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String userId);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void deleteId(String id, String passWord);
}