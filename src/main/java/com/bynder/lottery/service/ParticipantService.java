package com.bynder.lottery.service;

import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.entity.Participant;
import com.bynder.lottery.domain.entity.Submission;

import java.util.List;

public interface ParticipantService {

    Long register(Participant participant);

    List<String> submit(Submission submission);

    List<String> readAllBallotsOfLottery(String ssn, Long lotteryId);

    List<Lottery> readActiveRegisteredLotteries(String ssn);
}
