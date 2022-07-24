package com.bynder.lottery.service.impl;

import com.bynder.lottery.domain.LotteryState;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.exception.NotFoundException;
import com.bynder.lottery.repository.LotteryRepository;
import com.bynder.lottery.service.LotteryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryServiceImpl(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Override
    public Long create(Lottery lottery) {
        Lottery insertedLottery = lotteryRepository.save(lottery);
        return insertedLottery.getId();
    }

    @Override
    public List<Lottery> readAll(Integer page, Integer size) {
        return lotteryRepository.findAllByOrderByCreateDateDesc(PageRequest.of(page, size));
    }

    @Override
    public void finish(Long id) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(id);
        if (optionalLottery.isEmpty()) throw new NotFoundException("Lottery Not Found !!!");
        Lottery lottery = optionalLottery.get();
        lottery.setState(LotteryState.FINISHED);
    }

    @Override
    public boolean isActive(Long id) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(id);
        if (optionalLottery.isPresent()) return LotteryState.ACTIVE.equals(optionalLottery.get().getState());
        else throw new NotFoundException("No lottery found !!");
    }

}
