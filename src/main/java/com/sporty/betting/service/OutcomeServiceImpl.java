package com.sporty.betting.service;

import com.sporty.betting.dto.BetDTO;
import com.sporty.betting.dto.EventOutcomeRequest;
import com.sporty.betting.dto.EventOutcomeResponse;
import com.sporty.betting.dto.EventResultDTO;
import com.sporty.betting.entity.Bet;
import com.sporty.betting.entity.BetStatus;
import com.sporty.betting.repository.BetRepository;
import com.sporty.betting.repository.EventOutcomeRepository;
import com.sporty.betting.service.external.EventProviderFactory;
import com.sporty.betting.service.external.F1EventProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class OutcomeServiceImpl implements OutcomeService {

    private final BetRepository betRepo;
    private final EventOutcomeRepository outcomeRepo;
    private final EventProviderFactory factory;

    public OutcomeServiceImpl(BetRepository betRepo, EventOutcomeRepository outcomeRepo, EventProviderFactory factory) {
        this.betRepo = betRepo;
        this.outcomeRepo = outcomeRepo;
        this.factory = factory;
    }


    @Override
    @Transactional
    public EventOutcomeResponse processOutcome(EventOutcomeRequest req) {
        List<EventResultDTO> eventoutcomes = null;

        log.info("Fetch Event Results & Bet Winner For Event:{}", req.eventId());

        if (outcomeRepo.existsByEventId(req.eventId())) {
            eventoutcomes = outcomeRepo.findByEventId(req.eventId())
                    .stream().map(EventResultDTO::toDto).toList();
        }

        if(eventoutcomes == null || eventoutcomes.isEmpty()) {
            F1EventProvider provider = factory.getProvider("OpenF1EventProvider");
            eventoutcomes = provider.getEventResultDetails(req.eventId());
            eventoutcomes.forEach(e -> {
                outcomeRepo.save(EventResultDTO.fromDto(e));
            });
        }

        EventResultDTO winner = eventoutcomes.stream()
                .max(Comparator.comparing(EventResultDTO::createdAt).reversed())
                .orElse(null);

        if(winner == null) {
            throw new RuntimeException("Winner Details Not Found for Event:"+req.eventId());
        }

        List<BetDTO> betsWon = new ArrayList<>();
        List<Bet> bets = betRepo.findByEventIdWithUser(req.eventId());

        for (Bet bet : bets) {
            if (bet.getDriverId().equals(winner.driver_number())) {
                bet.setStatus(BetStatus.WON);
                BigDecimal payout = bet.getAmount().multiply(BigDecimal.valueOf(bet.getOdds()));
                bet.setPayout(payout);
                bet.getUser().setBalance(bet.getUser().getBalance().add(payout));
                betsWon.add(BetDTO.from(bet));
            } else {
                bet.setStatus(BetStatus.LOST);
            }
        }

        return new EventOutcomeResponse(winner, betsWon);
    }
}
