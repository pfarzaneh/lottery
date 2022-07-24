package com.bynder.lottery.mapper;

import com.bynder.lottery.controller.dto.CreateLotteryRequestDto;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.LotteryState;
import com.bynder.lottery.util.DateUtil;

public class LotteryMapper {

    public static Lottery mapToActiveLottery(CreateLotteryRequestDto dto) {
        Lottery lottery = new Lottery();
        lottery.setName(dto.getName());
        lottery.setCreateDate(DateUtil.currentTimestamp());
        lottery.setAward(dto.getAward());
        lottery.setBallotPrice(dto.getBallotPrice());
        lottery.setBallotUnit(dto.getBallotUnit());
        lottery.setState(LotteryState.ACTIVE);
        return lottery;
    }
}
