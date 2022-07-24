package com.bynder.lottery.mapper;

import com.bynder.lottery.controller.dto.SubmissionRequestDto;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.entity.Participant;
import com.bynder.lottery.domain.entity.Submission;
import com.bynder.lottery.util.DateUtil;

public class SubmissionMapper {

    public static Submission map(SubmissionRequestDto dto, String ssn) {
        Submission submission = new Submission();
        submission.setParticipant(Participant.build(ssn, Lottery.build(dto.getLotteryId())));
        submission.setNumberOfBallots(dto.getNumberOfBallots());
        submission.setDate(DateUtil.currentTimestamp());
        return submission;
    }
}
