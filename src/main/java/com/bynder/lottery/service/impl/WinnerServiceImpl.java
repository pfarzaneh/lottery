package com.bynder.lottery.service.impl;

import com.bynder.lottery.domain.entity.*;
import com.bynder.lottery.domain.model.WinnerInfo;
import com.bynder.lottery.exception.InvalidDateException;
import com.bynder.lottery.exception.NoBallotSubmittedException;
import com.bynder.lottery.repository.BallotRepository;
import com.bynder.lottery.repository.WinnerRepository;
import com.bynder.lottery.service.WinnerService;
import com.bynder.lottery.util.DateUtil;
import com.bynder.lottery.util.NumberUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class WinnerServiceImpl implements WinnerService {

    private final WinnerRepository winnerRepository;
    private final BallotRepository ballotRepository;

    public WinnerServiceImpl(WinnerRepository winnerRepository,
                             BallotRepository ballotRepository) {
        this.winnerRepository = winnerRepository;
        this.ballotRepository = ballotRepository;
    }

    @Override
    public WinnerInfo readWinnerByDate(String date) {
        Date d = DateUtil.convert(LocalDate.parse(date));
        Optional<Winner> optionalWinner = winnerRepository.findByDate(d);
        if (optionalWinner.isEmpty())
            throw new InvalidDateException("The entered date is not valid !!!");

        Ballot ballot = optionalWinner.get().getBallot();
        Submission submission = ballot.getSubmission();
        Participant participant = submission.getParticipant();
        Lottery lottery = participant.getLottery();

        WinnerInfo winnerModel = new WinnerInfo();
        winnerModel.setAward(lottery.getAward());
        winnerModel.setLotteryName(lottery.getName());
        winnerModel.setParticipantName(participant.getName());
        winnerModel.setParticipantSsn(participant.getSsn());
        winnerModel.setBallotCode(ballot.getCode());
        winnerModel.setSubmissionDate(submission.getDate());

        return winnerModel;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void selectWinner() {
        LocalDateTime dt = LocalDateTime.now();
        if (dt.getHour() == 0) dt = dt.minusDays(1);
        Date startDate = DateUtil.convert(dt.toLocalDate().atStartOfDay());
        Date endDate = DateUtil.convert(dt.toLocalDate().atTime(LocalTime.MAX));
        Object[][] range = ballotRepository.findRangeOfIds(startDate, endDate);
        if (range != null && range.length == 1) {
            long min = ((BigInteger) range[0][0]).longValue();
            long max = ((BigInteger) range[0][1]).longValue();
            long id = NumberUtil.randomBetween(min, max);
            Optional<Ballot> optionalBallot = ballotRepository.findById(id);
            if (optionalBallot.isEmpty()) throw new RuntimeException("Something Went Wrong !!!");
            winnerRepository.save(Winner.build(optionalBallot.get(), startDate));
        } else throw new NoBallotSubmittedException("Not any ballot submitted at specified date !!!");
    }

}
