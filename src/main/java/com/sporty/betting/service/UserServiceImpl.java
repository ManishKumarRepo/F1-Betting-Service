package com.sporty.betting.service;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.UserDTO;
import com.sporty.betting.dto.UserRegisterRequestDTO;
import com.sporty.betting.entity.User;
import com.sporty.betting.exception.NotFoundException;
import com.sporty.betting.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found - id:"+userId));
        List<BetDTO> bets = user.getBets().stream()
                .map(b -> new BetDTO(b.getUser().getId(), b.getEventId(), b.getDriverId(), b.getOdds(), b.getAmount(), b.getStatus(), b.getPayout()))
                .toList();
        return new UserDTO(user.getId(), user.getName(), user.getBalance(), bets);
    }

    @Override
    public UserDTO registerUser(UserRegisterRequestDTO reqDto) {
        User user = new User();
        user.setName(reqDto.name());
        userRepository.save(user);
        log.info("User:{} registered with ID:{}", user.getName(), user.getId());
        return new UserDTO(user.getId(), user.getName(), user.getBalance(), List.of());
    }
}

