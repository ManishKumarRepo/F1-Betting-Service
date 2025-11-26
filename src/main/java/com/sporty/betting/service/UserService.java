package com.sporty.betting.service;

import com.sporty.betting.dto.UserDTO;
import com.sporty.betting.dto.UserRegisterRequestDTO;

public interface UserService {
    UserDTO getUser(Long userId);
    UserDTO registerUser(UserRegisterRequestDTO reqDto);
}
