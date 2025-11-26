package com.sporty.betting.service;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.PlaceBetRequest;
import com.sporty.betting.entity.Bet;
import com.sporty.betting.entity.User;
import com.sporty.betting.exception.BadRequestException;
import com.sporty.betting.exception.NotFoundException;
import com.sporty.betting.repository.BetRepository;
import com.sporty.betting.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BettingServiceImpl implements BettingService {

    private final UserRepository userRepository;
    private final BetRepository betRepository;

    public BettingServiceImpl(UserRepository userRepository, BetRepository betRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
    }

    @Override
    @Transactional
    public BetDTO placeBet(Long userId, PlaceBetRequest req) {
        log.info("Place Bet Request for user:{} Req:{}",userId, req);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found "+ userId));

        boolean betAlreadyPlaced = user.getBets().stream().anyMatch(bet -> bet.getEventId().equals(req.eventId()));

        if (betAlreadyPlaced) {
            throw new BadRequestException("Bet is already placed against this event "+req.eventId());
        }

        if (user.getBalance().compareTo(req.amount()) < 0)
            throw new BadRequestException("Insufficient balance for placing bet request");

        Bet bet = new Bet();
        bet.setEventId(req.eventId());
        bet.setDriverId(req.driverId());
        bet.setOdds(req.odds());
        bet.setAmount(req.amount());
        bet.setUser(user);

        user.setBalance(user.getBalance().subtract(req.amount()));
        betRepository.save(bet);

        return BetDTO.from(bet);
    }
}
