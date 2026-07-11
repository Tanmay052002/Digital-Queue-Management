package com.queuemgmt.service;

import com.queuemgmt.model.Counter;
import com.queuemgmt.model.QueueEvent;
import com.queuemgmt.model.Token;
import com.queuemgmt.repository.CounterRepository;
import com.queuemgmt.repository.QueueEventRepository;
import com.queuemgmt.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final CounterRepository counterRepository;
    private final QueueEventRepository queueEventRepository;

    // just an in-memory counter for generating token numbers, resets when app restarts
    // (not great for a real system, but fine for this project)
    private int tokenCounter = 1;

    public TokenService(TokenRepository tokenRepository,
                         CounterRepository counterRepository,
                         QueueEventRepository queueEventRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.queueEventRepository = queueEventRepository;
    }

    // picks the counter that currently has the least people waiting
    public Counter pickBestCounter(Long officeId) {
        List<Counter> counters = counterRepository.findByOfficeId(officeId);

        if (counters.isEmpty()) {
            return null;
        }

        Counter best = counters.get(0);
        long bestWaiting = tokenRepository.countByCounterIdAndStatus(best.getId(), "WAITING");

        for (Counter c : counters) {
            long waiting = tokenRepository.countByCounterIdAndStatus(c.getId(), "WAITING");
            if (waiting < bestWaiting) {
                best = c;
                bestWaiting = waiting;
            }
        }

        return best;
    }

    public Token generateToken(Long userId, Long serviceId, Counter counter) {
        Token token = new Token();
        token.setUserId(userId);
        token.setServiceId(serviceId);
        token.setCounterId(counter.getId());
        token.setStatus("WAITING");
        token.setCreatedAt(LocalDateTime.now());

        // token number like "A-1", "A-2" ...
        String tokenNumber = "A-" + tokenCounter;
        tokenCounter++;
        token.setTokenNumber(tokenNumber);

        Token saved = tokenRepository.save(token);

        // record the queue event too
        QueueEvent event = new QueueEvent(saved.getId(), "TOKEN_GENERATED", LocalDateTime.now());
        queueEventRepository.save(event);

        return saved;
    }

    // returns how many free slots are left at a counter (very simplified)
    public int getAvailableSlots(Counter counter) {
        long waiting = tokenRepository.countByCounterIdAndStatus(counter.getId(), "WAITING");
        int free = counter.getMaxSlots() - (int) waiting;
        if (free < 0) {
            free = 0;
        }
        return free;
    }
}
