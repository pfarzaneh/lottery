package com.bynder.lottery.mapper;

import com.bynder.lottery.controller.dto.RegisterParticipantRequestDto;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.entity.Participant;
import com.bynder.lottery.util.DateUtil;

public class ParticipantMapper {

    public static Participant map(RegisterParticipantRequestDto dto) {
        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setSsn(dto.getSsn());
        participant.setLottery(Lottery.build(dto.getLotteryId()));
        participant.setRegistrationDate(DateUtil.currentTimestamp());
        return participant;
    }
}
