package com.cenoa.history.service;

import com.cenoa.common.event.AccountEvent;
import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.WithdrawEvent;
import com.cenoa.history.service.model.HistoryDto;

import java.util.List;

public interface HistoryService {

    List<HistoryDto> findAll();
    HistoryDto save(AccountEvent accountEvent);
    HistoryDto save(DepositEvent depositEvent);
    HistoryDto save(WithdrawEvent withdrawEvent);

}
