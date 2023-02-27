package com.cenoa.history.service.impl;

import com.cenoa.common.event.AccountEvent;
import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.WithdrawEvent;
import com.cenoa.history.domain.document.History;
import com.cenoa.history.domain.repository.HistoryRepository;
import com.cenoa.history.service.HistoryService;
import com.cenoa.history.service.model.HistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public List<HistoryDto> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return historyRepository.findAll(sort)
            .stream()
            .map(HistoryDto::from)
            .collect(Collectors.toList());
    }

    @Override
    public HistoryDto save(final AccountEvent accountEvent) {
        History history = new History();
        history.setResource("Account");
        history.setEventType(accountEvent.getEventType());
        history.setDescription(accountEvent.getItem().toString());
        history.setCreatedAt(LocalDateTime.now());
        history = historyRepository.save(history);
        return HistoryDto.from(history);
    }

    @Override
    public HistoryDto save(final DepositEvent depositEvent) {
        History history = new History();
        history.setResource("Deposit");
        history.setEventType(depositEvent.getEventType());
        history.setDescription(depositEvent.getItem().toString());
        history.setCreatedAt(LocalDateTime.now());
        history = historyRepository.save(history);
        return HistoryDto.from(history);
    }

    @Override
    public HistoryDto save(final WithdrawEvent depositEvent) {
        History history = new History();
        history.setResource("Withdraw");
        history.setEventType(depositEvent.getEventType());
        history.setDescription(depositEvent.getItem().toString());
        history.setCreatedAt(LocalDateTime.now());
        history = historyRepository.save(history);
        return HistoryDto.from(history);
    }
}
