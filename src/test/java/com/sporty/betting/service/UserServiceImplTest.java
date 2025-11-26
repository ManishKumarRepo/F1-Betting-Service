package com.sporty.betting.service;

import com.sporty.betting.dto.*;
import com.sporty.betting.entity.User;
import com.sporty.betting.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUser_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Alex");
        user.setBalance(BigDecimal.valueOf(100));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = userService.getUser(1L);

        assertEquals("Alex", dto.name());
    }

    @Test
    void registerUser_success() {
        UserRegisterRequestDTO req = new UserRegisterRequestDTO("Alex");

        User user = new User();
        user.setId(1L);
        user.setName("Alex");

        when(userRepository.save(any())).thenReturn(user);

        UserDTO dto = userService.registerUser(req);

        assertEquals("Alex", dto.name());
    }
}

