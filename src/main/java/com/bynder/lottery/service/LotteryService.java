package com.bynder.lottery.service;

import com.bynder.lottery.domain.entity.Lottery;

import java.util.List;

public interface LotteryService {

    Long create(Lottery lottery);

    List<Lottery> readAll(Integer page, Integer size);

    void finish(Long id);

    boolean isActive(Long id);

}
